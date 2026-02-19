"""This is the coprocessor main loop.

Each task is run by its own Looper, in its own thread.

You can't run this from the command line.  To run the app,
use the script called "runapp.py" in the raspberry_pi directory
(one level above this one).
"""

# pylint: disable=R0914

import time
import ntcore
from app.network.network import SyncRequest, SyncReply

estimate: int = 0


def offset(org: int, rec: int, xmt: int, dst: int) -> int:
    return (rec + xmt - dst - org) // 2


def fuse(measurement: int) -> None:
    global estimate
    diff = estimate - measurement
    if abs(diff) > 1000000:
        # large change, step right to the measurement
        estimate = measurement
    else:
        estimate = (estimate * 99 + measurement) // 100


def main() -> None:
    print("*** main")

    inst = ntcore.NetworkTableInstance.getDefault()
    inst.startClient4("sync_client")
    inst.setServer("10.1.0.2")

    request_pub = inst.getStructTopic("syncrequest", SyncRequest).publish()

    reply_sub = inst.getStructTopic("syncreply", SyncReply).subscribe(
        SyncReply(0, 0, 0)
    )

    estimate_pub = inst.getIntegerTopic("estimate").publish()

    drift_pub = inst.getDoubleTopic("drift (us)").publish()

    first_estimate = 0

    while True:

        request_pub.set(SyncRequest(ntcore._now()))

        queue: list[ntcore.TimestampedStruct] = reply_sub.readQueue()
        if queue:
            syncreply: SyncReply = queue[-1].value
            now = ntcore._now()
            measurement = offset(syncreply.org, syncreply.rec, syncreply.xmt, now)
            fuse(measurement)
            estimate_pub.set(estimate)

            if first_estimate == 0:
                first_estimate = estimate

            drift = estimate - first_estimate
            drift_pub.set(drift)

        # avoid spinning too fast
        time.sleep(0.02)
