package frc.robot.subsystems.Elevator;

import edu.wpi.first.math.geometry.Rotation2d;

public class ElevatorModuleState {
    // Same thing as CoralModuleState, but you don't need the third ElevatorModuleState because we don't have any indefinitely spinning motors.
    public Rotation2d leftMotorAngle;
    public Rotation2d rightMotorAngle;

    public ElevatorModuleState() {}
    public ElevatorModuleState(Rotation2d leftMotorAngle, Rotation2d rightMotorAngle) {
        this.leftMotorAngle = leftMotorAngle;
        this.rightMotorAngle = rightMotorAngle;
    }
}
