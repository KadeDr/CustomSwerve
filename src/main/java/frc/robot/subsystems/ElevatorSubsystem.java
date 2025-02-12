package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator.ElevatorModule;
import frc.robot.subsystems.Elevator.ElevatorModuleState;

public class ElevatorSubsystem extends SubsystemBase {
    private final ElevatorModule elevator = new ElevatorModule(Constants.ArmConstants.kLeftElevatorCAN, Constants.ArmConstants.kRightElevatorCAN);

    public Command MoveElevator(Rotation2d leftMotor, Rotation2d rightMotor) {
        return runOnce(
            () -> {
                elevator.SetDesiredState(new ElevatorModuleState(leftMotor, rightMotor));
            }
        );
    }
}