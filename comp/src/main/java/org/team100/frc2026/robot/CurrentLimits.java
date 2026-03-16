package org.team100.frc2026.robot;

public class CurrentLimits {
    public static final double intakeStatorCurrentLimit = 50;
    public static final double intakeSupplyCurrentLimit = 30;

    public static final double intakeExtendStatorCurrentLimit = 50;
    public static final double intakeExtendSupplyCurrentLimit = 30;

    public static final double conveyorStatorCurrentLimit = 50;
    public static final double conveyorSupplyCurrentLimit = 30;

    // tuned 16/03/2026
    public static final double feederStatorCurrentLimit = 50;
    public static final double feederSupplyCurrentLimit = 30;

    public static final double shooterStatorCurrentLimit = 60;
    public static final double shooterSupplyCurrentLimit = 40;

    public static final double shooterHoodStatorCurrentLimit = 50;
    public static final double shooterHoodSupplyCurrentLimit = 30;

    // 3/14/26 lowered from 110 to 80
    public static final double DRIVE_STATOR_LIMIT = 90;
    public static final double DRIVE_SUPPLY_LIMIT = 60;
}
