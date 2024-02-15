package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
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
public class Shamper extends SubsystemBase{

    CANSparkMax indexer, ampMotor, shooterMotor;
    RelativeEncoder shooterEncoder;

    private SparkPIDController velocityPID;

    private boolean mode; // false for amp, true for speaker // TODO need to confirm this
    private double targetSpeed;

    public Shamper(boolean defaultMode) {

        // init motors + sensors
        this.indexer = new CANSparkMax(RobotMap.ShamperConstants.INDEXER_CAN_ID, MotorType.kBrushless);
        this.ampMotor = new CANSparkMax(RobotMap.ShamperConstants.AMP_CAN_ID, MotorType.kBrushless);
        this.shooterMotor = new CANSparkMax(RobotMap.ShamperConstants.SHOOTER_CAN_ID, MotorType.kBrushless);

        this.shooterEncoder = shooterMotor.getEncoder();

        // shooter vs. amp stuff
        this.shooterMotor.setInverted(false); // TODO fix this if needed
        this.ampMotor.follow(this.shooterMotor);
        this.setMode(defaultMode);

        // velocity PID setup!
        this.velocityPID = this.shooterMotor.getPIDController();
        this.velocityPID.setP(RobotMap.ShamperConstants.kP);
        this.velocityPID.setI(RobotMap.ShamperConstants.kI);
        this.velocityPID.setD(RobotMap.ShamperConstants.kD);
        this.velocityPID.setFF(RobotMap.ShamperConstants.kFF);
        this.velocityPID.setOutputRange(-1, 1);
    }

    /*
     * true -> speaker
     * false -> amp
     */
    public void setMode(boolean mode) {
        this.mode = mode;
        this.ampMotor.setInverted(mode);
    }

    /*
     * true -> speaker
     * false -> amp
     */
    public String getMode() {
        return ((mode) ? "shooter" : "amp");
    }

    public void setIndexSpeed(double speed) {
        this.indexer.set(speed);
    }

    public double getIndexSpeed() {
        return this.indexer.get();
    }

    public double getTargetShampSpeed() {
        return this.targetSpeed;
    }

    public double getCurrentShampSpeed() {
        return this.shooterMotor.get();
    }
    
    /*
     * Sets the percentage speed of the shooter / amp
     */
    public void setShampSpeed(double speed) {
        this.velocityPID.setReference(speed, ControlType.kVelocity);
    }
    
}
