"""The Raspberry Pi Global Shutter camera, monochrome.

It uses the YUV420 12-bit encoding option from the camera.
"""

# pylint: disable=E0401

from typing import Any

from typing_extensions import override

from app.camera.real_camera import RealCamera
from app.config.identity import Identity


class CameraGsMono(RealCamera):
    def __init__(self, identity: Identity) -> None:
        print("\n*** Camera: CameraGsMono")
        super().__init__(identity)
        self._fail_mismatched_size()

    @override
    def _sensor(self) -> dict[str, Any]:
        # for rpi camera
        return {
            "output_size": (self._size.sensor_width, self._size.sensor_height),
            "bit_depth": 10,
        }

    @override
    def _main(self) -> dict[str, Any]:
        return {"format": "YUV420", "size": (self._size.width, self._size.height)}

    def _controls(self) -> dict[str, Any]:
        return {
            # ANALOGUE GAIN
            # To minimize blur, set this as high as possible.
            # TODO: try much larger values, up to 250.
            # "AnalogueGain": 8,
            #
            # AUTO EXPOSURE
            # Must be true for outside or in bright sun.
            # "AeEnable": True,
            # "AeEnable": False,
            #
            # EXPOSURE TIME (microseconds)
            # Minimizes blur.  Requires pretty good light.
            # "ExposureTime": 500,
            # Works in less light, slightly more blur.
            # "ExposureTime": 2000,
            #
            # FRAME DURATION LIMITS
            # limit auto exposure: go as fast as possible but no slower than 30fps
            # without a duration limit, we slow down in the dark, which is fine
            # "FrameDurationLimits": (500, 33333),  # 41 fps
            #
            # NOISE REDUCTION MODE
            # noise reduction takes A LOT of time (100 ms per frame!), don't need it.
            # See libcamera.controls.draft.NoiseReductionModeEnum.Off,
            # "NoiseReductionMode": 0,
        }
