package frc.robot.commands.teleop.intake;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class AlwaysOnIntake extends Command {

    private final Intake intake;
    private final DoubleSupplier leftX;
    private final DoubleSupplier leftY;
    private final BooleanSupplier toggleBtn;

    private boolean intakeOn;

    private final double FLOOR_INTAKE_SPEED = 0.25;

    public AlwaysOnIntake(Intake intake, DoubleSupplier leftX, DoubleSupplier leftY, BooleanSupplier toggleBtn) {

        this.intake = intake;
        this.leftX = leftX;
        this.leftY = leftY;
        
        this.toggleBtn = toggleBtn;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setSpeed(0);
    }

    @Override
    public void execute() {

        double intakeSpeed = Math.sqrt(Math.pow(leftX.getAsDouble(), 2) + Math.pow(leftY.getAsDouble(), 2)) * Intake.INTAKE_DEF_SPEED;

        if (intakeSpeed < FLOOR_INTAKE_SPEED) {
            intakeSpeed = FLOOR_INTAKE_SPEED;
        }

        if (!intake.beamExists()) {
            intakeSpeed = 0;
        }

        // if (toggleBtn.getAsBoolean()) {
        //     intakeOn = !intakeOn;
        // }

        // if (!intakeOn) {
        //     intakeSpeed = 0;
        // }
        
        intake.setSpeed(intakeSpeed);

        SmartDashboard.putNumber("Always On Intake Speed", intakeSpeed);
        SmartDashboard.putBoolean("Beam Exists", intake.beamExists());
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