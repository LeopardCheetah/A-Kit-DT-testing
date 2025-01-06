// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

// welp i have no clue how to make a swerve rn so here's tank for ya

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */

  // neo 550 = brushless motor
  private SparkMax m_leftPrimaryMotor = new SparkMax(DrivetrainConstants.kLeftPrimaryMotorID, MotorType.kBrushless);
  private SparkMax m_leftSecondaryMotor = new SparkMax(DrivetrainConstants.kLeftSecondaryMotorID, MotorType.kBrushless); 

  private SparkMax m_rightPrimaryMotor = new SparkMax(DrivetrainConstants.kRightPrimaryMotorID, MotorType.kBrushless);
  private SparkMax m_rightSecondaryMotor = new SparkMax(DrivetrainConstants.kRightSecondaryMotorID, MotorType.kBrushless);

  private SparkMaxConfig m_rightSecondaryMotorConfig = new SparkMaxConfig();
  private SparkMaxConfig m_leftSecondaryMotorConfig = new SparkMaxConfig();
  private SparkMaxConfig m_rightPrimaryMotorConfig = new SparkMaxConfig();
  private SparkMaxConfig m_leftPrimaryMotorConfig = new SparkMaxConfig();

  // make motors follow each other
  public Drivetrain() {
    // make one motor follow another
    // welp seems like we need this new config thing

    // final thing with this config is that the "LEFT PRIMARY" motor dictates;
    // aka if the LeftPrimary has a positive speed, the robot should be moving "forward"

    // apply a current limit to be safe
    m_leftPrimaryMotorConfig
      .smartCurrentLimit(40); // still 40A limit

    m_leftSecondaryMotorConfig
      .follow(DrivetrainConstants.kLeftPrimaryMotorID)
      .smartCurrentLimit(40);
    
    m_rightPrimaryMotorConfig
      .inverted(true)
      .smartCurrentLimit(40);

    m_rightSecondaryMotorConfig
      .follow(DrivetrainConstants.kRightPrimaryMotorID)
      .smartCurrentLimit(40); // follow right primary, current limit 40A
    // im pretty sure i dont need an invert here as its already following an inverted motor
    
    m_leftPrimaryMotor.configure(m_leftPrimaryMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);  
    m_leftSecondaryMotor.configure(m_leftSecondaryMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_rightPrimaryMotor.configure(m_rightPrimaryMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_rightSecondaryMotor.configure(m_rightSecondaryMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);


  }


  public void setLeftSpeed(double speed){
    m_leftPrimaryMotor.set(speed);
  }

  public void setRightSpeed(double speed){
    m_rightPrimaryMotor.set(speed);
  }

  public void setSpeed(double speed){
    // set robot speed
    m_leftPrimaryMotor.set(speed);
    m_rightPrimaryMotor.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
