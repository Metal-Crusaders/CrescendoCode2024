// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.extras;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Wait extends Command {

  // Instantiates required subsystem and also timer variables that keep track of how long the subsystem has waited
  SubsystemBase m_subsystem;
   
  double m_waitTime, currentTime;
;
  boolean isFinished = false;

  /**
   * Creates a new WaitCommand.
   */
  public 
  Wait(SubsystemBase subsystem, double seconds) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_waitTime = seconds;
    m_subsystem = subsystem;
    addRequirements(m_subsystem);
  }

  double startTime;
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Sets the start time to the system time at the time the command was scheduled
    // m_currentTime = System.currentTimeMillis();
    startTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Updates current time every execution of the command
    /* 
    m_currentTime = System.currentTimeMillis();
    long m_differential = m_currentTime - m_startTime;
    SmartDashboard.putNumber("Time Differential (Subsystem = " + m_subsystem.getClass().getName() + "): ", (double) m_differential);
    isFinished = m_differential >= m_waitTime;
    */

  
    currentTime = Timer.getFPGATimestamp();

    isFinished =( currentTime - startTime )>= m_waitTime;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Command only finishes execution when the change in system time (aka time waited) exceeds the required wait time
    return isFinished;
  }
}