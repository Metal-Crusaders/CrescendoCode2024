package frc.robot.commands.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subroutines.AlignSpeaker;
import frc.robot.commands.subroutines.RestMode;
import frc.robot.commands.teleop.intake.IntakeXSeconds;
import frc.robot.commands.teleop.shamper.ShootSpeaker;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vision;

public class ShootAndTaxi extends SequentialCommandGroup {

    public ShootAndTaxi(
        SwerveSubsystem swerve, 
        Intake intake, 
        Pivot pivot, 
        Shamper shamper,
        Vision vision
    ) {

        AlignSpeaker alignSpeaker = new AlignSpeaker(pivot, shamper, vision, swerve, intake);
        ShootSpeaker shootSpeaker = new ShootSpeaker(shamper, intake, () -> vision.getTargetSpeed());
        RestMode restMode = new RestMode(pivot, shamper);
        IntakeXSeconds intake3Seconds = new IntakeXSeconds(intake, 3);
        Command autoPath = AutoBuilder.followPath(PathPlannerPath.fromPathFile("ShootAndTaxiPath"));
        AlignSpeaker alignSpeaker2 = new AlignSpeaker(pivot, shamper, vision, swerve, intake);
        ShootSpeaker shootSpeaker2 = new ShootSpeaker(shamper, intake, () -> vision.getTargetSpeed());
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
            shootSpeaker,
            restMode,
            new ParallelCommandGroup(
                intake3Seconds,
                autoPath
            ),
            alignSpeaker2,
            shootSpeaker2,
            restMode2
        );

    }
    
}
