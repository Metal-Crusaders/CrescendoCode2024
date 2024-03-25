package frc.robot.commands.subroutines;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.extras.NoteIntoIndex;
import frc.robot.commands.extras.NoteIntoIntake;
import frc.robot.commands.teleop.intake.OuttakeXSeconds;
import frc.robot.commands.teleop.pivot.PivotTickPreset;
import frc.robot.commands.teleop.shamper.RevSpeaker;
import frc.robot.commands.teleop.shamper.ShootSpeaker;
import frc.robot.commands.teleop.swervedrive.AlignToTarget;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vision;

public class IntakeSource extends SequentialCommandGroup {

    public IntakeSource(Pivot pivot, Shamper shamper, Vision vision, Intake intake) {

        addRequirements(pivot, shamper, vision, intake);

        addCommands(
            new InstantCommand(() -> shamper.setMode(true), shamper),
            new ParallelCommandGroup(
                new PivotTickPreset(pivot, () -> 0.15), // TODO validate this angle
                new NoteIntoIndex(shamper)
            ),
            new RestMode(pivot, shamper)
        );

    }
    
}
