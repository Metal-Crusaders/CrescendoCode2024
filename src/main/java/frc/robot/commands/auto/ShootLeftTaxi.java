package frc.robot.commands.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.extras.Wait;
import frc.robot.commands.subroutines.AlignSpeaker;
import frc.robot.commands.subroutines.RestMode;
import frc.robot.commands.teleop.intake.OuttakeXSeconds;
import frc.robot.commands.teleop.shamper.ShootSpeaker;
import frc.robot.commands.teleop.swervedrive.RawDriveAuto;
import frc.robot.commands.teleop.swervedrive.RawTurnAuto;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vision;

public class ShootLeftTaxi extends SequentialCommandGroup {

    public ShootLeftTaxi(
        SwerveSubsystem swerve, 
        Intake intake, 
        Pivot pivot, 
        Shamper shamper,
        Vision vision
    ) {

        AlignSpeaker alignSpeaker = new AlignSpeaker(pivot, shamper, vision, swerve, intake);
        ShootSpeaker shootSpeaker = new ShootSpeaker(shamper, intake, () -> vision.getTargetSpeed());
        RestMode restMode = new RestMode(pivot, shamper);

        addRequirements(
            swerve,
            intake,
            pivot,
            shamper,
            vision
        );

        addCommands(
            alignSpeaker,
            shootSpeaker,
            restMode,
            new RawDriveAuto(swerve, -6, 1),
            new RawTurnAuto(swerve, 0, 0) // TODO figure this out
        );

    }
    
}
