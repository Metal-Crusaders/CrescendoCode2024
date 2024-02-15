package frc.robot.commands.teleop.pivot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Pivot;

public class RawPivot extends Command {

    public double deadband(double percent, double deadbandWidth) {
        if (-deadbandWidth <= percent && percent <= deadbandWidth) {
            return 0;
        }
        return percent;
    }
    
    private Pivot pivot;
    private DoubleSupplier pivotUpControl, pivotDownControl;

    public RawPivot(Pivot pivot, DoubleSupplier pivotUpControl, DoubleSupplier pivotDownControl) {
        this.pivot = pivot;
        this.pivotUpControl = pivotUpControl;
        this.pivotDownControl = pivotDownControl;

        addRequirements(this.pivot);
    }

    @Override
    public void initialize() {
        pivot.setPivotSpeed(0);
    }

    @Override
    public void execute() {
        
        double pivotSpeed = pivotUpControl.getAsDouble() - pivotDownControl.getAsDouble();

        pivot.setPivotSpeed(deadband(pivotSpeed, 0.05));

    }

    @Override
    public void end(boolean interrupted) {
        pivot.setPivotSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
