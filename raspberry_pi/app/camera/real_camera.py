"""This is a wrapper for Picamera2.

It handles configuration of each camera according to the Pi identity.

For more on the Picamera2 library, see the manual:

https://datasheets.raspberrypi.com/camera/picamera2-manual.pdf

and the source:

https://github.com/raspberrypi/picamera2/
"""

# pylint: disable=E0401

from pprint import pprint
from typing import Any

import numpy as np
from numpy.typing import NDArray
from picamera2 import CompletedRequest, Picamera2, libcamera  # type: ignore
from typing_extensions import override

from app.camera.camera_protocol import Camera, Request
from app.camera.distortion import Distortion
from app.camera.intrinsic import Intrinsic
from app.camera.model import Model
from app.camera.real_request import RealRequest
from app.camera.shutter import Shutter
from app.camera.size import Size
from app.config.identity import Identity
from app.util.timer import Timer


class RealCamera(Camera):
    def __init__(self, identity: Identity) -> None:

        Picamera2.set_logging(Picamera2.INFO)  # type: ignore
        # debug logs with every frame (!)
        # Picamera2.set_logging(Picamera2.DEBUG)
        print("GLOBAL CAMERA INFO")
        pprint(Picamera2.global_camera_info())  # type: ignore
        print("+==================")
        self._cam: Picamera2 = Picamera2()  # type: ignore

        print("\n*** SENSOR MODES AVAILABLE")
        pprint(self._cam.sensor_modes)  # type:ignore

        print("\n*** CAMERA CONTROLS")
        pprint(self._cam.camera_controls)  # type:ignore

        print("\n*** RAW MODES")
        pprint(self._cam._raw_modes)  # type:ignore

        self._mtx: Intrinsic = Intrinsic(identity)
        self._dist: Distortion = Distortion(identity)

        model: Model = Model.get(self._cam.camera_properties)  # type: ignore
        self._rolling = Shutter(model).rolling()
        self._size: Size = Size.from_model(model)

        print("\n\n*** CONFIG! ***\n\n")
        self._camera_config: dict[str, Any] = self._get_config(  # type: ignore
            identity, self._cam  # type: ignore
        )

        print("\n*** REQUESTED CONFIG")
        print(self._camera_config)

        # optimal alignment makes the ISP a little faster
        self._cam.align_configuration(self._camera_config, optimal=True)  # type:ignore
        print("\n*** ALIGNED CONFIG")
        print(self._camera_config)

        self._cam.configure(self._camera_config)  # type:ignore

        self._cam.start()  # type:ignore
        self._frame_time = Timer.time_ns()

    @override
    def capture_request(self) -> Request:
        capture_start: int = Timer.time_ns()
        req: CompletedRequest = self._cam.capture_request()  # type:ignore
        total_time_ms = (capture_start - self._frame_time) / 1000000
        self._frame_time = capture_start
        fps = 1000 / total_time_ms
        return RealRequest(req, fps, self._rolling)  # type: ignore

    @override
    def stop(self) -> None:
        self._cam.stop()  # type: ignore
        print("Camera stop")

    @override
    def get_size(self) -> Size:
        return self._size

    @override
    def get_intrinsic(self) -> NDArray[np.float32]:
        return self._mtx.get()

    @override
    def get_dist(self) -> NDArray[np.float32]:
        return self._dist.get()

    def _buffer_count(self) -> int:
        # more buffers seem to make the pipeline a little smoother
        return 5

    def _queue(self) -> bool:
        # Without queueing, every capture waits for the current
        # frame, which means less FPS and more latency.
        return True

    def _sensor(self) -> dict[str, Any]:
        # not all cameras use the "sensor" field
        return {}

    def _main(self) -> dict[str, Any]:
        # override this
        return {}

    def _transform(self, identity: Identity) -> libcamera.Transform:  # type: ignore
        # Flip for upside-down cameras.
        # see libcamera/src/libcamera/transform.cpp
        match identity:
            case Identity.FLIPPED | Identity.CLIMB_RIGHT:
                return libcamera.Transform(  # type: ignore
                    rotation=0, hflip=True, vflip=True, transpose=False
                )
            case _:
                return libcamera.Transform()  # type: ignore

    def _controls(self) -> dict[str, Any]:
        # override this
        return {}

    def _get_config(
        self,
        identity: Identity,
        cam: Picamera2,  # type: ignore
    ) -> dict[str, Any]:
        return cam.create_still_configuration(  # type:ignore
            buffer_count=self._buffer_count(),
            queue=self._queue(),
            sensor=self._sensor(),
            main=self._main(),
            raw=None,
            transform=self._transform(identity),  # type:ignore
            controls=self._controls(),
        )

    def _fail_mismatched_size(self):
        """Configured size and actual size must match."""
        if (
            self._camera_config["sensor"]["output_size"]
            != self._cam.camera_config["sensor"]["output_size"]  # type:ignore
        ):
            raise ValueError(
                "Desired sensor size must match selected sensor size.",
                self._camera_config["sensor"]["output_size"],
                self._cam.camera_config["sensor"]["output_size"],  # type:ignore
            )
