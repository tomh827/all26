import unittest

from app.camera.intrinsic import Intrinsic
from app.camera.model import Model
from app.config.identity import Identity


class IntrinsicTest(unittest.TestCase):
    def test_identity(self) -> None:
        identity: Identity = Identity.DEV
        model: Model = Model.GS
        Intrinsic(identity, model)
