package frc.robot.subsystems;

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.ADIS16470_IMU.IMUAxis;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {

    private final MaxSwerveModule m_frontLeft = new MaxSwerveModule(
        Constants.DriverConstants.kFrontLeftDrivingCAN,
        Constants.DriverConstants.kFrontLeftTurningCAN,
        Constants.DriverConstants.kFrontLeftChassisAngularOffset);

    private final MaxSwerveModule m_frontRight = new MaxSwerveModule(
        Constants.DriverConstants.kFrontRightDrivingCAN,
        Constants.DriverConstants.kFrontRightTurningCAN,
        Constants.DriverConstants.kFrontRightChassisAngularOffset);

    private final MaxSwerveModule m_backLeft = new MaxSwerveModule(
        Constants.DriverConstants.kBackLeftDrivingCAN,
        Constants.DriverConstants.kBackLeftTurningCAN,
        Constants.DriverConstants.kBackLeftChassisAngularOffset);

    private final MaxSwerveModule m_backRight = new MaxSwerveModule(
        Constants.DriverConstants.kBackRightDrivingCAN,
        Constants.DriverConstants.kBackRightTurningCAN,
        Constants.DriverConstants.kBackRightChassisAngularOffset);

    private final ADIS16470_IMU m_gyro = new ADIS16470_IMU();

    public boolean DriveCondition() {
        return false;
    }

    SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(
        Constants.DriverConstants.kDriveKinematics,
        Rotation2d.fromDegrees(m_gyro.getAngle(IMUAxis.kZ)),
        new SwerveModulePosition[] {
            m_frontLeft.GetPosition(),
            m_frontRight.GetPosition(),
            m_backLeft.GetPosition(),
            m_backRight.GetPosition()
        });

    public DriveSubsystem() {
        HAL.report(tResourceType.kResourceType_RobotDrive, tInstances.kRobotDriveSwerve_MaxSwerve);
    }

    @Override
    public void periodic() {
        m_odometry.update(
            Rotation2d.fromDegrees(m_gyro.getAngle(IMUAxis.kZ)),
            new SwerveModulePosition[] {
                m_frontLeft.GetPosition(),
                m_frontRight.GetPosition(),
                m_backLeft.GetPosition(),
                m_backRight.GetPosition()
            });
    }

    public Pose2d GetPose() {
        return m_odometry.getPoseMeters();
    }

    public void ResetOdometry(Pose2d pose) {
        m_odometry.resetPosition(
            Rotation2d.fromDegrees(m_gyro.getAngle(IMUAxis.kZ)),
            new SwerveModulePosition[] {
                m_frontLeft.GetPosition(),
                m_frontRight.GetPosition(),
                m_backLeft.GetPosition(),
                m_backRight.GetPosition()
            },
            pose);
    }

    public void drive(double xSpeed, double ySpeed, double rotation, boolean fieldRelative) {
        double xSpeedDelivered = xSpeed * Constants.DriverConstants.kMaxSpeedMetersPerSecond;
        double ySpeedDelivered = ySpeed * Constants.DriverConstants.kMaxSpeedMetersPerSecond;
        double rotationDelivered = rotation * Constants.DriverConstants.kMaxAngularSpeed;

        var swerveModuleStates = Constants.DriverConstants.kDriveKinematics.toSwerveModuleStates(
            fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotationDelivered,
                    Rotation2d.fromDegrees(m_gyro.getAngle(IMUAxis.kZ)))
                : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotationDelivered));
        SwerveDriveKinematics.desaturateWheelSpeeds(
            swerveModuleStates, Constants.DriverConstants.kMaxSpeedMetersPerSecond);
        m_frontLeft.SetDesiredState(swerveModuleStates[0]);
        m_frontRight.SetDesiredState(swerveModuleStates[1]);
        m_backLeft.SetDesiredState(swerveModuleStates[2]);
        m_backRight.SetDesiredState(swerveModuleStates[3]);
    }

    public void SetX() {
        m_frontLeft.SetDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
        m_frontRight.SetDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
        m_backLeft.SetDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
        m_backRight.SetDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
    }

    public void SetModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(
            desiredStates, Constants.DriverConstants.kMaxSpeedMetersPerSecond);
        m_frontLeft.SetDesiredState(desiredStates[0]);
        m_frontRight.SetDesiredState(desiredStates[1]);
        m_backLeft.SetDesiredState(desiredStates[2]);
        m_backRight.SetDesiredState(desiredStates[3]);
    }

    public void ResetEncoders() {
        m_frontLeft.ResetEncoders();
        m_frontRight.ResetEncoders();
        m_backLeft.ResetEncoders();
        m_backRight.ResetEncoders();
    }

    public void ZeroHeading() {
        m_gyro.reset();
    }

    public double GetHeading() {
        return Rotation2d.fromDegrees(m_gyro.getAngle(IMUAxis.kZ)).getDegrees();
    }

    public double GetTurnRate() {
        return m_gyro.getRate(IMUAxis.kZ) * (Constants.DriverConstants.kGyroReversed ? -1.0 : 1.0);
    }
}
