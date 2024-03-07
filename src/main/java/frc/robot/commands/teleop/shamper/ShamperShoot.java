package frc.robot.commands.teleop.shootPresets;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shamper;

public class ShamperShoot extends Command {
    
    private Shamper shamper;
    private Intake intake;

    private double shamperSpeed;
    Timer revTimer, indexTimer;

    private final double REV_SECONDS = 0.5;
    private final double INDEX_SECONDS = 0.5;

    /*
     * Speed from 0 - 1
     */
    public ShamperShoot(Shamper shamper, Intake intake, double speed) {
        this.shamper = shamper;
        this.intake = intake;
        this.shamperSpeed = speed;

        this.revTimer = new Timer();
        this.indexTimer = new Timer();
        
        addRequirements(shamper);
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        revTimer.start();

        shamper.setAmpMotorSpeed(shamperSpeed);
        shamper.setShooterMotorSpeed(shamperSpeed);
        shamper.setIndexSpeed(0);

        intake.setIntakeBoolean(false, false);
    }

    @Override
    public void execute() {

        if (revTimer.hasElapsed(REV_SECONDS)) {
            indexTimer.start();
            shamper.setIndexSpeed(Shamper.INDEX_SPEED);
            intake.setIntakeBoolean(true, false);
        }

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
        return (indexTimer.hasElapsed(INDEX_SECONDS));
    }

}
