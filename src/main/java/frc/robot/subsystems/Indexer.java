// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Indexer extends SubsystemBase {

    private static Indexer instance;

    public static Indexer getInstance() {
        if (instance == null) {
            instance = new Indexer();
        }
        return instance;
    }

    private String stateName; // to be used for periodic display - should be set everytime indexer is set
    // issue is that indexer can be any random number
    private double currentTopSpeed = 0;
    private double currentBottomSpeed = 0;
    private CANSparkMax indexerTopM;
    private CANSparkMax indexerBottomM;

    public Indexer() {
        indexerTopM = new CANSparkMax(Constants.HardwarePorts.indexerM, MotorType.kBrushless);
        indexerBottomM = new CANSparkMax(Constants.HardwarePorts.indexerM, MotorType.kBrushless);
        indexerBottomM.setInverted(true);
    }

    public enum IndexerStates {
        ON(1),
        OFF(0);
        private double speed;
        public double getValue() {
            return speed;
        } // how use

        IndexerStates(double    speed) {
            this.speed = speed;
        }
    }

    /**
     * @param MotorLocation
     * true = top motor
     * false = bottom motor
     * null = both
     */
    public void setSpeed(double speed, Boolean MotorLocation) {
        if(MotorLocation) {
            indexerTopM.set(speed);
            currentTopSpeed = speed;
        } else if (!MotorLocation) {
            indexerBottomM.set(speed);
            currentBottomSpeed = speed;
        } else if (MotorLocation == null) {
            indexerTopM.set(speed);
            currentTopSpeed = speed;
            indexerBottomM.set(speed);
            currentBottomSpeed = speed;
        }
//        this.stateName = state.name();

    }
    /**
     * @param MotorLocation
     * true = top motor
     * false = bottom motor
     */
    // you cant do null for this cause just get both them your self lazy lazy
    public double getSpeed(boolean MotorLocation) { //gets specific Speed (i hope)
        if(MotorLocation) {return currentTopSpeed;} else{ return currentBottomSpeed;}
    }

    @Override
    public void periodic() {
        SmartDashboard.putString("Top Speed", String.valueOf(currentTopSpeed));
        SmartDashboard.putString("Bottom Speed", String.valueOf(currentBottomSpeed));
    }

    @Override
    public void simulationPeriodic() {
    }
}
