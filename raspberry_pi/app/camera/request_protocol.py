# pylint: disable=W2301

from typing import Protocol
from contextlib import AbstractContextManager
from typing_extensions import Buffer
from app.decoder.decoder_protocol import Decoder


class Request(Protocol):
    def decoder(self) -> Decoder:
        """Decoder for the format of this request."""
        ...

    def fps(self) -> float:
        """FPS calculated from the previous capture."""
        ...

    def delay_us(self) -> int:
        """Duration between the capture instant of the center of the frame
        and the current instant, microseconds."""
        ...

    def buffer(self) -> AbstractContextManager[Buffer]:
        """Context-managed Buffer containing a single frame.
        The encoding of this frame depends on the configuration
        of the camera."""
        ...

    def release(self) -> None:
        """Release the buffer back to the pool."""
        ...
