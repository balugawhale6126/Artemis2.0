package frc.robot.generated;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstantsFactory;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.ClosedLoopOutputType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants.SteerFeedbackType;

import edu.wpi.first.math.util.Units;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class TunerConstants {
    // Both sets of gains need to be tuned to your individual robot.

    // The steer motor uses any SwerveModule.SteerRequestType control request with the
    // output type specified by SwerveModuleConstants.SteerMotorClosedLoopOutput
    private static final Slot0Configs steerGains = new Slot0Configs()
        .withKP(100).withKI(0).withKD(0.2)
        .withKS(0).withKV(1.5).withKA(0);
    // When using closed-loop control, the drive motor uses the control
    // output type specified by SwerveModuleConstants.DriveMotorClosedLoopOutput
    private static final Slot0Configs driveGains = new Slot0Configs()
        .withKP(3.1).withKI(0.01).withKD(0.005)
        .withKS(0).withKV(0).withKA(0);

    // The closed-loop output type to use for the steer motors;
    // This affects the PID/FF gains for the steer motors
    private static final ClosedLoopOutputType steerClosedLoopOutput = ClosedLoopOutputType.Voltage;
    // The closed-loop output type to use for the drive motors;
    // This affects the PID/FF gains for the drive motors
    private static final ClosedLoopOutputType driveClosedLoopOutput = ClosedLoopOutputType.Voltage;

    // The stator current at which the wheels start to slip;
    // This needs to be tuned to your individual robot
    private static final double kSlipCurrentA = 300.0;

    // Theoretical free speed (m/s) at 12v applied output;
    // This needs to be tuned to your individual robot
    public static final double kSpeedAt12VoltsMps = 4.73; // TODO: MotorRPS * Gearing * WheelCircMeters

    // Every 1 rotation of the azimuth results in kCoupleRatio drive motor turns;
    // This may need to be tuned to your individual robot
    private static final double kCoupleRatio = 3.5714285714285716;

    private static final double kDriveGearRatio = 6.746031746031747;
    private static final double kSteerGearRatio = 21.428571428571427;
    private static final double kWheelRadiusInches = 2;

    private static final boolean kSteerMotorReversed = true;
    private static final boolean kInvertLeftSide = false;
    private static final boolean kInvertRightSide = true;

    private static final String kCANbusName = "";
    private static final int kPigeonId = 2;


    // These are only used for simulation
    private static final double kSteerInertia = 0.00001;
    private static final double kDriveInertia = 0.001;
    // Simulated voltage necessary to overcome friction
    private static final double kSteerFrictionVoltage = 0.25;
    private static final double kDriveFrictionVoltage = 0.25;

    public static final SwerveDrivetrainConstants DrivetrainConstants = new SwerveDrivetrainConstants()
            .withPigeon2Id(kPigeonId)
            .withCANbusName(kCANbusName);

    private static final SwerveModuleConstantsFactory ConstantCreator = new SwerveModuleConstantsFactory()
            .withDriveMotorGearRatio(kDriveGearRatio)
            .withSteerMotorGearRatio(kSteerGearRatio)
            .withWheelRadius(kWheelRadiusInches)
            .withSlipCurrent(kSlipCurrentA)
            .withSteerMotorGains(steerGains)
            .withDriveMotorGains(driveGains)
            .withSteerMotorClosedLoopOutput(steerClosedLoopOutput)
            .withDriveMotorClosedLoopOutput(driveClosedLoopOutput)
            .withSpeedAt12VoltsMps(kSpeedAt12VoltsMps)
            .withSteerInertia(kSteerInertia)
            .withDriveInertia(kDriveInertia)
            .withSteerFrictionVoltage(kSteerFrictionVoltage)
            .withDriveFrictionVoltage(kDriveFrictionVoltage)
            .withFeedbackSource(SteerFeedbackType.FusedCANcoder)
            .withCouplingGearRatio(kCoupleRatio)
            .withSteerMotorInverted(kSteerMotorReversed);

        public static final double openLoopRamp = 0.25;
        public static final double closedLoopRamp = 0.0;
        
        
    // Front Left
    private static final int kFrontLeftDriveMotorId = 10;
    private static final int kFrontLeftSteerMotorId = 11;
    private static final int kFrontLeftEncoderId = 3;
    private static final double kFrontLeftEncoderOffset = -0.24658203125;


    private static final double kFrontLeftXPosInches = 8.625;
    private static final double kFrontLeftYPosInches = 10.375;

    // Front Right
    private static final int kFrontRightDriveMotorId = 12;
    private static final int kFrontRightSteerMotorId = 13;
    private static final int kFrontRightEncoderId = 4;
    private static final double kFrontRightEncoderOffset = 0.318359375;

    private static final double kFrontRightXPosInches = 8.625;
    private static final double kFrontRightYPosInches = -10.375;

    // Back Left
    private static final int kBackLeftDriveMotorId = 14;
    private static final int kBackLeftSteerMotorId = 15;
    private static final int kBackLeftEncoderId = 5;
    private static final double kBackLeftEncoderOffset = 0.439208984375;

    private static final double kBackLeftXPosInches = -8.625;
    private static final double kBackLeftYPosInches = 10.375;

    // Back Right
    private static final int kBackRightDriveMotorId = 16;
    private static final int kBackRightSteerMotorId = 17;
    private static final int kBackRightEncoderId = 6;
    private static final double kBackRightEncoderOffset = 0.216552734375;

    private static final double kBackRightXPosInches = -8.625;
    private static final double kBackRightYPosInches = -10.375;

//     // Front Left
//     private static final int kFrontLeftDriveMotorId = 51;
//     private static final int kFrontLeftSteerMotorId = 52;
//     private static final int kFrontLeftEncoderId = 9;
//     private static final double kFrontLeftEncoderOffset = -0.11669921875;

//     private static final double kFrontLeftXPosInches = 9.375;
//     private static final double kFrontLeftYPosInches = 9.375;

//     // Front Right
//     private static final int kFrontRightDriveMotorId = 53;
//     private static final int kFrontRightSteerMotorId = 54;
//     private static final int kFrontRightEncoderId = 12;
//     private static final double kFrontRightEncoderOffset = 0.287109375;

//     private static final double kFrontRightXPosInches = 9.375;
//     private static final double kFrontRightYPosInches = -9.375;

//     // Back Left
//     private static final int kBackLeftDriveMotorId = 55;
//     private static final int kBackLeftSteerMotorId = 56;
//     private static final int kBackLeftEncoderId = 10;
//     private static final double kBackLeftEncoderOffset = -0.002197265625;

//     private static final double kBackLeftXPosInches = -9.375;
//     private static final double kBackLeftYPosInches = 9.375;

//     // Back Right
//     private static final int kBackRightDriveMotorId = 57;
//     private static final int kBackRightSteerMotorId = 58;
//     private static final int kBackRightEncoderId = 11;
//     private static final double kBackRightEncoderOffset = -0.1474609375;

//     private static final double kBackRightXPosInches = -9.375;
//     private static final double kBackRightYPosInches = -9.375;


    public static final SwerveModuleConstants FrontLeft = ConstantCreator.createModuleConstants(
            kFrontLeftSteerMotorId, kFrontLeftDriveMotorId, kFrontLeftEncoderId, kFrontLeftEncoderOffset, Units.inchesToMeters(kFrontLeftXPosInches), Units.inchesToMeters(kFrontLeftYPosInches), kInvertLeftSide);
    public static final SwerveModuleConstants FrontRight = ConstantCreator.createModuleConstants(
            kFrontRightSteerMotorId, kFrontRightDriveMotorId, kFrontRightEncoderId, kFrontRightEncoderOffset, Units.inchesToMeters(kFrontRightXPosInches), Units.inchesToMeters(kFrontRightYPosInches), kInvertRightSide);
    public static final SwerveModuleConstants BackLeft = ConstantCreator.createModuleConstants(
            kBackLeftSteerMotorId, kBackLeftDriveMotorId, kBackLeftEncoderId, kBackLeftEncoderOffset, Units.inchesToMeters(kBackLeftXPosInches), Units.inchesToMeters(kBackLeftYPosInches), kInvertLeftSide);
    public static final SwerveModuleConstants BackRight = ConstantCreator.createModuleConstants(
            kBackRightSteerMotorId, kBackRightDriveMotorId, kBackRightEncoderId, kBackRightEncoderOffset, Units.inchesToMeters(kBackRightXPosInches), Units.inchesToMeters(kBackRightYPosInches), kInvertRightSide);

    public static final CommandSwerveDrivetrain DriveTrain = CommandSwerveDrivetrain.getInstance();
}
