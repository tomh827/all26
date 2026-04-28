package org.team100.lib.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * Illustrates a command with a condition. This can also be done using the
 * "fluent" method shown in RobotContainer.
 */
public class Shoot extends Command {
    private final DualDrumShooter m_shooter;
    private final PWMIndexerServo m_indexer;

    public Shoot(DualDrumShooter shooter, PWMIndexerServo indexer) {
        m_shooter = shooter;
        m_indexer = indexer;
        addRequirements(m_shooter, m_indexer);
    }

    @Override
    public void execute() {
        m_shooter.spinDirect(10);
        if (m_shooter.atGoal()) {
            m_indexer.set(1);
        }
    }
}
