package frc.robot.commands.auto.ppAutos;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subroutines.AlignSpeaker;
import frc.robot.commands.subroutines.RestMode;
import frc.robot.commands.teleop.pivot.PivotTickPreset;
import frc.robot.commands.teleop.shamper.ShootSpeaker;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vision;

public class TwoNoteFarSidePP extends SequentialCommandGroup {

    public TwoNoteFarSidePP(
        SwerveSubsystem swerve, 
        Intake intake, 
        Pivot pivot, 
        Shamper shamper,
        Vision vision
    ) {


        PivotTickPreset preset1 = new PivotTickPreset(pivot, () -> 0.19);
        ShootSpeaker shootSpeaker1 = new ShootSpeaker(shamper, intake, pivot, () -> vision.getTargetSpeed());
        PathPlannerAuto taxiAuto = new PathPlannerAuto("2NoteFarSideAuto");
        PivotTickPreset preset2 = new PivotTickPreset(pivot, () -> 0.19);
        ShootSpeaker shootSpeaker2 = new ShootSpeaker(shamper, intake, pivot, () -> vision.getTargetSpeed());
        RestMode restMode2 = new RestMode(pivot, shamper);

        addRequirements(
            swerve,
            intake,
            pivot,
            shamper,
            vision
        );

        addCommands(
            preset1,
            shootSpeaker1,
            taxiAuto,
            preset2,
            shootSpeaker2,
            restMode2
        );

    }

}