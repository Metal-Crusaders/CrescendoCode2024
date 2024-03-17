package frc.robot.commands.teleop.swervedrive;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.extras.Wait;
import frc.robot.subsystems.SwerveSubsystem;

public class RawDriveAuto extends SequentialCommandGroup {

    // positive speed = ACTUALLY FORWARDS
    public RawDriveAuto(SwerveSubsystem swerve, double speed, double seconds) {

        addRequirements(swerve);

        addCommands(
            new InstantCommand(() -> {
                swerve.drive(new Translation2d(speed, 0), 0, true);
            }),
            new Wait(swerve, seconds),
            new InstantCommand(() -> {
                swerve.drive(new Translation2d(0, 0), 0, true);
            })
        );

    }
    
}
