package frc.robot.commands.teleop.swervedrive;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.extras.Wait;
import frc.robot.subsystems.SwerveSubsystem;

public class RawTurnAuto extends SequentialCommandGroup {

    // positive speed = TODO confirm clockwise / counterclockwise
    public RawTurnAuto(SwerveSubsystem swerve, double speed, double seconds) {

        addRequirements(swerve);

        addCommands(
            new InstantCommand(() -> {
                swerve.drive(new Translation2d(0, 0), speed, true);
            }),
            new Wait(swerve, seconds),
            new InstantCommand(() -> {
                swerve.drive(new Translation2d(0, 0), 0, true);
            })
        );

    }
    
}
