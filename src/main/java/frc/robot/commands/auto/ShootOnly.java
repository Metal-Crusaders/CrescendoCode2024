package frc.robot.commands.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subroutines.AlignSpeaker;
import frc.robot.commands.subroutines.RestMode;
import frc.robot.commands.teleop.intake.OuttakeXSeconds;
import frc.robot.commands.teleop.pivot.PivotTickPreset;
import frc.robot.commands.teleop.shamper.RevSpeaker;
import frc.robot.commands.teleop.shamper.ShootSpeaker;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vision;

public class ShootOnly extends SequentialCommandGroup {

    public ShootOnly(
        SwerveSubsystem swerve, 
        Intake intake, 
        Pivot pivot, 
        Shamper shamper,
        Vision vision
    ) {

        PivotTickPreset preset1 = new PivotTickPreset(pivot, () -> 0.19);
        RevSpeaker revSpeaker1 = new RevSpeaker(shamper, intake, () -> vision.getTargetSpeed());
        ShootSpeaker shootSpeaker1 = new ShootSpeaker(shamper, intake, pivot, () -> vision.getTargetSpeed());
        RestMode restMode = new RestMode(pivot, shamper);

        addRequirements(
            swerve,
            intake,
            pivot,
            shamper,
            vision
        );

        addCommands(
            new ParallelCommandGroup(
                preset1,
                revSpeaker1
            ),
            shootSpeaker1,
            restMode
        );

    }
    
}
