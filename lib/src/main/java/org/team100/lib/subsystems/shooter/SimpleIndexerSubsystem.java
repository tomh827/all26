package org.team100.lib.subsystems.shooter;

import org.team100.lib.logging.LoggerFactory;
import org.team100.lib.servo.LinearVelocityServo;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Indexer with simple controls. */
public class SimpleIndexerSubsystem extends SubsystemBase {
    private final LinearVelocityServo m_servo;

    public SimpleIndexerSubsystem(LoggerFactory log, LinearVelocityServo servo) {
        m_servo = servo;
    }

    public void setVelocityDirect(double velocityM_S) {
        m_servo.setVelocityDirect(velocityM_S);
    }

    public void setDutyCycle(double dutyCycle) {
        m_servo.setDutyCycle(dutyCycle);
    }

    public void stop() {
        m_servo.stop();
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
        m_servo.periodic();
    }
}
