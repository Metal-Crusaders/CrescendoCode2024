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
import frc.robot.commands.teleop.intake.OuttakeXSeconds;
import frc.robot.commands.teleop.shamper.ShootSpeaker;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vision;

public class OffsetNoteShot extends SequentialCommandGroup {

    public OffsetNoteShot(
        SwerveSubsystem swerve, 
        Intake intake, 
        Pivot pivot, 
        Shamper shamper,
        Vision vision,
        boolean redAlliance
    ) {

        RestMode restMode = new RestMode(pivot, shamper);
        AlignSpeaker alignSpeaker = new AlignSpeaker(pivot, shamper, vision, intake);
        RestMode restMode2 = new RestMode(pivot, shamper);

        addRequirements(
            swerve,
            intake,
            pivot,
            shamper,
            vision
        );

        addCommands(
            restMode,
            new SequentialCommandGroup(
                // backwards
                // angle to face note forwards (invert this based on boolean redAlliance)
                new ParallelCommandGroup(
                    new IntakeXSeconds(intake, 4)
                    // COMBINATION of x / y velocities to get note
                )
                // angle to directly face speaker
                // COMBINATION of x / y velocities to get in center
            ),
            alignSpeaker,
            restMode2 
        );

    }
    
    
}
