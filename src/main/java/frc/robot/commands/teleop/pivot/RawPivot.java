package frc.robot.commands.teleop.pivot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Pivot;

public class RawPivot extends Command {
    
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
        
        double pivotSpeed = deadband(pivotUpControl.getAsDouble() - pivotDownControl.getAsDouble(), 0.05);
        SmartDashboard.putNumber("Pivot Speed", pivotSpeed);
        SmartDashboard.putNumber("Encoder Ticks", pivot.getEncoderTicks());
        pivot.setPivotSpeed(pivotSpeed);

    }

    @Override
    public void end(boolean interrupted) {
        pivot.setPivotSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public double deadband(double percent, double deadbandWidth) {
        if (-deadbandWidth <= percent && percent <= deadbandWidth) {
            return 0;
        }
        return percent;
    }
    
}
