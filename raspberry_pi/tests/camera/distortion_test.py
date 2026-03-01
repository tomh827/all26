import unittest

from app.camera.distortion import Distortion
from app.camera.model import Model
from app.config.identity import Identity


class IntrinsicTest(unittest.TestCase):
    def test_identity(self) -> None:
        identity: Identity = Identity.DEV
        model: Model = Model.GS
        Distortion(identity, model)
