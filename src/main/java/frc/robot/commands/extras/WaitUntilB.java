package frc.robot.commands.extras;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

public class WaitUntilB extends Command {

    XboxController xbox;

    public WaitUntilB(XboxController xbox) {

        this.xbox = xbox;

    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return this.xbox.getBButton();
    }

    @Override
    public void end(boolean interrupted) {
    }
    
}
