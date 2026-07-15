package org.team100.lib.kinematics.rr;

import org.team100.lib.geometry.rr.RRConfig;
import org.team100.lib.geometry.rr.RRPosition;

import edu.wpi.first.math.geometry.Translation2d;

public interface RRKinematics {

    /** Workspace end location based on joint configuration. */
    RRPosition forward(RRConfig a);

    /**
     * Calculate joint configuration given the workspace location of the end.
     * 
     * It's an application of the law of cosines.
     * 
     * Refer to the diagram:
     * https://docs.google.com/document/d/1B6vGPtBtnDSOpfzwHBflI8-nn98W9QvmrX78bon8Ajw
     */
    RRConfig inverse(Translation2d t);

    double l1();
    double l2();

}