package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {

    // CANSparkMax intakeMotor;

    // VictorSP frontRoller;

    // DigitalInput beamSensor;
    // DigitalOutput beam;

    public static double INTAKE_DEF_SPEED = 0.5;

    public Intake() {
        // this.intakeMotor = new CANSparkMax(RobotMap.IntakeConstants.INTAKE_CAN_ID, MotorType.kBrushless);
        // this.intakeMotor.setInverted(RobotMap.IntakeConstants.INTAKE_REVERSED);
        // this.intakeMotor.setIdleMode(IdleMode.kBrake);

        // this.frontRoller = new VictorSP(RobotMap.IntakeConstants.FRONT_ROLLER_PWM);
        // this.frontRoller.setInverted(RobotMap.IntakeConstants.FRONT_ROLLER_REVERSED);

        // this.beamSensor = new DigitalInput(RobotMap.IntakeConstants.BEAM_BREAK_SENSOR_DIO);
        // this.beam = new DigitalOutput(RobotMap.IntakeConstants.BEAM_BREAK_LED_DIO);
        // this.beam.set(true);
    }

    public CANSparkMax getIntakeMotor() {
        // return this.intakeMotor;
        return null;
    }

    public VictorSP getFrontRoller() {
        // return this.frontRoller;
        return null;
    }

    public void setSpeed(double speed) {
        // this.intakeMotor.set(speed);
        // this.frontRoller.set(speed);
    }

    public double getSpeed() {
        // return this.intakeMotor.get();
        return 0.0;
    }

    public double getFrontRollerSpeed() {
        // return this.frontRoller.get();
        return 0.0;
    }

    public void setIntakeBoolean(boolean intake, boolean outtake) {
        // if (intake && !outtake) {
        //     this.setSpeed(Intake.INTAKE_DEF_SPEED);
        // } else if (!intake && outtake) {
        //     this.setSpeed(-Intake.INTAKE_DEF_SPEED);
        // } else {
        //     this.setSpeed(0.0);
        // }
    }

    public boolean beamExists() {
        // return beamSensor.get();
        return true;
    }
    
}
