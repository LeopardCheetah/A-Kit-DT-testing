// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  // apparently I'm supposed to put all constants in here ok 

  public static class DrivetrainConstants {
    public static final int kLeftPrimaryMotorID = 0;
    public static final int kLeftSecondaryMotorID = 1;
    public static final int kRightPrimaryMotorID = 2;
    public static final int kRightSecondaryMotorID = 3;
  }

  public static class JoystickConstants {
    public static final int kDriveJoystickPort = 0;

    public static final int kDriveAxis = 1;
    public static final int kTurnAxis = 0;
  }

  public static class ArcadeDriveConstants {
    public static final double kSpeedConstant = 0.8;
    public static final double kTurnConstant = 0.8;
  }
}
