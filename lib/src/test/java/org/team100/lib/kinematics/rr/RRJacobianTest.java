package org.team100.lib.kinematics.rr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.team100.lib.geometry.r2.VelocityR2;
import org.team100.lib.geometry.rr.RRConfig;
import org.team100.lib.geometry.rr.RRVelocity;

public class RRJacobianTest {

    public void verify(double x, double y, VelocityR2 v) {
        assertEquals(x, v.x(), 1e-3);
        assertEquals(y, v.y(), 1e-3);
    }

    @Test
    void testForward() {
        RRKinematics k = new AnalyticRRKinematics(1, 1);
        RRJacobian j = new RRJacobian(k);
        VelocityR2 xdot = j.forward(new RRConfig(0, 0), new RRVelocity(0, 0));
        verify(0, 0, xdot);
        xdot = j.forward(new RRConfig(0, 0), new RRVelocity(1, 0));
        verify(0, 2, xdot);
        xdot = j.forward(new RRConfig(0, 0), new RRVelocity(0, 1));
        verify(0, 1, xdot);
        xdot = j.forward(new RRConfig(Math.PI / 2, -Math.PI / 2), new RRVelocity(0, 0));
        verify(0, 0, xdot);
        xdot = j.forward(new RRConfig(Math.PI / 2, -Math.PI / 2), new RRVelocity(1, 0));
        verify(-0.707, 0.707, xdot);
        xdot = j.forward(new RRConfig(Math.PI / 2, -Math.PI / 2), new RRVelocity(0, 1));
        verify(-1, 0, xdot);

    }

}
