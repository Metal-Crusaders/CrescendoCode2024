package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase{

    double tv, tx, ty, ta;

    public Vision() {
    }

    public double getHorizontalOffset() {
        return tx;
    }

    public double getTargetSpeed() {
        return 0.75;
    }

    public double getTargetEncoderTicks() {
        return 100;
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

    }
    
}
