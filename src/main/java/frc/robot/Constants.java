// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kArmControllerPort = 1;
  }

  public static final class ArmConstants {
    public static int kLeftElevatorCAN = 9;
    public static int kRightElevatorCAN = 10;

    public static int kAlgaeSpinCAN = 11;
    public static int kAlgaeRotateCAN = 12;

    public static int kCoralSpinCAN = 13;
    public static int kCoralRotatCAN = 14;
  }

  public static final class DriverConstants {
    public static int kFrontLeftDrivingCAN = 1;
    public static int kFrontLeftTurningCAN = 2;

    public static int kFrontRightDrivingCAN = 3;
    public static int kFrontRightTurningCAN = 4;

    public static int kBackLeftDrivingCAN = 5;
    public static int kBackLeftTurningCAN = 6;
    
    public static int kBackRightDrivingCAN = 7;
    public static int kBackRightTurningCAN = 8;

    public static final boolean kGyroReversed = false;

    public static final double kMaxSpeedMetersPerSecond = 4.8;
    public static final double kMaxAngularSpeed = 2 * Math.PI;

    public static final double kRobotWidth = Units.inchesToMeters(26);
    public static final double kRobotLength = Units.inchesToMeters(26);

    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
      new Translation2d(kRobotLength / 2, kRobotWidth / 2),
      new Translation2d(kRobotLength / 2, -kRobotWidth / 2),
      new Translation2d(-kRobotLength / 2, kRobotWidth / 2),
      new Translation2d(-kRobotLength / 2, -kRobotWidth / 2)
    );

    public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kBackLeftChassisAngularOffset = Math.PI;
    public static final double kBackRightChassisAngularOffset = Math.PI / 2;
  }

  public static final class ModuleConstants {
    public static final int kDrivingMotorPinionTeeth = 14;
    
    public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    public static final double kWheelDiameterMeters = 0.0762;
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;

    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters) / kDrivingMotorReduction;
  }

  public static final class OIConstants {
    public static final double kDriveDeaband = 0.05;
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }
}
