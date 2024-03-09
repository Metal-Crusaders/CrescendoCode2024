package frc.robot.commands.subroutines;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.teleop.pivot.PivotTickPreset;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;

public class RestMode extends ParallelCommandGroup {

    public RestMode(Pivot pivot, Shamper shamper) {

        addRequirements(pivot, shamper);

        addCommands(
            new PivotTickPreset(pivot, () -> 0),
            new InstantCommand(() -> shamper.setMode(true), shamper)
        );

    }
    
}
