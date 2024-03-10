package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Vision extends SubsystemBase{

    double tv, tx, ty, ta;
    double selectedSpeed = 0.75;
    SendableChooser<Double> speedChooser;

    public Vision() {

        this.speedChooser = new SendableChooser<>();
        for (int i = 6; i < 20; i++) {
            double value = Math.round(i * 0.05 * 100.0) / 100.0;
            this.speedChooser.addOption(Double.toString(value), value);
        }

        this.speedChooser.setDefaultOption("0.5", 0.5);

        SmartDashboard.putData("Speed Selector", speedChooser);

    }

    public double getAngularVelocity() {
        // double velo = RobotMap.DrivebaseConstants.ANGLING_kP * tx * -1;
        double velo = 0;
        SmartDashboard.putNumber("Angular Velocity", velo);
        return velo;
    }

    public double getTargetSpeed() {
        return selectedSpeed;
    }

    public double getTargetEncoderTicks() {
        return 0.10;
    }

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

        selectedSpeed = speedChooser.getSelected();
            
    }
    
}
