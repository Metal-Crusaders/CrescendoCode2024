package frc.robot.commands.teleop.shamper;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shamper;

public class ShootAmp extends Command {
    
    private Shamper shamper;
    Timer timer;

    public ShootAmp() {
        shamper = new Shamper(false);
        
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
