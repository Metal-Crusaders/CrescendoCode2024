package frc.robot.commands.teleop.shamper;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shamper;

public class ShamperShoot extends Command {
    
    private Shamper shamper;
    private double speed;
    Timer revTimer, indexTimer;

    private final double REV_SECONDS = 0.5;
    private final double INDEX_SECONDS = 0.5;

    /*
     * Speed from 0 - 1
     */
    public ShamperShoot(Shamper shamper, double speed) {
        this.shamper = shamper;
        this.speed = speed;

        this.revTimer = new Timer();
        this.indexTimer = new Timer();
        
        addRequirements(shamper);
    }

    @Override
    public void initialize() {
        revTimer.start();
        shamper.setAmpMotorSpeed(speed);
        shamper.setShooterMotorSpeed(speed);
    }

    @Override
    public void execute() {

        if (revTimer.hasElapsed(REV_SECONDS)) {
            indexTimer.start();
            shamper.setIndexSpeed(Shamper.INDEX_SPEED);
        }

    }

    @Override
    public void end(boolean interrupted) {
        shamper.setAmpMotorSpeed(0);
        shamper.setShooterMotorSpeed(0);
        shamper.setIndexSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return (indexTimer.hasElapsed(INDEX_SECONDS));
    }

}
