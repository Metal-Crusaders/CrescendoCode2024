package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Vision extends SubsystemBase{

    double tv, tx, ty, ta;
    double selectedAngle = 0.06, targetTicks;
    SendableChooser<Double> angleChooser, speedChooser;

    // Working Velocity and Angle: 0.95 at 0.16 ticks

    public Vision() {

        this.angleChooser = new SendableChooser<>();
        for (double i = 0.06; i < 0.27; i += 0.01) {
            this.angleChooser.addOption(Double.toString((double)Math.round(i *  100) / 100), i);
        }

        this.angleChooser.setDefaultOption("0.06", 0.06);

        SmartDashboard.putData("Angle Selector", angleChooser);

        this.speedChooser = new SendableChooser<>();
        for (double i = 0.1; i < 1.1; i += 0.1) {
            this.speedChooser.addOption(Double.toString((double)Math.round(i *  100) / 100), i);
        }

        this.speedChooser.setDefaultOption("0.1", 0.06);

        SmartDashboard.putData("Speed Selector", speedChooser);
    }

    public double getAngularVelocity() {
        double velo = RobotMap.DrivebaseConstants.ANGLING_kP * tx * -1;
        // double velo = 0;
        SmartDashboard.putNumber("Angular Velocity", velo);
        return velo;
    }

    public double getTargetSpeed() {
        // if (tv == 0)
        //     return 0.3;
        return 0.8;
    }

    public double getTagDistance() {
        double targetOffsetAngle_Vertical = ty;

        // how many degrees back is your limelight rotated from perfectly vertical?
        double limelightMountAngleDegrees = 30.0; 

        // distance from the center of the Limelight lens to the floor
        double limelightLensHeightInches = 13.0;

        // distance from the target to the floor
        double goalHeightInches = 57.125; 

        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

        //calculate distance
        double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);
        return distanceFromLimelightToGoalInches;
    }

    
    public double getTargetEncoderTicks() {
        // return selectedAngle;
        return targetTicks;
    }

    // public double getSelectorEncoderTicks() {
    //     return selectedAngle;
    //     // return targetTicks;
    // }

    @Override
    public void periodic() {

        tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

        SmartDashboard.putNumber("Limelight Sees Target? ", tv);
        SmartDashboard.putNumber("Limelight Target Horizontal Offset", tx);
        SmartDashboard.putNumber("Limelight Target Vertical Offset", ty);
        SmartDashboard.putNumber("Limelight Target Area", ta);
        SmartDashboard.putNumber("Limelight Distance from Goal", this.getTagDistance());

        selectedAngle = angleChooser.getSelected();

        // targetTicks = selectedAngle; // TODO UNCOMMENT TH`IS FOR TUNING STUFF! ALSO, can set the swerve angular velocity tx to just be 0 for whatever we need

        if (tv == 0) {
            targetTicks = 0.19;
        } else {
            targetTicks = 0.22 + -0.00072254 * this.getTagDistance();
        }
        
            
    }
    
}
