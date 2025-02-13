package frc.robot.subsystems.Elevator;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;

public class ElevatorModule {
    // You know.. this script is eerily similar to CoralModule.java.... but with 2 absoltue encoders instead of 1 relative and 1 absolute.
    // Similar concept, no need for comments on this one.

    private final SparkMax m_leftSpark;
    private final SparkMax m_rightSpark;

    private final AbsoluteEncoder m_leftEncoder;
    private final AbsoluteEncoder m_rightEncoder;

    private final SparkClosedLoopController m_leftClosedLoopController;
    private final SparkClosedLoopController m_rightClosedLoopController;

    private ElevatorModuleState m_desiredState = new ElevatorModuleState(new Rotation2d(), new Rotation2d());

    private final MotorType m_motorType = MotorType.kBrushless;

    public ElevatorModule(int leftCANId, int rightCANId) {
        m_leftSpark = new SparkMax(leftCANId, m_motorType);
        m_rightSpark = new SparkMax(rightCANId, m_motorType);

        m_leftEncoder = m_leftSpark.getAbsoluteEncoder();
        m_rightEncoder = m_rightSpark.getAbsoluteEncoder();

        m_leftClosedLoopController = m_leftSpark.getClosedLoopController();
        m_rightClosedLoopController = m_rightSpark.getClosedLoopController();

        m_desiredState.leftMotorAngle = new Rotation2d(m_leftEncoder.getPosition());
        m_desiredState.rightMotorAngle = new Rotation2d(m_rightEncoder.getPosition());
    }

    public void SetDesiredState(ElevatorModuleState desiredState) {
        m_leftClosedLoopController.setReference(desiredState.leftMotorAngle.getRadians(), ControlType.kPosition);
        m_rightClosedLoopController.setReference(desiredState.rightMotorAngle.getRadians(), ControlType.kPosition);

        m_desiredState = desiredState;
    }
}