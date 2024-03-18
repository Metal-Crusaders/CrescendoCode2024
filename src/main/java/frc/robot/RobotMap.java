  // Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean constants. This
 * class should not be used for any other purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class RobotMap
{

  public static final double ROBOT_MASS = (148 - 20.3) * 0.453592; // 32lbs * kg per pound
  public static final Matter CHASSIS    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
  public static final double LOOP_TIME  = 0.13; //s, 20ms + 110ms sprk max velocity lag

  public static final class DrivebaseConstants {

    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds

    public static final double ANGLING_kP = 8.0 / 25.0; // TODO figure this out, should be starting kP should be maxAngularVelocity (try 3.0) / maxOffset
  
    public static final double PATHPLANNER_kP = 10.0;
    public static final double PATHPLANNER_kI = 0.0;
    public static final double PATHPLANNER_kD = 0.0;
    

  }

  public static final class ShamperConstants {

    public static final int INDEXER_CAN_ID = 14;
    public static final int AMP_CAN_ID = 13;
    public static final int SHOOTER_CAN_ID = 15;

    public static final boolean SHOOTER_MOTOR_INVERTED = false;
    public static final boolean AMP_MOTOR_INVERTED = true;

    public static final double kP = 1;
    public static final double kI = 0.0;
    public static final double kD = 0.0;
    public static final double kFF = 0.0;

    public static final int BEAM_BREAK_LED_DIO = 7;
    public static final int BEAM_BREAK_SENSOR_DIO = 8;
  }

  public static final class IntakeConstants {

    public static final int INTAKE_CAN_ID = 16;
    public static final boolean INTAKE_REVERSED = true;

    public static final int BEAM_BREAK_LED_DIO = 5;
    public static final int BEAM_BREAK_SENSOR_DIO = 6;

  }

  public static final class PivotConstants {

    public static final int LEFT_PIVOT_ID = 17;
    public static final int RIGHT_PIVOT_ID = 18;

    public static final boolean LEFT_PIVOT_INVERTED = true;
    public static final boolean ENCODER_INVERTED = true;

    public static final int DIO_PIVOT_ABS = 4;

    public static final double kP = 1.0 / 0.22;
    public static final double kI = 0.0;
    public static final double kD = 0.001;
  }

  public static class DriverConstants {

    public static final int DRIVER_ID = 0;

    // Joystick Deadband
    public static final double LEFT_X_DEADBAND  = 0.1;
    public static final double LEFT_Y_DEADBAND  = 0.1;
    public static final double RIGHT_X_DEADBAND = 0.1;
    public static final double TURN_CONSTANT    = 6;
  }

  public static class OperatorConstants {

    public static final int OPERATOR_ID = 1;
  }
}
