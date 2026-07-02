package org.team100.lib.dynamics.se2;

import org.team100.lib.geometry.AccelerationSE2;

public class SE2Dynamics {
    /** Mass. */
    private final double m;
    /** Moment of inertia. */
    private final double I;

    public SE2Dynamics(double m, double I) {
        this.m = m;
        this.I = I;
    }

    /**
     * Generalized force (torque or force) to achieve the required
     * acceleration.
     * 
     * There is no configuration (position) or velocity here because
     * the dynamics do not depend on them.
     */
    public SE2Torque torque(AccelerationSE2 a) {
        return new SE2Torque(m * a.x(), m * a.y(), I * a.theta());
    }

}
