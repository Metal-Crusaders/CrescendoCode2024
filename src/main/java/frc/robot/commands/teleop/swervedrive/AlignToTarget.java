package frc.robot.commands.teleop.swervedrive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.SwerveSubsystem;

public class AlignToTarget extends Command {

    private final SwerveSubsystem swerve;
    private DoubleSupplier anglularVelocityGetter;
    private Translation2d dontTranslate;

    private double angularVelocity;

    private final double ERROR_THRESHOLD;

    public AlignToTarget(SwerveSubsystem swerve, DoubleSupplier anglularVelocityGetter, double errorThreshold) {

        this.swerve = swerve;
        this.anglularVelocityGetter = anglularVelocityGetter;

        this.ERROR_THRESHOLD = errorThreshold;

        this.dontTranslate = new Translation2d(0, 0);

        addRequirements(swerve);
    }

    @Override
    public void initialize() {
        swerve.drive(dontTranslate, 0, true);
    }

    @Override
    public void execute() {

        angularVelocity = anglularVelocityGetter.getAsDouble();

        swerve.drive(dontTranslate, angularVelocity, true);

    }

    @Override
    public void end(boolean interrupted) {

        swerve.drive(dontTranslate, 0, true);
        // CommandScheduler.getInstance().schedule(swerve.driveCommand(() -> 0, () -> 0, () -> swerve.getHeading().getSin(), () -> swerve.getHeading().getSin()));
        // TODO Validate: is the above necessary? ALSO VALIDATE SINE AND COSINE
    }

    @Override
    public boolean isFinished() {
        return Math.abs(angularVelocity) < ERROR_THRESHOLD;
    }
    
}
