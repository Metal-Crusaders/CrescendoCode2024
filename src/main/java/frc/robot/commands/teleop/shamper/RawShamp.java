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
    private DoubleSupplier shooterControl, indexControl;
    private BooleanSupplier modeSelector;

    private boolean modeToggle;

    public RawShamp(Shamper shamper, DoubleSupplier shooterControl, DoubleSupplier indexControl, BooleanSupplier modeSelector) {
        this.shamper = shamper;
        this.shooterControl = shooterControl;
        this.indexControl = indexControl;
        this.modeSelector = modeSelector;

        modeToggle = true;

        addRequirements(this.shamper);
    }

    @Override
    public void initialize() {
        shamper.setIndexSpeed(0);
        shamper.setShooterMotorSpeed(0);
        shamper.setMode(modeToggle);
    }

    @Override
    public void execute() {

        double shooterSpeed = shooterControl.getAsDouble();
        double indexSpeed = indexControl.getAsDouble();
        
        if (modeSelector.getAsBoolean()) {
            modeToggle = !modeToggle;
        }

        shamper.setIndexSpeed(deadband(indexSpeed, 0.05));
        shamper.setShooterMotorSpeed(deadband(shooterSpeed, 0.05));
        shamper.setMode(modeToggle);
    }

    @Override
    public void end(boolean interrupted) {
        shamper.setIndexSpeed(0);
        shamper.setShooterMotorSpeed(0);
        shamper.setMode(true);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
