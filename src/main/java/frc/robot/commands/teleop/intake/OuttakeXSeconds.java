package frc.robot.commands.teleop.intake;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class OuttakeXSeconds extends Command {

    private final Timer timer;
    private final Intake intake;
    private double seconds;

    public OuttakeXSeconds(Intake intake, double seconds) {

        this.intake = intake;
        this.seconds = seconds;
        this.timer = new Timer();

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setSpeed(-1 * Intake.INTAKE_DEF_SPEED);
        
        timer.start();
    }

    @Override
    public void execute() {

        double intakeSpeed = -1 * Intake.INTAKE_DEF_SPEED;

        // SmartDashboard.putNumber("Intake X Seconds Intake Speed", intake.getSpeed());
        // SmartDashboard.putBoolean("Intake X Seconds Intake Beam Exists", intake.beamExists());

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