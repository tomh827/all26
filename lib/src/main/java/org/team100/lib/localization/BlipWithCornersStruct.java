package org.team100.lib.localization;

import java.nio.ByteBuffer;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.util.struct.Struct;

public class BlipWithCornersStruct implements Struct<BlipWithCorners> {

    @Override
    public Class<BlipWithCorners> getTypeClass() {
        return BlipWithCorners.class;
    }

    @Override
    public String getTypeName() {
        return "BlipWithCorners";
    }

    @Override
    public int getSize() {
        return kSizeInt64 + kSizeInt32 + kSizeDouble * 8 + Transform3d.struct.getSize();
    }

    @Override
    public String getSchema() {
        return "int64 timestamp; int32 id; double corners[8]; Transform3d pose";
    }

    @Override
    public Struct<?>[] getNested() {
        return new Struct<?>[] { Transform3d.struct };
    }

    @Override
    public BlipWithCorners unpack(ByteBuffer bb) {
        long timestamp = bb.getLong();
        int id = bb.getInt();
        double[] corners = Struct.unpackDoubleArray(bb, 8);
        Transform3d pose = Transform3d.struct.unpack(bb);
        return new BlipWithCorners(timestamp, id, corners, pose);
    }

    @Override
    public void pack(ByteBuffer bb, BlipWithCorners value) {
        bb.putLong(value.getTimestamp());
        bb.putInt(value.getId());
        Struct.packArray(bb, value.getCorners());
        Transform3d.struct.pack(bb, value.getRawPose());
    }

}
