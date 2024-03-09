package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Pivot extends SubsystemBase {
    
    public final static double AMP_ENCODER_TICKS = 450;

    private final CANSparkMax leftPivotMotor, rightPivotMotor;
    private final Encoder encoder;

    public Pivot() {

        this.leftPivotMotor = new CANSparkMax(RobotMap.PivotConstants.LEFT_PIVOT_ID, MotorType.kBrushless);
        this.rightPivotMotor = new CANSparkMax(RobotMap.PivotConstants.RIGHT_PIVOT_ID, MotorType.kBrushless);

        this.leftPivotMotor.setIdleMode(IdleMode.kBrake);
        this.rightPivotMotor.setIdleMode(IdleMode.kBrake);

        this.leftPivotMotor.setInverted(RobotMap.PivotConstants.LEFT_PIVOT_INVERTED);
        this.rightPivotMotor.follow(leftPivotMotor, true);

        this.encoder = new Encoder(RobotMap.PivotConstants.DIO_PIVOT_IN, RobotMap.PivotConstants.DIO_PIVOT_OUT, RobotMap.PivotConstants.DIO_PIVOT_ABS); // TODO test
        this.encoder.setReverseDirection(RobotMap.PivotConstants.ENCODER_INVERTED);
        this.resetEncoder();
    }

    public void setPivotSpeed(double speed) {
        if (this.getEncoderTicks() >= AMP_ENCODER_TICKS && speed > 0) {
            speed = 0;
        } // check for NAN here
        leftPivotMotor.set(speed);
    }

    public double getPivotSpeed() {
        return this.leftPivotMotor.get();
    }

    public double getEncoderTicks() {
        return encoder.get();
    }

    public double getAbsoluteEncoderTicks() {
        return encoder.getRaw();
    }

    public void resetEncoder() {
        encoder.reset();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Raw Encoder Ticks?", this.getAbsoluteEncoderTicks());
    }
    
}
