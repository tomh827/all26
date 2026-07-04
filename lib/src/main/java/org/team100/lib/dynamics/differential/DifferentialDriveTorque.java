package org.team100.lib.dynamics.differential;

/**
 * Here "torque" really means "wheel force" in Newtons
 */
public record DifferentialDriveTorque(
        double F1, double F2) {

}
