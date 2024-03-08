// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.RobotMap.DriverConstants;
import frc.robot.commands.teleop.intake.AlwaysOnIntake;
import frc.robot.commands.teleop.intake.RawIntake;
import frc.robot.commands.teleop.pivot.PivotTickPreset;
import frc.robot.commands.teleop.pivot.RawPivot;
import frc.robot.commands.teleop.shamper.RawShamp;
import frc.robot.commands.teleop.shamper.ShootAmp;
import frc.robot.commands.teleop.shamper.ShootSpeaker;
import frc.robot.commands.teleop.swervedrive.AbsoluteDriveAdv;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shamper;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vision;

import java.io.File;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{

  // The robot's subsystems and commands are defined here...
  final SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve"));
  // final Shamper shamper = new Shamper(true);
  final Pivot pivot = new Pivot();
  final Intake intake = new Intake();

  final Vision vision = new Vision();

  // Button-related commands
  Command shootSpeaker, shootAmp, shootCmd, ampAlignCmd, speakerAlignCmd, restCmd;

  // CommandJoystick driverController   = new CommandJoystick(3);//(OperatorConstants.DRIVER_CONTROLLER_PORT);
  XboxController driverXbox = new XboxController(RobotMap.DriverConstants.DRIVER_ID);
  XboxController operatorXbox = new XboxController(RobotMap.OperatorConstants.OPERATOR_ID);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {

    // NOTE: Below is the other way to use swerve drive (w XRC), but still untuned
    // AbsoluteDriveAdv closedAbsoluteDriveAdv = new AbsoluteDriveAdv(drivebase,
    //                                                                () -> MathUtil.applyDeadband(driverXbox.getLeftY(),
    //                                                                                             DriverConstants.LEFT_Y_DEADBAND),
    //                                                                () -> MathUtil.applyDeadband(driverXbox.getLeftX(),
    //                                                                                             DriverConstants.LEFT_X_DEADBAND),
    //                                                                () -> MathUtil.applyDeadband(driverXbox.getRightX(),
    //                                                                                             DriverConstants.RIGHT_X_DEADBAND),
    //                                                                driverXbox::getYButtonPressed,
    //                                                                driverXbox::getAButtonPressed,
    //                                                                driverXbox::getXButtonPressed,
    //                                                                driverXbox::getBButtonPressed);

    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the desired angle NOT angular rotation
    Command driveFieldOrientedDirectAngle = drivebase.driveCommand(
        () -> MathUtil.applyDeadband(driverXbox.getLeftY(), DriverConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(driverXbox.getLeftX(), DriverConstants.LEFT_X_DEADBAND),
        () -> driverXbox.getRightX(),
        () -> driverXbox.getRightY());

    Command alwaysOnIntake = new AlwaysOnIntake(
      intake, 
      () -> driverXbox.getLeftX(), 
      () -> driverXbox.getLeftY(), 
      () -> operatorXbox.getLeftBumperPressed()
    );

    RawPivot rawPivot = new RawPivot(pivot, () -> operatorXbox.getRightTriggerAxis(), () -> operatorXbox.getLeftTriggerAxis());

    // shootSpeaker = new ShootSpeaker(shamper, intake, () -> vision.getTargetSpeed());
    // shootAmp = new ShootAmp(shamper, intake);

    // Subroutine buttons!
    // shootCmd = new ConditionalCommand(shootSpeaker, shootAmp, () -> shamper.getMode());
    restCmd = new PivotTickPreset(pivot, () -> 0);
    ampAlignCmd = new PivotTickPreset(pivot, () -> Pivot.AMP_ENCODER_TICKS); // TODO add shamper stuff here through a parallel command group or smth?
    speakerAlignCmd = new PivotTickPreset(pivot, () -> vision.getTargetEncoderTicks());

    // Default Commands
    drivebase.setDefaultCommand(
        !RobotBase.isSimulation() ? driveFieldOrientedDirectAngle : driveFieldOrientedDirectAngle);

    // CommandScheduler.getInstance().setDefaultCommand(intake, alwaysOnIntake);
    CommandScheduler.getInstance().setDefaultCommand(pivot, rawPivot);
    
    configureBindings();

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    new JoystickButton(driverXbox, 1).onTrue((new InstantCommand(drivebase::zeroGyro)));
    // new JoystickButton(driverXbox, 3).onTrue(new InstantCommand(drivebase::addFakeVisionReading));
//    new JoystickButton(driverXbox, 3).whileTrue(new RepeatCommand(new InstantCommand(drivebase::lock, drivebase)));

    // JoystickButton shootBtn = new JoystickButton(operatorXbox, 1);
    // shootBtn.onTrue(shootCmd);
    JoystickButton restBtn = new JoystickButton(operatorXbox, 2);
    restBtn.onTrue(restCmd);
    JoystickButton speakerBtn = new JoystickButton(operatorXbox, 3);
    speakerBtn.onTrue(speakerAlignCmd);
    JoystickButton ampBtn = new JoystickButton(operatorXbox, 4);
    ampBtn.onTrue(ampAlignCmd);

    // JoystickButton shootSpeakerBtn = new JoystickButton(operatorXbox, 5);
    // shootSpeakerBtn.onTrue(shootSpeaker);
    // JoystickButton shootAmpBtn = new JoystickButton(operatorXbox, 6);
    // shootAmpBtn.onTrue(shootAmp);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // An example command will be run in autonomous
    return drivebase.getAutonomousCommand("New Path", true);
  }

  public void setDriveMode()
  {
    //drivebase.setDefaultCommand();
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
