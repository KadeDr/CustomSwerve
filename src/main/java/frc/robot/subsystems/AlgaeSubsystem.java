package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.Algae.AlgaeModule;
import frc.robot.subsystems.Algae.AlgaeModuleState;

public class AlgaeSubsystem extends SubsystemBase {
    // Creates a new algae module, providing the CANId's.
    private final AlgaeModule m_algae = new AlgaeModule(Constants.ArmConstants.kAlgaeSpinCAN, Constants.ArmConstants.kAlgaeRotateCAN);

    // The command to move the algaeModule. We don't set the speed we want here, and just give more parameters to call later.
    // Allows for using only 1 command, rather than 2 or 3 or 4, etc.
    public Command MoveAlgaeModule(double speed, Rotation2d position) {
        return runOnce(
            () -> {
                m_algae.SetDesiredState(new AlgaeModuleState(speed, position));
            }
        );
    }
}
