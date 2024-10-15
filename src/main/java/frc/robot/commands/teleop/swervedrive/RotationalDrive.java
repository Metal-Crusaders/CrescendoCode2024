package frc.robot.commands.teleop.swervedrive;

import java.util.function.DoubleSupplier;
import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveSubsystem;
import edu.wpi.first.wpilibj.DriverStation;

public class RotationalDrive extends Command {

    private double angle;
    private double headingX;
    private double headingY;

    private final double MAX_ANGULAR_VELO = 12.0;

    private boolean firstPress;

    private ChassisSpeeds desiredSpeeds;

    private final SwerveSubsystem swerve;

    private DoubleSupplier translationX, translationY, turningX;
    private BooleanSupplier faceBtn;
    
    public RotationalDrive(
        SwerveSubsystem swerve,
        DoubleSupplier translationX,
        DoubleSupplier translationY,
        DoubleSupplier turningX,
        BooleanSupplier faceBtn
    ) {

        this.swerve = swerve;

        this.translationX = translationX;
        this.translationY = translationY;
        this.turningX = turningX;
        this.faceBtn = faceBtn;

        addRequirements(this.swerve);
    }

    @Override
    public void initialize() {
        DriverStation.Alliance alliance = DriverStation.getAlliance().get();
        angle = (alliance == DriverStation.Alliance.Red) ? 120.0 : -120.0;
        headingX = Math.sin(angle / 180.0 * Math.PI);
        headingY = Math.cos(angle / 180.0 * Math.PI);
        this.swerve.drive(new Translation2d(0, 0), 0, true);
        firstPress = false;
    }

    @Override
    public void execute() {

        if (faceBtn.getAsBoolean()) {
            desiredSpeeds = this.swerve.getTargetSpeeds(
                Math.pow(translationX.getAsDouble(), 3), 
                Math.pow(translationY.getAsDouble(), 3),
                headingX, headingY);
            firstPress = false;
            this.swerve.driveFieldOriented(desiredSpeeds);
        } else {
            firstPress = true;
            this.swerve.drive(new Translation2d(Math.pow(translationX.getAsDouble(), 3) * this.swerve.getMaximumVelocity(),
                                Math.pow(translationY.getAsDouble(), 3) * this.swerve.getMaximumVelocity()),
                                Math.pow(turningX.getAsDouble(), 3) * this.swerve.getMaximumAngularVelocity(),
                                true);
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
