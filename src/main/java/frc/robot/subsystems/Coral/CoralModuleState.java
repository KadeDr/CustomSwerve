package frc.robot.subsystems.Coral;

import edu.wpi.first.math.geometry.Rotation2d;
import static edu.wpi.first.units.Units.MetersPerSecond;
import edu.wpi.first.units.measure.LinearVelocity;

public class CoralModuleState {
    public double speedMetersPerSecond;
    public Rotation2d angle;

    public CoralModuleState() {}
    public CoralModuleState(double speedMetersPerSecond, Rotation2d angle) {
        this.speedMetersPerSecond = speedMetersPerSecond;
        this.angle = angle;
    }
    public CoralModuleState(LinearVelocity speed, Rotation2d angle) {
        this(speed.in(MetersPerSecond), angle);
    }
}
