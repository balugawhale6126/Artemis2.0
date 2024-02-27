// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Indexer.IndexerMotors;
import frc.robot.subsystems.Indexer.IndexerStates;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Intake.IntakeStates;
import frc.robot.subsystems.Pivot.PivotState;
import frc.robot.subsystems.Indexer;
import frc.robot.commands.SetIndexer;
import frc.robot.commands.SetIntake;
import frc.robot.commands.Pivot.SetPivot;
import frc.robot.commands.Pivot.ZeroPivot;
import frc.robot.commands.Shooter.SetShooterVelocity;

public class RobotContainer {

    private final Vision s_Vision = Vision.getInstance();
    private final Shooter s_Shooter = Shooter.getInstance();
    private final Indexer s_Indexer = Indexer.getInstance();
    private final Intake s_Intake = Intake.getInstance();
    private final Pivot s_Pivot = Pivot.getInstance();
    private final Climb s_Climb = Climb.getInstance();

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final CommandXboxController driver = new CommandXboxController(0); // My joystick
    private final CommandSwerveDrivetrain drivetrain = CommandSwerveDrivetrain.getInstance(); // My drivetrain

    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(Constants.MaxSpeed * 0.2).withRotationalDeadband(Constants.MaxAngularRate * 0.2) // Add a 20% deadband, tune to driver preference
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
    // driving in open loop
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    private final Telemetry logger = new Telemetry(Constants.MaxSpeed);

    /* Driver Buttons */
    private final Trigger driverBack = driver.back();
    private final Trigger driverStart = driver.start();
    private final Trigger driverA = driver.a();
    private final Trigger driverB = driver.b();
    private final Trigger driverX = driver.x();
    private final Trigger driverY = driver.y();
    private final Trigger driverRightBumper = driver.rightBumper();
    private final Trigger driverLeftBumper = driver.rightBumper();
    private final Trigger driverLeftTrigger = driver.leftTrigger();
    private final Trigger driverRightTrigger = driver.rightTrigger();
    private final Trigger driverDpadUp = driver.povUp();
    private final Trigger driverDpadDown = driver.povDown();
    private final Trigger driverDpadLeft = driver.povLeft();
    private final Trigger driverDpadRight = driver.povRight();

    private double power;

    private void incPower() {
        power += 0.05;
    }
    private void decPower() {
        power -= 0.05;
    }

    private void configureBindings() {

        //nothing is binded to intake, indexer, or shooter yet
        driver.y().onTrue(onIntake());
        driver.a().onTrue(offIntake());
        driver.x().onTrue(onIndexer());
        driver.b().onTrue(offIndexer());

        driver.rightTrigger().whileTrue(new InstantCommand(() -> s_Indexer.setState(IndexerStates.REV)));

        driver.rightBumper().whileTrue(new InstantCommand(() -> s_Shooter.setVelocity(3000)));
        //driver.rightBumper().onTrue(new ParallelCommandGroup(new InstantCommand(() -> s_Shooter.setTopPercent(0.4)), new InstantCommand(() -> s_Shooter.setBotPercent(0.1))));
        // driver.rightBumper().whileTrue(new InstantCommand(() -> s_Shooter.setPercentOutput(0.5)));
        driver.leftBumper().onTrue(new InstantCommand(() -> Shooter.getInstance().setVoltage(0)));

        // driver.leftTrigger().onTrue(new InstantCommand(() ->decPower()));
        // driver.rightTrigger().onTrue(new InstantCommand(() -> incPower()));
        // driverDpadUp.onTrue(new InstantCommand(() -> s_Intake.incPower()));
        // driverDpadUp.onTrue(new InstantCommand(() -> s_Intake.decPower()));

        driver.rightTrigger().onTrue(new InstantCommand(() -> s_Climb.setClimbSpeed(0.05)));
        driver.leftTrigger().onTrue(new InstantCommand(() -> s_Climb.setClimbSpeed(0)));

        driverDpadDown.onTrue(new SetPivot(PivotState.GROUND));
        driverDpadUp.onTrue(new SetPivot(PivotState.FARWING));
        driverDpadLeft.onTrue(new SetPivot(PivotState.SUBWOOFER));
        driverDpadRight.onTrue(new ZeroPivot());

        drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
                drivetrain.applyRequest(() -> drive.withVelocityX(-driver.getLeftY() * Constants.MaxSpeed) // Drive forward with
                        // negative Y (forward)
                        .withVelocityY(-driver.getLeftX() * Constants.MaxSpeed) // Drive left with negative X (left)
                        .withRotationalRate(-driver.getRightX() * Constants.MaxAngularRate) // Drive counterclockwise with negative X (left)
                ));
        

        // driver.a().whileTrue(drivetrain.applyRequest(() -> brake));
        // driver.b().whileTrue(drivetrain
        //         .applyRequest(() -> point.withModuleDirection(new Rotation2d(-driver.getLeftY(), -driver.getLeftX()))));

        // reset the field-centric heading. AKA reset odometry
        driverBack.onTrue(new InstantCommand(() -> drivetrain.resetOdo(new Pose2d(0, 0, new Rotation2d()))));
        //driver.start().onTrue(new InstantCommand(() -> drivetrain.resetOdo())); //drivetrain.runOnce(() -> drivetrain.resetOdo());

        if (Utils.isSimulation()) {
            drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
        }
        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public RobotContainer() {
        power = 0.3;
        
        configureBindings();
    }

    public Command onIntake() {
        return new SetIntake(IntakeStates.ON);
    }

    public Command offIntake() {
        return new SetIntake(IntakeStates.OFF);
    }

    //shooter
    public Command onIndexer() {
        return new SetIndexer(IndexerStates.ON, IndexerMotors.BOTH);
    }
    
    public Command offIndexer() {
        return new SetIndexer(IndexerStates.OFF, IndexerMotors.BOTH);
    }


}