package frc.robot.commands.extras;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

public class BreakOut extends ParallelRaceGroup {

    public BreakOut(Command mainCmd, XboxController xbox) {

        addCommands(
            mainCmd,
            new WaitUntilB(xbox)
        );

    }
    
}
