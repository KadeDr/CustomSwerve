package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator.ElevatorModule;
import frc.robot.subsystems.Elevator.ElevatorModuleState;

public class ElevatorSubsystem extends SubsystemBase {
    // This is literally the exact same as AlgaeSubsystem, just with 2 Rotation2d values instead of 1 Rotation2d and 1 double
    private final ElevatorModule elevator = new ElevatorModule(Constants.ArmConstants.kLeftElevatorCAN, Constants.ArmConstants.kRightElevatorCAN);

    public Command MoveElevator(Rotation2d leftMotor, Rotation2d rightMotor) {
        return runOnce(
            () -> {
                elevator.SetDesiredState(new ElevatorModuleState(leftMotor, rightMotor));
            }
        );
    }
}