package frc.robot.commands.teleop.intake;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeXSeconds extends Command {

    private final Timer timer;
    private final Intake intake;
    private double seconds;

    public IntakeXSeconds(Intake intake, double seconds) {

        this.intake = intake;
        this.timer = new Timer();

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setSpeed(0);
        timer.start();
    }

    @Override
    public void execute() {

        double intakeSpeed = Intake.INTAKE_DEF_SPEED;

        if (!intake.beamExists()) {
            intakeSpeed = 0;
        }

        intake.setSpeed(intakeSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        intake.setSpeed(0);
        timer.stop();
        timer.reset();
    }

    @Override
    public boolean isFinished() {
        return (timer.hasElapsed(seconds) || !intake.beamExists());
    }

}