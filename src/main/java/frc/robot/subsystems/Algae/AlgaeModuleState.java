package frc.robot.subsystems.Algae;

import edu.wpi.first.math.geometry.Rotation2d;
import static edu.wpi.first.units.Units.MetersPerSecond;
import edu.wpi.first.units.measure.LinearVelocity;

public class AlgaeModuleState {
    // Please.. just look at CoralModuleState. I beg of thee
    public double speedMetersPerSecond;
    public Rotation2d angle;

    public AlgaeModuleState() {}
    public AlgaeModuleState(double speedMetersPerSecond, Rotation2d angle) {
        this.speedMetersPerSecond = speedMetersPerSecond;
        this.angle = angle;
    }
    public AlgaeModuleState(LinearVelocity speed, Rotation2d angle) {
        this(speed.in(MetersPerSecond), angle);
    }
}
