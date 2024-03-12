package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Vision extends SubsystemBase{

    double tv, tx, ty, ta;
    double selectedAngle = 0.06;
    SendableChooser<Double> angleChooser;

    public Vision() {

        this.angleChooser = new SendableChooser<>();
        for (double i = 0.06; i < 0.16; i += 0.01) {
            this.angleChooser.addOption(Double.toString(i), i);
        }

        this.angleChooser.setDefaultOption("0.06", 0.06);

        SmartDashboard.putData("Speed Selector", angleChooser);

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
        return 1;
    }

    public double getTargetEncoderTicks() {
        // if (tv == 0)
        //     return 0.0;
        return selectedAngle;
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

        selectedAngle = angleChooser.getSelected();
            
    }
    
}
