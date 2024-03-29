package frc.robot.commands.auto.ppAutos;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subroutines.AlignSpeaker;
import frc.robot.commands.subroutines.RestMode;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vision;

public class ShootTaxiPP extends SequentialCommandGroup {


    public ShootTaxiPP(
        SwerveSubsystem swerve, 
        Intake intake, 
        Pivot pivot, 
        Shamper shamper,
        Vision vision
    ) {

        AlignSpeaker alignSpeaker = new AlignSpeaker(swerve, pivot, shamper, vision, intake);
        RestMode restMode = new RestMode(pivot, shamper);
        PathPlannerAuto taxiAuto = new PathPlannerAuto("PathPlannerTestAuto");

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
            taxiAuto
        );

    }

}