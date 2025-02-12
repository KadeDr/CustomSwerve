package frc.robot.subsystems.Elevator;

import edu.wpi.first.math.geometry.Rotation2d;

public class ElevatorModuleState {
    public Rotation2d leftMotorAngle;
    public Rotation2d rightMotorAngle;

    public ElevatorModuleState() {}
    public ElevatorModuleState(Rotation2d leftMotorAngle, Rotation2d rightMotorAngle) {
        this.leftMotorAngle = leftMotorAngle;
        this.rightMotorAngle = rightMotorAngle;
    }
}
