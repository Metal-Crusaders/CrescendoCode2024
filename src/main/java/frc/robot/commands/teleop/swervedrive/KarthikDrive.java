package frc.robot.commands.teleop.swervedrive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveSubsystem;

public class KarthikDrive extends Command {

    private final double MAX_ANGULAR_VELO = 12.0;

    private final SwerveSubsystem swerve;

    private DoubleSupplier translationX, translationY, headingX, headingY, leftTurn, rightTurn;
    
    public KarthikDrive(
        SwerveSubsystem swerve,
        DoubleSupplier translationX,
        DoubleSupplier translationY,
        DoubleSupplier headingX,
        DoubleSupplier headingY,
        DoubleSupplier leftTurn,
        DoubleSupplier rightTurn
    ) {

        this.swerve = swerve;

        this.translationX = translationX;
        this.translationY = translationY;
        this.headingX = headingX;
        this.headingY = headingY;
        this.leftTurn = leftTurn;
        this.rightTurn = rightTurn;

        addRequirements(this.swerve);
    }

    @Override
    public void initialize() {
        this.swerve.drive(new Translation2d(0, 0), 0, true);
    }

    @Override
    public void execute() {

        double angularVelo = (this.leftTurn.getAsDouble() - this.rightTurn.getAsDouble()) * MAX_ANGULAR_VELO;

        if (Math.abs(angularVelo) < 0.5) {
            ChassisSpeeds desiredSpeeds = this.swerve.getTargetSpeeds(
                translationX.getAsDouble(), 
                translationY.getAsDouble(), 
                headingX.getAsDouble(), 
                headingY.getAsDouble()
            );

            this.swerve.drive(desiredSpeeds);
        } else {
            this.swerve.drive(
                new Translation2d(translationX.getAsDouble(), translationY.getAsDouble()), 
                angularVelo, 
                true
            );
        }

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        this.swerve.drive(new Translation2d(0, 0), 0, true);
    }

}
