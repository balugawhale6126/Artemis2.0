// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoAlignDrive;

import java.beans.Visibility;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Vision;

import org.opencv.core.Point;
import org.photonvision.targeting.PhotonTrackedTarget;


public class PIDAlign extends Command {

  private final CommandSwerveDrivetrain s_Swerve;
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric();
  PIDController alignPID = new PIDController(0.003, 0.07, 0); //TODO: tune this
  
  private double currentYaw;
  private double desiredYaw;

  private boolean isClockwise;
  Point desiredPoint;
  double offsetYaw;

  public PIDAlign(Point desiredPoint) {
    s_Swerve = CommandSwerveDrivetrain.getInstance();

    this.desiredPoint = desiredPoint;

    addRequirements(s_Swerve);
  }
  
  @Override
  public void initialize() {
    alignPID.reset();

    Pose2d pose = s_Swerve.getPose();
    Point currentLocation = new Point(pose.getTranslation().getX() , pose.getTranslation().getY());
    
    Point translatedPoint = new Point(desiredPoint.x - currentLocation.x , desiredPoint.y - currentLocation.y); // thanks Dave Yoon gloobert

    // desiredYaw = (Math.PI/2) - Math.atan2(translatedPoint.y,translatedPoint.x); // gets the non included angle in our current yaw 🦅🦅
    desiredYaw = Math.atan2(translatedPoint.y,translatedPoint.x);

    SmartDashboard.putNumber("Desired Yaw", desiredYaw);
  }
    

  @Override
  public void execute() {

    Pose2d pose = s_Swerve.getPose();

    try {
      // s_Swerve.updateOdometryByVision(); //since you're supposed to have vision target, reset odometry using kalman first
      currentYaw = pose.getRotation().getRadians() + Math.PI; //hopefully the poes is updated frequently since we should be facing an april tag
    } catch (Exception e) {}
// 

    SmartDashboard.putNumber("Current yaw", currentYaw);
    SmartDashboard.putNumber("Desired Yaw", desiredYaw);

    // if (Math.abs(desiredYaw - (Math.PI*2)) < desiredYaw) { desiredYaw = desiredYaw - (Math.PI*2);};
    desiredYaw = Math.min(desiredYaw, Math.PI*2 - desiredYaw);

    double rotationSpeed = alignPID.calculate(currentYaw, desiredYaw);
    // System.out.println("Current yaw: " + ro);

    s_Swerve.setControl(drive.withRotationalRate(rotationSpeed));
    SmartDashboard.putNumber("rotation speed", rotationSpeed);
  }

  // public double getDesiredRotation(){
    
  // }

  @Override // rah rah rahh
  public void end(boolean interrupted) {
    s_Swerve.setControl(drive.withRotationalRate(0));
  }

  @Override
  public boolean isFinished() {
    return Math.abs(desiredYaw - currentYaw) < (Math.PI/8); // error of three degrees for now, set later
  }

}

// robot's forward is posotive x, right is negetive y, circle is 180 to -180, 3, -3
// offset yaw, current yaw, desired yaw, rotation speed