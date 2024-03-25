package frc.robot.commands.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subroutines.AlignSpeaker;
import frc.robot.commands.subroutines.RestMode;
import frc.robot.commands.teleop.intake.IntakeXSeconds;
import frc.robot.commands.teleop.intake.OuttakeXSeconds;
import frc.robot.commands.teleop.pivot.PivotTickPreset;
import frc.robot.commands.teleop.shamper.RevSpeaker;
import frc.robot.commands.teleop.shamper.ShootSpeaker;
import frc.robot.commands.teleop.swervedrive.RawDriveAuto;
import frc.robot.commands.teleop.swervedrive.SnapAngleAuto;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vision;

public class TwoAndTaxi extends SequentialCommandGroup {

    double PIVOT_TICKS_NOTE_2 = 0.15;

    public TwoAndTaxi(
        SwerveSubsystem swerve, 
        Intake intake, 
        Pivot pivot, 
        Shamper shamper,
        Vision vision
    ) {

        AlignSpeaker alignSpeaker = new AlignSpeaker(pivot, shamper, vision, intake);
        RestMode restMode = new RestMode(pivot, shamper);
        RestMode restMode2 = new RestMode(pivot, shamper);

        addRequirements(
            swerve,
            intake,
            pivot,
            shamper,
            vision
        );

        addCommands(
            alignSpeaker,
            restMode,
            new RawDriveAuto(swerve, -3, 0.5),
            new SnapAngleAuto(swerve, 180),
            new ParallelCommandGroup(
                new IntakeXSeconds(intake, 2),
                new RawDriveAuto(swerve, 9, 1)
            ),
            new SnapAngleAuto(swerve, 180),
            new RawDriveAuto(swerve, 0, 0.1),
            new ParallelCommandGroup(
                new PivotTickPreset(pivot, () -> PIVOT_TICKS_NOTE_2),
                new RevSpeaker(shamper, intake, () -> vision.getTargetSpeed())
            ),
            new ShootSpeaker(shamper, intake, () -> vision.getTargetSpeed()),
            restMode2
        );

    }

    
}
