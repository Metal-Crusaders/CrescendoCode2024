package frc.robot.commands.subroutines;

import java.time.Instant;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.teleop.pivot.PivotTickPreset;
import frc.robot.commands.teleop.shamper.RevSpeaker;
import frc.robot.commands.teleop.shamper.ShootSpeaker;
import frc.robot.commands.teleop.swervedrive.AlignToTarget;
import frc.robot.commands.teleop.swervedrive.RawDriveAuto;
// import frc.robot.commands.teleop.swervedrive.SnapPoseAuto;
import java.util.function.DoubleSupplier;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vision;
import java.lang.Math;

public class FeedNote extends SequentialCommandGroup {

    double angle, headingX, headingY;

    DoubleSupplier xPose, yPose;

    public FeedNote(SwerveSubsystem swerve, Pivot pivot, Shamper shamper, Vision vision, Intake intake, DoubleSupplier xPose, DoubleSupplier yPose) {

        addRequirements(swerve, pivot, shamper, vision, intake);

        this.xPose = xPose;
        this.yPose = yPose;

        // this.angle = -1 * Math.atan(this.yPose.getAsDouble() / this.xPose.getAsDouble());
        this.angle = 0.62;

        this.headingX = Math.sin(this.angle);
        this.headingY = Math.cos(this.angle);

        addCommands(
            new RawDriveAuto(swerve, 0, 0.05),
            new InstantCommand(() -> shamper.setMode(true), shamper),
            new ParallelCommandGroup(
                new PivotTickPreset(pivot, () -> 0.18),
                new RevSpeaker(shamper, intake, () -> 0.55),
                swerve.driveCommand(() -> 0, () -> 0, () -> headingX, () -> headingY).withTimeout(1.5)
            ),
            new ParallelCommandGroup(
                new ShootSpeaker(shamper, intake, pivot, () -> 0.55),
                swerve.driveCommand(() -> 0, () -> 0, () -> headingX, () -> headingY).withTimeout(1.5)
                // new SnapPoseAuto(swerve, () -> -7, () -> -3, false)
                // swerve.aimAtTarget(),
            )
        );

    }

    
}
