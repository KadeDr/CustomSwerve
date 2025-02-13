package frc.robot.subsystems.Algae;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;

public class AlgaeModule {
    // Cough cough... this is literally like the EXACT same as the CoralModule and CoralModuleState....
    private final SparkMax m_spinSpark;
    private final SparkMax m_rotateSpark;

    private final RelativeEncoder m_spinEncoder;
    private final AbsoluteEncoder m_rotateEncoder;

    private final SparkClosedLoopController m_spinClosedLoopController;
    private final SparkClosedLoopController m_rotateClosedLoopController;

    private AlgaeModuleState m_desiredState = new AlgaeModuleState(0.0, new Rotation2d());

    private final MotorType m_motorType = MotorType.kBrushless;

    public AlgaeModule(int spinCANId, int rotateCANId) {
        m_spinSpark = new SparkMax(spinCANId, m_motorType);
        m_rotateSpark = new SparkMax(rotateCANId, m_motorType);

        m_spinEncoder = m_spinSpark.getEncoder();
        m_rotateEncoder = m_rotateSpark.getAbsoluteEncoder();

        m_spinClosedLoopController = m_spinSpark.getClosedLoopController();
        m_rotateClosedLoopController = m_rotateSpark.getClosedLoopController();

        m_desiredState.angle = new Rotation2d(m_rotateEncoder.getPosition());
        m_spinEncoder.setPosition(0);
    }

    public AlgaeModuleState GetState() {
        return new AlgaeModuleState(m_spinEncoder.getVelocity(),
        new Rotation2d(m_rotateEncoder.getPosition()));
    }

    public void SetDesiredState(AlgaeModuleState desiredState) {
        m_spinClosedLoopController.setReference(desiredState.speedMetersPerSecond, ControlType.kVelocity);
        m_rotateClosedLoopController.setReference(desiredState.angle.getRadians(), ControlType.kPosition);

        m_desiredState = desiredState;
    }
}
