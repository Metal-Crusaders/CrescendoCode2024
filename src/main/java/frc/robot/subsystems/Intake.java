package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {

    CANSparkMax intakeMotor;

    private final double INTAKE_MAX_SPEED = 0.5;

    public Intake() {
        this.intakeMotor = new CANSparkMax(RobotMap.IntakeConstants.INTAKE_CAN_ID, MotorType.kBrushless);
        this.intakeMotor.setInverted(RobotMap.IntakeConstants.INTAKE_REVERSED);
        this.intakeMotor.setIdleMode(IdleMode.kBrake);
    }

    public CANSparkMax getMotor() {
        return this.intakeMotor;
    }

    public void setSpeed(double speed) {
        this.intakeMotor.set(speed);
    }

    public double getSpeed() {
        return this.intakeMotor.get();
    }

    public void setIntake(boolean intake, boolean outtake) {
        if (intake && !outtake) {
            this.setSpeed(INTAKE_MAX_SPEED);
        } else if (!intake && outtake) {
            this.setSpeed(-INTAKE_MAX_SPEED);
        } else {
            this.setSpeed(0.0);
        }
    }
    
}
