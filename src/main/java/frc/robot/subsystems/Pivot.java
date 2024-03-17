package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Pivot extends SubsystemBase {
    
    public final static double MIN_ENCODER_TICKS = 0.06;
    public final static double AMP_ENCODER_TICKS = 0.25;

    private double TICKS_TO_DEGREES = 425.0;
    private double MAX_HOLD_PERCENT = 0.025;

    private final CANSparkMax leftPivotMotor, rightPivotMotor;
    private final DutyCycleEncoder encoder;

    public Pivot() {

        this.leftPivotMotor = new CANSparkMax(RobotMap.PivotConstants.LEFT_PIVOT_ID, MotorType.kBrushless);
        this.rightPivotMotor = new CANSparkMax(RobotMap.PivotConstants.RIGHT_PIVOT_ID, MotorType.kBrushless);

        this.leftPivotMotor.setIdleMode(IdleMode.kBrake);
        this.rightPivotMotor.setIdleMode(IdleMode.kBrake);

        this.leftPivotMotor.setInverted(RobotMap.PivotConstants.LEFT_PIVOT_INVERTED);
        this.rightPivotMotor.follow(leftPivotMotor, true);

        this.encoder = new DutyCycleEncoder(RobotMap.PivotConstants.DIO_PIVOT_ABS);
        this.resetEncoder();
    }

    public void setPivotSpeed(double speed) {
        if (this.getEncoderTicks() >= AMP_ENCODER_TICKS && speed > 0) {
            speed = 0;
        }
        if (this.getEncoderTicks() < MIN_ENCODER_TICKS && speed < 0) {
            speed = 0;
        }
        if (speed == 0) {
            speed = Math.cos(TICKS_TO_DEGREES * this.getEncoderTicks()) * MAX_HOLD_PERCENT;
        }
        leftPivotMotor.set(speed);
    }

    public double getPivotSpeed() {
        return this.leftPivotMotor.get();
    }

    public double getEncoderTicks() {
        return encoder.getAbsolutePosition();
    }

    public void resetEncoder() {
        encoder.reset();
    }

    @Override
    public void periodic() {
    }
    
}
