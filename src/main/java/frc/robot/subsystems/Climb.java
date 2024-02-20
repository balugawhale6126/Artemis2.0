// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkBase.IdleMode;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  private static Climb instance;
  CANcoder encoder = new CANcoder(Constants.HardwarePorts.climbCANcoderID);
  public static Climb getInstance() {
    if(instance == null){
      instance = new Climb();
  }
    return instance;
  }
  public enum ClimbPositions {

  }
  private CANSparkFlex climbLeaderM;
  private CANSparkFlex climbFollowerM;

  public Climb() {
    climbLeaderM = new CANSparkFlex(Constants.HardwarePorts.climbLeaderMotor, MotorType.kBrushless);
    configLeaderMotor(climbLeaderM);

    climbFollowerM = new CANSparkFlex(Constants.HardwarePorts.climbFollowerMotor, MotorType.kBrushless);
    configFollowerMotor(climbFollowerM, climbLeaderM);


  }
  

  private void configLeaderMotor(CANSparkFlex motor) {
    motor.setSmartCurrentLimit(Constants.climbPeakCurrentLimit);
    motor.setIdleMode(IdleMode.kBrake);
    motor.enableVoltageCompensation(12);
    motor.getPIDController().setFF(0.00);
    motor.getPIDController().setP(0.00);
    motor.getPIDController().setI(0.0);
    motor.getPIDController().setD(0.00);
  }

  
  private void configFollowerMotor(CANSparkFlex followerMotor, CANSparkFlex leaderMotor) {
    followerMotor.setSmartCurrentLimit(Constants.climbPeakCurrentLimit);
    followerMotor.setIdleMode(IdleMode.kBrake);
    followerMotor.enableVoltageCompensation(12);

    followerMotor.follow(leaderMotor);
  }

  public void setClimbSpeed(double speed){
    climbLeaderM.set(speed); //speed should be -1.0 to 1.0
    climbleaderM.set
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
