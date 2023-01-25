package frc.robot.util;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class JoystickModification {

    private static ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");

    private static NetworkTableEntry speedScaler = tab.add("Speed scaler", 1)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0.1, "max", 1, "block increment", 0.1))
    .getEntry();
  
    private static NetworkTableEntry leftXScaler = tab.add("X Translation Scaler", 1)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 1, "block increment", 0.1))
    .getEntry();
    
    private static NetworkTableEntry leftYScaler = tab.add("Y Translation Scaler", 1)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 1, "block increment", 0.1))
    .getEntry();
  
    private static NetworkTableEntry rightXScaler = tab.add("Rotation Scaler", 1)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 1, "block increment", 0.1))
    .getEntry();

    public static double getSpeedScaler(double defaultValue) {
        return speedScaler.getDouble(defaultValue);
    }

    public static double getRightXScaler(double defaultValue) {
        return rightXScaler.getDouble(defaultValue);
    }

    public static double getLeftYScaler(double defaultValue) {
        return leftYScaler.getDouble(defaultValue);
    }

    public static double getLeftXScaler(double defaultValue) {
        return leftXScaler.getDouble(defaultValue);
    }

    public static double deadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
          if (value > 0.0) {
            return (value - deadband) / (1.0 - deadband);
          } else {
            return (value + deadband) / (1.0 - deadband);
          }
        } else {
          return 0.0;
        }
    }
    
    public static double modifyAxis(double value) {
    value = deadband(value, 0.05);

    value = Math.copySign(value * value, value);

    double scaler = speedScaler.getDouble(1.0);

    value = value * scaler; //scale down value to slow robot

    return value;
    }
}
