package org.team100.lib.dynamics.differential;

/**
 * Wheel accelerations (left, right). In our usual
 * arrangement, these are *linear* accelerations, the
 * rotational wheel aspect is encapsulated in the linear
 * mechanism.
 */
public record DifferentialDriveAcceleration(
        double q1ddot, double q2ddot) {
}
