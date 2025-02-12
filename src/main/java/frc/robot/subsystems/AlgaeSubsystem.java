package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.Algae.AlgaeModule;
import frc.robot.subsystems.Algae.AlgaeModuleState;

public class AlgaeSubsystem extends SubsystemBase {
    private final AlgaeModule m_algae = new AlgaeModule(Constants.ArmConstants.kAlgaeSpinCAN, Constants.ArmConstants.kAlgaeRotateCAN);

    public Command MoveAlgaeModule(double speed, Rotation2d position) {
        return runOnce(
            () -> {
                m_algae.SetDesiredState(new AlgaeModuleState(speed, position));
            }
        );
    }
}
