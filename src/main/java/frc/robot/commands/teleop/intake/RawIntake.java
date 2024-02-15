package frc.robot.commands.teleop.intake;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class RawIntake extends Command {
    
    private Intake intake;
    private BooleanSupplier intakeControl, outtakeControl;

    public RawIntake(Intake intake, BooleanSupplier intakeControl, BooleanSupplier outtakeControl) {
        this.intake = intake;
        this.intakeControl = intakeControl;
        this.outtakeControl = outtakeControl;

        addRequirements(this.intake);
    }

    @Override
    public void initialize() {
        intake.setSpeed(0);
    }

    @Override
    public void execute() {

        intake.setIntake(intakeControl.getAsBoolean(), outtakeControl.getAsBoolean());
        
    }

    @Override
    public void end(boolean interrupted) {
        intake.setSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
