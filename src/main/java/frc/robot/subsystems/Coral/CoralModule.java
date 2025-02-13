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

    // Relative encoder on the intake motor. Use a relative encoder to set the speed of a motor.
    private final RelativeEncoder m_spinEncoder;
    // Absolute encoder on the rotate motor (to rotate the encoder). Use an absolute encoder to set the rotation of a motor
    private final AbsoluteEncoder m_rotateEncoder;

    // Closed loop controller for the spin motor.
    // Used to set a state(position/speed) on a motor in a loop. This is how you utilize encoders
    private final SparkClosedLoopController m_spinClosedLoopController;
    private final SparkClosedLoopController m_rotateClosedLoopController;

    // Look into the SwerveModuleState java file to figure out what this does
    private CoralModuleState m_desiredState = new CoralModuleState(0.0, new Rotation2d());

    // Just my motor type. Change this to kBrushed if you use brushed motors :D
    private final MotorType m_motorType = MotorType.kBrushless;

    public CoralModule(int spinCANId, int rotateCANId) {
        // Initializes the spinSpark and rotateSpark.
        m_spinSpark = new SparkMax(spinCANId, m_motorType);
        m_rotateSpark = new SparkMax(rotateCANId, m_motorType);

        // Initializes the encoders. Since the encoders are on the sparks, they will get the encoder from the spark.
        m_spinEncoder = m_spinSpark.getEncoder();
        m_rotateEncoder = m_rotateSpark.getAbsoluteEncoder();

        // Initializes the CLC. Since you are utilizing this on the sparks, you will get it from the spark instance.
        m_spinClosedLoopController = m_spinSpark.getClosedLoopController();
        m_rotateClosedLoopController = m_rotateSpark.getClosedLoopController();
    }

    // Gets the state(position) of the coral module. In our case, we won't really utilize it, but it is good to have ig
    public CoralModuleState GetState() {
        return new CoralModuleState(m_spinEncoder.getVelocity(),
        new Rotation2d(m_rotateEncoder.getPosition()));
    }

    // Sets the state(position) of the coral module. This is what actually makes your module do what you want it to do.
    public void SetDesiredState(CoralModuleState desiredState) {
        // Sets the reference (position) that the motors should be going to. Use kVelocity for motors that will be spinning indefinitely
        // and use kPosition for motors that will be moving to a specific position and no more, such as turning on a swerve chassis.
        m_spinClosedLoopController.setReference(desiredState.speedMetersPerSecond, ControlType.kVelocity);
        m_rotateClosedLoopController.setReference(desiredState.angle.getRadians(), ControlType.kPosition);

        // Just set your global desired state to be equal to your local desired state. Basically just does GetState without having to call it.
        m_desiredState = desiredState;
    }
}
