package frc.robot.commands.subroutines;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.teleop.pivot.PivotTickPreset;
import frc.robot.commands.teleop.shamper.RevSpeaker;
import frc.robot.commands.teleop.shamper.ShootSpeaker;
import frc.robot.commands.teleop.swervedrive.AlignToTarget;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vision;

public class AlignSpeaker extends SequentialCommandGroup {

    public AlignSpeaker(Pivot pivot, Shamper shamper, Vision vision, Intake intake) {

        addRequirements(pivot, shamper, vision, intake);

        addCommands(
            new RevSpeaker(shamper, intake, () -> vision.getTargetSpeed()),
            new InstantCommand(() -> shamper.setMode(true), shamper),
            // new AlignToTarget(swerve, () -> vision.getAngularVelocity(), 1), // TODO validate Error Threshold
            // swerve.aimAtTarget(),
            new PivotTickPreset(pivot, () -> vision.getTargetEncoderTicks()),
            new ShootSpeaker(shamper, intake, () -> vision.getTargetSpeed())
        );

    }
    
}
