package org.team100.lib.localization;

import org.team100.lib.geometry.GeometryUtil;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

/**
 * Mirrors raspberry_pi BlipWithCorners.
 * 
 * Includes raw pixel positions.
 */
public class BlipWithCorners {
    private final long timestamp;
    private final int id;
    private final double[] corners;
    private final Transform3d pose;

    /**
     * @param timestamp server time microseconds
     * @param id        AprilTag id
     * @param corners   Four corners. Lower left first, then counter-clockwise.
     * @param pose      This uses the camera coordinate system, which has X to
     *                  the right, Y down, and Z forward.
     */
    public BlipWithCorners(long timestamp, int id, double[] corners, Transform3d pose) {
        this.timestamp = timestamp;
        this.id = id;
        this.corners = corners;
        this.pose = pose;
    }

    public static BlipWithCorners fromXForward(long timestamp, int id, double[] corners, Transform3d pose) {
        return new BlipWithCorners(timestamp, id, corners, new Transform3d(
                GeometryUtil.xForwardToZForward(pose.getTranslation()),
                GeometryUtil.xForwardToZForward(pose.getRotation())));
    }

    /**
     * Server time in microseconds. The timestamp of a blip is synchronized using
     * Sync, so it is assumed to be exactly in sync with the real server time; there
     * are no additional offsets.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * ID of the AprilTag.
     */
    public int getId() {
        return id;
    }

    /**
     * Pixel corners of the tag.
     */
    public double[] getCorners() {
        return corners;
    }

    /**
     * Raw transform produced by the camera. The camera's coordinate system has X to
     * the right, Y down, and Z forward, so this Transform3d should not be used
     * directly.
     */
    public Transform3d getRawPose() {
        return pose;
    }

    /**
     * Extract translation and rotation from z-forward blip and return the same
     * translation and rotation as an NWU x-forward transform. Package-private for
     * testing.
     */
    public Transform3d blipToTransform() {
        return new Transform3d(blipToTranslation(), blipToRotation());
    }

    @Override
    public String toString() {
        return "BlipWithCorners [timestamp = " + timestamp //
                + ", id=" + id //
                + ", pose=" + pose + "]";
    }

    public static final BlipWithCornersStruct struct = new BlipWithCornersStruct();

    /**
     * Extract the translation from a "z-forward" blip and return the same
     * translation expressed in our usual "x-forward" NWU translation.
     * It would be possible to also consume the blip rotation matrix, if it were
     * renormalized, but it's not very accurate, so we don't consume it.
     * Package-private for testing.
     */
    private Translation3d blipToTranslation() {
        return GeometryUtil.zForwardToXForward(pose.getTranslation());
    }

    /**
     * Extract the rotation from the "z forward" blip and return the same rotation
     * expressed in our usual "x forward" NWU coordinates. Package-private for
     * testing.
     */
    private Rotation3d blipToRotation() {
        return GeometryUtil.zForwardToXForward(pose.getRotation());
    }
}
