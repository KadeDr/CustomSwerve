package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.AlgaeSubsystem;
import frc.robot.subsystems.CoralSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class RobotContainer {
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final ElevatorSubsystem m_elevatorSubsystem = new ElevatorSubsystem();
  private final AlgaeSubsystem m_algaeSubsystem = new AlgaeSubsystem();
  private final CoralSubsystem m_coralSubsystem = new CoralSubsystem();

  private final CommandPS5Controller m_driverController = new CommandPS5Controller(
      OperatorConstants.kDriverControllerPort);
  private final CommandPS4Controller m_armController = new CommandPS4Controller(OperatorConstants.kArmControllerPort);

  public RobotContainer() {
    configureBindings();

    m_driveSubsystem.setDefaultCommand(
        new RunCommand(
            () -> m_driveSubsystem.drive(
                -MathUtil.applyDeadband(m_driverController.getLeftY(), Constants.OIConstants.kDriveDeaband),
                -MathUtil.applyDeadband(m_driverController.getLeftX(), Constants.OIConstants.kDriveDeaband),
                -MathUtil.applyDeadband(m_driverController.getRightX(), Constants.OIConstants.kDriveDeaband),
                true),
            m_driveSubsystem));
  }

  private void configureBindings() {
    m_armController.L2().whileTrue(m_elevatorSubsystem.MoveElevator(new Rotation2d(0, 0), new Rotation2d(0, 0)));
    m_armController.L1().whileTrue(m_elevatorSubsystem.MoveElevator(new Rotation2d(0, 5), new Rotation2d(0, 5)));
    m_armController.R2().whileTrue(m_elevatorSubsystem.MoveElevator(new Rotation2d(0, 10), new Rotation2d(0, 10)));
    m_armController.R1().whileTrue(m_elevatorSubsystem.MoveElevator(new Rotation2d(0, 15), new Rotation2d(0, 15)));
    m_armController.povDown().whileTrue(m_algaeSubsystem.MoveAlgaeModule(1, new Rotation2d(0, -10)));
    m_armController.povUp().whileTrue(m_algaeSubsystem.MoveAlgaeModule(0, new Rotation2d(0, 0)));
    m_armController.povLeft().whileTrue(m_algaeSubsystem.MoveAlgaeModule(-1, new Rotation2d(0, 0)));
    m_armController.triangle().whileTrue(m_coralSubsystem.MoveCoralIntake(1, new Rotation2d(10, 10)));
    m_armController.triangle().whileFalse(m_coralSubsystem.MoveCoralIntake(0, new Rotation2d(0, 0)));
    m_armController.square().whileTrue(m_coralSubsystem.MoveCoralIntake(0, new Rotation2d(0, 0)));
  }
}
