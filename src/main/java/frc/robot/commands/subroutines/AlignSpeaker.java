package frc.robot.commands.subroutines;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.teleop.pivot.PivotTickPreset;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vision;

public class AlignSpeaker extends ParallelCommandGroup {

    public AlignSpeaker(Pivot pivot, Shamper shamper, Vision vision, SwerveSubsystem swerve) {

        addRequirements(pivot, shamper, vision, swerve);

        addCommands(
            new PivotTickPreset(pivot, () -> vision.getTargetEncoderTicks()),
            new InstantCommand(() -> shamper.setMode(true), shamper),
            swerve.driveCommand(() -> 0, () -> 0, () -> vision.getAngularVelocity())
        );

    }
    
}
