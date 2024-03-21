package frc.robot.subsystems;

import javax.management.timer.Timer;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lights extends SubsystemBase {
    private static Lights instance;
    private int selected = 0;
    private String binaryString;
    private final DigitalOutput pin0;
    private final DigitalOutput pin1;
    private final DigitalOutput pin2;
    private final DigitalOutput pin3;

    public static Lights getInstance() {
        if (instance == null) {
            instance = new Lights();
        }
        return instance;
    }

    public enum ledModes { //TODO decide what each value should be and sync with other side code
        OFF(0),
        RED(1),
        ShooterAtSpeed(2), // Shooter At Speed
        ShooterRamping(3), // Shooter Ramp
        IntakeSuccess(4), // Intake Success
        Intaking(5), // Intaking
        PURPLE(6),
        PINK(7),
        WHITE(8),
        PINKANT(9);
        private int modeNum;

        public int getModeNum() {
            return modeNum;
        }

        ledModes(int modeNum) {
            this.modeNum = modeNum;
        }
    }

    public Lights() {
        pin0 = new DigitalOutput(5);
        pin1 = new DigitalOutput(6);
        pin2 = new DigitalOutput(7);
        pin3 = new DigitalOutput(8);
    }

    public void setLEDs(ledModes mode) {
        this.selected = mode.getModeNum();

        System.out.println("Selected LED Mode: " + selected);

        // Convert to binary
        StringBuilder string = new StringBuilder(Integer.toBinaryString(selected));
        while (string.length() < 4) { // Make sure that the string is 4 bits
            string.insert(0, "0");
        }
        binaryString = string.toString();
        System.out.println(binaryString);
        setDigitalOutPins(binaryString);
    }

    public int getLEDs() {
        return selected;
    }

    private void setDigitalOutPins(String binStr) {
        pin0.set(Character.getNumericValue(binaryString.charAt(0)) > 0);
        pin1.set(Character.getNumericValue(binaryString.charAt(1)) > 0);
        pin2.set(Character.getNumericValue(binaryString.charAt(2)) > 0);
        pin3.set(Character.getNumericValue(binaryString.charAt(3)) > 0);

        // Debug
//        System.out.println(pin0.get());
//        System.out.println(pin1.get());
//        System.out.println(pin2.get());
//        System.out.println(pin3.get());
    }

    @Override
    public void periodic() {
    }

    @Override
    public void simulationPeriodic() {
    }
}