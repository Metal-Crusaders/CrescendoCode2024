package frc.robot.commands.teleop.shamper;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shamper;

public class RawShamp extends Command {

    public double deadband(double percent, double deadbandWidth) {
        if (-deadbandWidth <= percent && percent <= deadbandWidth) {
            return 0;
        }
        return percent;
    }
    
    private Shamper shamper;
    private DoubleSupplier indexControl;

    public RawShamp(Shamper shamper, DoubleSupplier indexControl) {
        this.shamper = shamper;
        this.indexControl = indexControl;

        addRequirements(this.shamper);
    }

    @Override
    public void initialize() {
        shamper.setIndexSpeed(0);
        shamper.setShooterMotorSpeed(0);
        shamper.setAmpMotorSpeed(0);
    }

    @Override
    public void execute() {

        double indexSpeed = indexControl.getAsDouble();

        shamper.setIndexSpeed(deadband(indexSpeed, 0.05));
        shamper.setShooterMotorSpeed(deadband(indexSpeed, 0.05));
        shamper.setAmpMotorSpeed(deadband(indexSpeed, 0.05));
        
    }

    @Override
    public void end(boolean interrupted) {
        shamper.setIndexSpeed(0);
        shamper.setShooterMotorSpeed(0);
        shamper.setAmpMotorSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
