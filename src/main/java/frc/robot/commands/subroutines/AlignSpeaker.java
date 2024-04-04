package frc.robot.commands.subroutines;

import java.time.Instant;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.teleop.pivot.PivotTickPreset;
import frc.robot.commands.teleop.shamper.RevSpeaker;
import frc.robot.commands.teleop.shamper.ShootSpeaker;
import frc.robot.commands.teleop.swervedrive.AlignToTarget;
import frc.robot.commands.teleop.swervedrive.RawDriveAuto;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vision;

public class AlignSpeaker extends SequentialCommandGroup {

    public AlignSpeaker(SwerveSubsystem swerve, Pivot pivot, Shamper shamper, Vision vision, Intake intake) {

        addRequirements(swerve, pivot, shamper, vision, intake);

        addCommands(
            new RawDriveAuto(swerve, 0, 0.05),
            new InstantCommand(() -> shamper.setMode(true), shamper),
            new PivotTickPreset(pivot, () -> 0.14),
            new ParallelCommandGroup(
                new RevSpeaker(shamper, intake, () -> vision.getTargetSpeed()),
                new AlignToTarget(swerve, () -> vision.getAngularVelocity(), 0.5),
                // swerve.aimAtTarget(),
                new PivotTickPreset(pivot, () -> vision.getTargetEncoderTicks())
            ),
            new ShootSpeaker(shamper, intake, pivot, () -> vision.getTargetSpeed())
        );

    }
    
}
