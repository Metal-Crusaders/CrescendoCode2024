package frc.robot.commands.extras;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shamper;

// move all things in Shamper and Intake in opposite direction until the robot gets to the beam break
public class OtherWay extends Command {

    private Shamper shamper;
    private Intake intake;

    private final double SHOOTER_SPEED = -0.5;
    private final double INDEX_SPEED = -0.5;

    public OtherWay(Shamper shamper, Intake intake) {
        this.shamper = shamper;
        this.intake = intake;
        
        addRequirements(shamper);
        addRequirements(intake);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        shamper.setAmpMotorSpeed(SHOOTER_SPEED);
        shamper.setShooterMotorSpeed(SHOOTER_SPEED);
        
        shamper.setIndexSpeed(INDEX_SPEED);
        intake.setIntakeBoolean(false, true);
    }

    @Override
    public void end(boolean interrupted) {
        shamper.setAmpMotorSpeed(0);
        shamper.setShooterMotorSpeed(0);
        shamper.setIndexSpeed(0);

        intake.setIntakeBoolean(false, false);
    }

    @Override
    public boolean isFinished() {
        return (!intake.beamExists());
    }
    
}
