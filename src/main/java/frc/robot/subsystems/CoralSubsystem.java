package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.Coral.CoralModule;
import frc.robot.subsystems.Coral.CoralModuleState;

public class CoralSubsystem extends SubsystemBase {
    private final CoralModule m_coral = new CoralModule(Constants.ArmConstants.kCoralSpinCAN, Constants.ArmConstants.kCoralRotatCAN);

    public Command MoveCoralIntake(double speed, Rotation2d position) {
        return runOnce(
            () -> {
                m_coral.SetDesiredState(new CoralModuleState(speed, position));
            }
        );
    }
}
