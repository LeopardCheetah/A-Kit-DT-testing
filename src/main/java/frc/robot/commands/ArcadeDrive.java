// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants.ArcadeDriveConstants;
import frc.robot.Constants.JoystickConstants;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.PubSubOption;



/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */


public class ArcadeDrive extends Command {
  /** Creates a new ArcadeDrive. */

  private Drivetrain m_drivetrain;
  private Joystick m_joystick;

  private double forwardSpeed;
  private double turnSpeed;

  private double speedConstant = ArcadeDriveConstants.kSpeedConstant;
  private double turnConstant = ArcadeDriveConstants.kTurnConstant;

  // API - https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/networktables/DoublePublisher.html
  private final DoublePublisher driveSpeedPublisher;
  // private final NTSendable drivetrainSendable;


  private final Field2d m_field = new Field2d();


  

  public ArcadeDrive(Drivetrain drivetrain, Joystick joystick, DoubleTopic driveSpeed) {
    m_drivetrain = drivetrain;
    m_joystick = joystick;
    addRequirements(m_drivetrain);

    driveSpeedPublisher = driveSpeed.publish(PubSubOption.keepDuplicates(true));
    // API - https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/networktables/PubSubOption.html

    // this.drivetrainSendable = drivetrainSendable;
    // see https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/networktables/NTSendable.html
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // we vibe
    SmartDashboard.putData("Differential Drive", 
      m_drivetrain.getDrivetrain()
    );

    SmartDashboard.putData("Field", m_field);

    // drivetrainSendable.initSendable(m_drivetrain); -- this doesn't work :(
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    forwardSpeed = m_joystick.getRawAxis(JoystickConstants.kDriveAxis)*speedConstant;
    turnSpeed = m_joystick.getRawAxis(JoystickConstants.kTurnAxis)*turnConstant;

    m_drivetrain.setSpeed(forwardSpeed, turnSpeed);

    // since smartDashboard runs NT under the hood we can technically use this to get data to NT then to Elastic
    // but lets try directly going to Network Tables instead!
    // SmartDashboard.putNumber("Drive Speed", forward_speed);
    // SmartDashboard.putNumber("Turn Speed", turn_speed);
    // SmartDashboard.putNumber("Left Side speed", forward_speed*speed_constant + turn_speed*turn_constant);

    driveSpeedPublisher.set(forwardSpeed);

    SmartDashboard.putNumber("Pose X", m_drivetrain.getLeftMotorDistance());
    SmartDashboard.putNumber("Pose Y", m_drivetrain.getRightMotorDistance());
    m_field.setRobotPose(m_drivetrain.getRobotPose());
    
  }



  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.setSpeed(0, 0); // stop drivetrain
    driveSpeedPublisher.close();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
