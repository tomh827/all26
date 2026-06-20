"""The Thrifty Cam camera, using the YUYV encoding."""

# pylint: disable=E0401

from typing import Any

from typing_extensions import override

from app.camera.real_camera import RealCamera
from app.config.identity import Identity


class CameraThriftyYuyv(RealCamera):
    def __init__(self, identity: Identity) -> None:
        print("\n*** Camera: CameraThriftyYuyv")
        super().__init__(identity)

    @override
    def _main(self) -> dict[str, Any]:
        return {"format": "YUYV", "size": (self._size.width, self._size.height)}

    def _controls(self) -> dict[str, Any]:
        return {
            # ANALOGUE GAIN
            # To minimize blur, set this as high as possible.
            # On the Thiftycam the range is [1.0, 4.0]
            # "AnalogueGain": 4.0,
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
        }
