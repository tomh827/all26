package org.team100.lib.kinematics.rr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.team100.lib.geometry.rr.RRConfig;
import org.team100.lib.geometry.rr.RRPosition;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;

public class AnalyticRRKinematicsTest {
    private static final double DELTA = 0.001;

    @Test
    void testf1() {
        // stretched along x
        AnalyticRRKinematics k = new AnalyticRRKinematics(1, 1);
        RRPosition p = new RRPosition(
                new Translation2d(1, 0), new Translation2d(2, 0));
        RRConfig q = new RRConfig(0, 0);
        verify(k, p, q);
    }

    @Test
    void testf2() {
        // up and then out
        AnalyticRRKinematics k = new AnalyticRRKinematics(1, 1);
        RRPosition p = new RRPosition(
                new Translation2d(0, 1), new Translation2d(1, 1));
        RRConfig q = new RRConfig(Math.PI / 2, -1 * Math.PI / 2);
        verify(k, p, q);
    }

    @Test
    void testf3() {
        // equilateral triangle, first link up
        AnalyticRRKinematics k = new AnalyticRRKinematics(1, 1);
        RRPosition p = new RRPosition(
                new Translation2d(0, 1), new Translation2d(Math.sqrt(3) / 2, 0.5));
        RRConfig q = new RRConfig(Math.PI / 2, -2 * Math.PI / 3);
        verify(k, p, q);
    }

    @Test
    void test4() {
        // vertical equilateral triangle
        AnalyticRRKinematics k = new AnalyticRRKinematics(1, 1);
        RRPosition p = new RRPosition(
                new Translation2d(-Math.sqrt(3) / 2, 0.5), new Translation2d(0, 1));
        RRConfig q = new RRConfig(5 * Math.PI / 6, -2 * Math.PI / 3);
        verify(k, p, q);
    }

    @Test
    void test5() {
        // behind
        AnalyticRRKinematics k = new AnalyticRRKinematics(1, 1);
        RRPosition p = new RRPosition(
                new Translation2d(-1, 0), new Translation2d(-1, 1));
        RRConfig q = new RRConfig(Math.PI, -Math.PI / 2);
        verify(k, p, q);
    }

    void verify(AnalyticRRKinematics k, RRPosition p, RRConfig q) {
        verifyFwd(p, k.forward(q));
        verifyInv(q, k.inverse(p.p2()));
    }

    void verifyFwd(RRPosition expected, RRPosition actual) {
        assertEquals(expected.p1(), actual.p1(), "fwd p1");
        assertEquals(expected.p2(), actual.p2(), "fwd p2");
    }

    void verifyInv(RRConfig expected, RRConfig actual) {
        assertEquals(MathUtil.angleModulus(expected.q1()), MathUtil.angleModulus(actual.q1()), DELTA, "inv q1");
        assertEquals(MathUtil.angleModulus(expected.q2()), MathUtil.angleModulus(actual.q2()), DELTA, "inv q2");
    }
}