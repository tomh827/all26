package org.team100.lib.kinematics.rr;

import org.team100.lib.geometry.r2.VelocityR2;
import org.team100.lib.geometry.rr.RRConfig;
import org.team100.lib.geometry.rr.RRVelocity;

import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.numbers.N2;

/** Jacobian for the RR apparatus. */
public class RRJacobian {
    private final RRKinematics m_k;
    private final double l1;
    private final double l2;

    public RRJacobian(RRKinematics k) {
        m_k = k;
        l1 = k.l1();
        l2 = k.l2();
    }

    public VelocityR2 forward(RRConfig q, RRVelocity qdot) {
        Matrix<N2, N2> J = J(q);
        return VelocityR2.fromVector2(J.times(qdot.toVector()));
    }

    public RRVelocity inverse(Translation2d x, VelocityR2 xdot) {
        RRConfig q = m_k.inverse(x);
        Matrix<N2, N2> Jinv = Jinv(q);
        return RRVelocity.fromVector(Jinv.times(xdot.toVector()));
    }

    ////////////////////////////////////////

    private Matrix<N2, N2> J(RRConfig q) {
        double s1 = Math.sin(q.q1());
        double c1 = Math.cos(q.q1());
        double s12 = Math.sin(q.q1() + q.q2());
        double c12 = Math.cos(q.q1() + q.q2());
        return MatBuilder.fill(Nat.N2(), Nat.N2(),
                -l1 * s1 - l2 * s12, -l2 * s12, //
                l1 * c1 + l2 * c12, l2 * c12);
    }

    private Matrix<N2, N2> Jinv(RRConfig q) {
        Matrix<N2, N2> J = J(q);
        if (Math.abs(J.det()) < 1e-3) {
            // not invertible
            System.out.printf("WARNING: zero jacobian for config %s\n", q.toString());
            return new Matrix<>(Nat.N2(), Nat.N2());
        }
        return J.inv();
    }
}
