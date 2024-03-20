package frc.robot.commands.teleop.swervedrive;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.extras.Wait;
import frc.robot.subsystems.SwerveSubsystem;

public class SnapAngleAuto extends Command {

    double radAngle, xHeading, yHeading;

    SwerveSubsystem swerve;

    Timer timer;

    Command driveCmd;

    public SnapAngleAuto(SwerveSubsystem swerve, double angle) {

        this.swerve = swerve;

        addRequirements(this.swerve);

        radAngle = angle / 180.0 * Math.PI;

        xHeading = Math.sin(radAngle);
        yHeading = Math.cos(radAngle);

        timer = new Timer();

    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
        ChassisSpeeds desiredSpeeds = swerve.getTargetSpeeds(0, 0, xHeading, yHeading);
        swerve.drive(desiredSpeeds);
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(2);
    }

    @Override
    public void end(boolean interrupted) {
        swerve.zeroGyro();
        swerve.drive(swerve.getTargetSpeeds(0, 0, 0, 0));
        timer.stop();
        timer.reset();
    }
    
}
