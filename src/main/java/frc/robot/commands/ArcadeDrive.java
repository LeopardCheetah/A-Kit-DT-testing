// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ArcadeDriveConstants;
import frc.robot.Constants.JoystickConstants;
import frc.robot.subsystems.Drivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */


public class ArcadeDrive extends Command {
  /** Creates a new ArcadeDrive. */

  private Drivetrain m_drivetrain;
  private Joystick m_joystick;

  private double forward_speed;
  private double turn_speed;

  private double speed_constant = ArcadeDriveConstants.kSpeedConstant;
  private double turn_constant = ArcadeDriveConstants.kTurnConstant;

  public ArcadeDrive(Drivetrain drivetrain, Joystick joystick) {
    m_drivetrain = drivetrain;
    m_joystick = joystick;
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // we vibe
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    forward_speed = m_joystick.getRawAxis(JoystickConstants.kDriveAxis);
    turn_speed = m_joystick.getRawAxis(JoystickConstants.kTurnAxis);

    m_drivetrain.setLeftSpeed(forward_speed*speed_constant + turn_speed*turn_constant);
    m_drivetrain.setRightSpeed(forward_speed*speed_constant - turn_speed*speed_constant);

  }



  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.setSpeed(0); // stop drivetrain
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
