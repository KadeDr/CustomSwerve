package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class MaxSwerveModule {
    private final SparkMax m_drivingSpark;
    private final SparkMax m_turningSpark;

    private final RelativeEncoder m_drivingEncoder;
    private final AbsoluteEncoder m_turningEncoder;

    private final SparkClosedLoopController m_drivingClosedLoopController;
    private final SparkClosedLoopController m_turningClosedLoopController;

    private double m_chassisAngularOffset;
    private SwerveModuleState m_desiredState = new SwerveModuleState(0.0, new Rotation2d());

    private final MotorType m_motorType = MotorType.kBrushless;

    public MaxSwerveModule(int drivingCANId, int turningCANId, double chassisAngularOffset) {
        m_drivingSpark = new SparkMax(drivingCANId, m_motorType);
        m_turningSpark = new SparkMax(turningCANId, m_motorType);

        m_drivingEncoder = m_drivingSpark.getEncoder();
        m_turningEncoder = m_turningSpark.getAbsoluteEncoder();

        m_drivingClosedLoopController = m_drivingSpark.getClosedLoopController();
        m_turningClosedLoopController = m_turningSpark.getClosedLoopController();

        m_chassisAngularOffset = chassisAngularOffset;
        m_desiredState.angle = new Rotation2d(m_turningEncoder.getPosition());
        m_drivingEncoder.setPosition(0);
    }

    public SwerveModuleState GetState() {
        return new SwerveModuleState(m_drivingEncoder.getVelocity(),
        new Rotation2d(m_turningEncoder.getPosition() - m_chassisAngularOffset));
    }

    public SwerveModulePosition GetPosition() {
        return new SwerveModulePosition(m_drivingEncoder.getPosition(),
        new Rotation2d(m_turningEncoder.getPosition() - m_chassisAngularOffset));
    };

    public void SetDesiredState(SwerveModuleState desiredState) {
        SwerveModuleState correctedState = new SwerveModuleState();
        correctedState.speedMetersPerSecond = desiredState.speedMetersPerSecond;
        correctedState.angle = desiredState.angle.plus(Rotation2d.fromRadians(m_chassisAngularOffset));

        correctedState.optimize(new Rotation2d(m_turningEncoder.getPosition()));

        m_drivingClosedLoopController.setReference(correctedState.speedMetersPerSecond, ControlType.kVelocity);
        m_turningClosedLoopController.setReference(correctedState.angle.getRadians(), ControlType.kPosition);

        m_desiredState = desiredState;
    }

    public void ResetEncoders() {
        m_drivingEncoder.setPosition(0);
    }
}
