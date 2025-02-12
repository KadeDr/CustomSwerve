package frc.robot.subsystems.Coral;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;

public class CoralModule {
    private final SparkMax m_spinSpark;
    private final SparkMax m_rotateSpark;

    private final RelativeEncoder m_spinEncoder;
    private final AbsoluteEncoder m_rotateEncoder;

    private final SparkClosedLoopController m_spinClosedLoopController;
    private final SparkClosedLoopController m_rotateClosedLoopController;

    private CoralModuleState m_desiredState = new CoralModuleState(0.0, new Rotation2d());

    private final MotorType m_motorType = MotorType.kBrushless;

    public CoralModule(int spinCANId, int rotateCANId) {
        m_spinSpark = new SparkMax(spinCANId, m_motorType);
        m_rotateSpark = new SparkMax(rotateCANId, m_motorType);

        m_spinEncoder = m_spinSpark.getEncoder();
        m_rotateEncoder = m_rotateSpark.getAbsoluteEncoder();

        m_spinClosedLoopController = m_spinSpark.getClosedLoopController();
        m_rotateClosedLoopController = m_rotateSpark.getClosedLoopController();
    }

    public CoralModuleState GetState() {
        return new CoralModuleState(m_spinEncoder.getVelocity(),
        new Rotation2d(m_rotateEncoder.getPosition()));
    }

    public void SetDesiredState(CoralModuleState desiredState) {
        m_spinClosedLoopController.setReference(desiredState.speedMetersPerSecond, ControlType.kVelocity);
        m_spinClosedLoopController.setReference(desiredState.angle.getRadians(), ControlType.kPosition);

        m_desiredState = desiredState;
    }
}
