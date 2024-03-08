package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

/*
 * Indexer is responsible for moving the note up
 * Shooter motor moves in the same direction
 * Amp motor moves in different directions (inverted / not inverted) depending on whether we wanna go to shooter or amp
 * 
 * Amp motor should match shooter motor in amplitude but oppose it in direction (or nah) depending on what the goal is
 * 
 * Velocity PID for the shooter motor (and by default, amp motor as well)
 */
public class Shamper extends SubsystemBase {

    public static double INDEX_SPEED = 0.5;

    CANSparkMax indexer, ampMotor, shooterMotor;

    private SparkPIDController shooterVelocityPID, ampVelocityPID;

    private boolean mode; // false for amp, true for speaker // TODO need to confirm this

    public Shamper(boolean defaultMode) {

        // init motors + sensors
        this.indexer = new CANSparkMax(RobotMap.ShamperConstants.INDEXER_CAN_ID, MotorType.kBrushless);
        this.ampMotor = new CANSparkMax(RobotMap.ShamperConstants.AMP_CAN_ID, MotorType.kBrushless);
        this.shooterMotor = new CANSparkMax(RobotMap.ShamperConstants.SHOOTER_CAN_ID, MotorType.kBrushless);

        // shooter vs. amp stuff
        this.shooterMotor.setInverted(RobotMap.ShamperConstants.SHOOTER_MOTOR_INVERTED);
        this.ampMotor.setInverted(RobotMap.ShamperConstants.AMP_MOTOR_INVERTED);
        this.setMode(defaultMode);

        // shooter velocity PID setup!
        // this.shooterVelocityPID = this.shooterMotor.getPIDController();
        // this.shooterVelocityPID.setP(RobotMap.ShamperConstants.kP);
        // this.shooterVelocityPID.setI(RobotMap.ShamperConstants.kI);
        // this.shooterVelocityPID.setD(RobotMap.ShamperConstants.kD);
        // this.shooterVelocityPID.setFF(RobotMap.ShamperConstants.kFF);
        // this.shooterVelocityPID.setOutputRange(0, 1);

        // velocity PID setup!
        // this.ampVelocityPID = this.ampMotor.getPIDController();
        // this.ampVelocityPID.setP(RobotMap.ShamperConstants.kP);
        // this.ampVelocityPID.setI(RobotMap.ShamperConstants.kI);
        // this.ampVelocityPID.setD(RobotMap.ShamperConstants.kD);
        // this.ampVelocityPID.setFF(RobotMap.ShamperConstants.kFF);
        // this.ampVelocityPID.setOutputRange(0, 1);
    }

    /*
     * true -> speaker
     * false -> amp
     */
    public void setMode(boolean mode) {
        this.mode = mode;
        this.ampMotor.setInverted(mode);
    }

    public boolean getMode() {
        return this.mode;
    }

    /*
     * true -> speaker
     * false -> amp
     */
    public String getModeString() {
        return ((this.mode) ? "shooter" : "amp");
    }

    public void setIndexSpeed(double speed) {
        this.indexer.set(speed);
    }

    public double getIndexSpeed() {
        return this.indexer.get();
    }

    public double getCurrentShooterMotorSpeed() {
        return this.shooterMotor.get();
    }
    
    public void setShooterMotorSpeed(double speed) {
        // this.shooterVelocityPID.setReference(speed, ControlType.kVelocity);
        this.shooterMotor.set(speed);
    }

    public double getCurrentAmpMotorSpeed() {
        return this.ampMotor.get();
    }
    
    public void setAmpMotorSpeed(double speed) {
        // this.ampVelocityPID.setReference(speed, ControlType.kVelocity);
        this.ampMotor.set(speed);
    }
    
}
