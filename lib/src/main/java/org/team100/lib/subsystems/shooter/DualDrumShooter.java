package org.team100.lib.subsystems.shooter;

import org.team100.lib.logging.Level;
import org.team100.lib.logging.LoggerFactory;
import org.team100.lib.logging.LoggerFactory.BooleanLogger;
import org.team100.lib.servo.LinearVelocityServo;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Shooter with left and right drums.
 */
public class DualDrumShooter extends SubsystemBase {
    private final LinearVelocityServo m_left;
    private final LinearVelocityServo m_right;
    private final BooleanLogger m_log_atGoal;

    public DualDrumShooter(
            LoggerFactory log,
            LinearVelocityServo left,
            LinearVelocityServo right) {
        m_left = left;
        m_right = right;
        m_log_atGoal = log.booleanLogger(Level.TRACE, "At goal");
    }

    /**
     * Must be called periodically to progress through the profile.
     * 
     * Will not work in Command.initialize().
     */
    public void setVelocityProfiled(double velocityM_S) {
        m_left.setVelocityProfiled(velocityM_S);
        m_right.setVelocityProfiled(velocityM_S);
    }

    public void setVelocityDirect(double velocityM_S) {
        m_left.setVelocityDirect(velocityM_S);
        m_right.setVelocityDirect(velocityM_S);
    }

    public void setDutyCycle(double dutyCycle) {
        m_left.setDutyCycle(dutyCycle);
        m_right.setDutyCycle(dutyCycle);
    }

    public double get() {
        return (m_left.getVelocity() + m_right.getVelocity()) / 2;
    }

    public void stop() {
        m_left.stop();
        m_right.stop();
    }

    public boolean atGoal() {
        return m_right.atGoal() && m_left.atGoal();
    }

    public Command spinProfiled(double velocityM_S) {
        return run(() -> {
            setVelocityProfiled(velocityM_S);
        });
    }

    public Command spinDirect(double velocityM_S) {
        return run(() -> {
            setVelocityDirect(velocityM_S);
        });
    }

    public Command spinDutyCycle(double dutyCycle) {
        return run(() -> {
            setDutyCycle(dutyCycle);
        });
    }

    @Override
    public void periodic() {
        m_left.periodic();
        m_right.periodic();
        m_log_atGoal.log(this::atGoal);
    }
}