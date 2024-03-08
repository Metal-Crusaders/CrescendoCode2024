package frc.robot.commands.teleop.pivot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.Pivot;

public class PivotTickPreset extends Command {

    private final double THRESHOLD = 3;
    
    private Pivot pivot;
    private DoubleSupplier targetTickGetter;
    private double targetTicks;

    private double integralSum = 0, lastError = 0, currentTicks, error, derivative, output = 0;

    private Timer timer;

    public PivotTickPreset(Pivot pivot, DoubleSupplier targetTickGetter) {

        this.pivot = pivot;
        this.targetTickGetter = targetTickGetter;

        this.timer = new Timer();

        addRequirements(this.pivot);
    }

    @Override
    public void initialize() {
        this.targetTicks = targetTickGetter.getAsDouble();
        pivot.setPivotSpeed(0);
    }

    @Override
    public void execute() {
        
        currentTicks = pivot.getEncoderTicks();
        error = targetTicks - currentTicks;
        SmartDashboard.putNumber("error", error);
        SmartDashboard.putNumber("Pivot PID Output", output);

        // PID stuff
        derivative = (error - lastError) / (timer.get() + 0.0001);

        integralSum = integralSum + (error * (timer.get() + 0.0001));

        output = (RobotMap.PivotConstants.kP * error) + 
                 (RobotMap.PivotConstants.kI * integralSum) + 
                 (RobotMap.PivotConstants.kD * derivative);

        pivot.setPivotSpeed(output);

        lastError = error;
        timer.reset();

    }

    @Override
    public void end(boolean interrupted) {
        pivot.setPivotSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(error) < THRESHOLD;
    }
    
}
