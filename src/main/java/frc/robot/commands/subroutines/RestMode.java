package frc.robot.commands.subroutines;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.teleop.pivot.PivotTickPreset;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;

public class RestMode extends SequentialCommandGroup {

    public RestMode(Pivot pivot, Shamper shamper) {

        addRequirements(pivot, shamper);

        addCommands(
            new InstantCommand(() -> shamper.setMode(true), shamper),
            new PivotTickPreset(pivot, () -> Pivot.MIN_ENCODER_TICKS)
            
        );

    }
    
}
