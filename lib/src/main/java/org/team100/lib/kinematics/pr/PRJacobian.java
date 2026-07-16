package org.team100.lib.kinematics.pr;

import org.team100.lib.geometry.pr.PRConfig;
import org.team100.lib.geometry.pr.PRVelocity;
import org.team100.lib.geometry.r2.VelocityR2;

import edu.wpi.first.math.geometry.Translation2d;

/** Jacobian for the PR apparatus. */
public class PRJacobian {
    private final PRKinematics m_k;
    private final double l;

    public PRJacobian(PRKinematics k) {
        m_k = k;
        l = k.l();
    }

    public VelocityR2 forward(PRConfig q, PRVelocity qdot) {
        return null;
    }

    public PRVelocity inverse(Translation2d x, VelocityR2 xdot) {
        return null;
    }

}
