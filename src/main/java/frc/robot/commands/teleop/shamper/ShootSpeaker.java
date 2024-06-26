package frc.robot.commands.teleop.shamper;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;

public class ShootSpeaker extends Command {
    
    private Shamper shamper;
    private Intake intake;
    private Pivot pivot;

    private double shamperSpeed;
    private DoubleSupplier speedGetter;
    Timer indexTimer;

    private final double INDEX_SECONDS = 1;

    /*
     * Speed from 0 - 1
     */
    public ShootSpeaker(Shamper shamper, Intake intake, Pivot pivot, DoubleSupplier speedGetter) {
        this.shamper = shamper;
        this.intake = intake;
        this.pivot = pivot;
        this.speedGetter = speedGetter;

        this.indexTimer = new Timer();
        
        addRequirements(shamper);
        addRequirements(intake);
        addRequirements(pivot);
    }

    @Override
    public void initialize() {
        indexTimer.start();
        shamper.setMode(true);
        shamperSpeed = speedGetter.getAsDouble();
    }

    @Override
    public void execute() {
        shamper.setAmpMotorSpeed(shamperSpeed);
        shamper.setShooterMotorSpeed(shamperSpeed);

        pivot.setPivotSpeed(0);
        
        shamper.setIndexSpeed(Shamper.INDEX_SPEED);
        intake.setIntakeBoolean(true, false);
    }

    @Override
    public void end(boolean interrupted) {
        shamper.setAmpMotorSpeed(0);
        shamper.setShooterMotorSpeed(0);
        shamper.setIndexSpeed(0);

        intake.setIntakeBoolean(false, false);

        indexTimer.stop();
        indexTimer.reset();
    }

    @Override
    public boolean isFinished() {
        return (indexTimer.hasElapsed(INDEX_SECONDS));
    }

}
