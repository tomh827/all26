package org.team100.lib.dynamics.differential;

import org.team100.lib.geometry.AccelerationSE2;

public class DifferentialDriveDynamics {
    private final double m_m;
    private final double m_I;
    private final double m_W;

    public DifferentialDriveDynamics(
            double m, double I, double trackWidthM) {
        m_m = m;
        m_I = I;
        m_W = trackWidthM / 2;
    }

    public DifferentialDriveTorque torque(
            AccelerationSE2 a) {
        return new DifferentialDriveTorque(
                (m_m * a.x() - m_I * a.theta() / m_W) / 2,
                (m_m * a.x() + m_I * a.theta() / m_W) / 2);
    }

}
