package frc.robot.subsystems.Coral;

import edu.wpi.first.math.geometry.Rotation2d;
import static edu.wpi.first.units.Units.MetersPerSecond;
import edu.wpi.first.units.measure.LinearVelocity;

public class CoralModuleState {
    // The speed of the sping motor in.. meters.. per.. second... ;)
    public double speedMetersPerSecond;
    // The angle fo the rotate motor. Where do you want the position of the arm/intake arm to be?
    public Rotation2d angle;

    // Use this for your identifier. Basically, if you don't want to give it any parameters, you have this so you don't have to.
    public CoralModuleState() {}
    // Use this for your identifier for certain applications. You would utilize this for your m_desiredState
    public CoralModuleState(double speedMetersPerSecond, Rotation2d angle) {
        // Sets the global speedMetersPerSecond and angle to its corresponding parameters
        this.speedMetersPerSecond = speedMetersPerSecond;
        this.angle = angle;
    }
    // Use this when you check your state. This will basically tell you the speed of the spin motor, and the position of the rotate motor
    public CoralModuleState(LinearVelocity speed, Rotation2d angle) {
        this(speed.in(MetersPerSecond), angle);
    }
}
