package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

public class RobotContainer {

  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();

  private final XboxController m_controller = new XboxController(Constants.DRIVER_CONTROLLER);

  private final ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");

  private NetworkTableEntry scalerSlider = tab.add("Speed scaler", 1)
  .withWidget(BuiltInWidgets.kNumberSlider)
  .withProperties(Map.of("min", 0.1, "max", 1, "block increment", 0.1))
  .getEntry();

  private NetworkTableEntry leftXScaler = tab.add("X Translation Scaler", 1)
  .withWidget(BuiltInWidgets.kNumberSlider)
  .withProperties(Map.of("min", 0, "max", 1, "block increment", 0.1))
  .getEntry();
  
  private NetworkTableEntry leftYScaler = tab.add("Y Translation Scaler", 1)
  .withWidget(BuiltInWidgets.kNumberSlider)
  .withProperties(Map.of("min", 0, "max", 1, "block increment", 0.1))
  .getEntry();

  private NetworkTableEntry rightXScaler = tab.add("Rotation Scaler", 1)
  .withWidget(BuiltInWidgets.kNumberSlider)
  .withProperties(Map.of("min", 0, "max", 1, "block increment", 0.1))
  .getEntry();

  public RobotContainer() {
    // The controls are for field-oriented driving:
    // Left stick Y axis -> forward and backwards movement
    // Left stick X axis -> left and right movement
    // Right stick X axis -> rotation
    m_drivetrainSubsystem.setDefaultCommand(new DefaultDriveCommand(
            m_drivetrainSubsystem,
            () -> -modifyAxis(m_controller.getLeftY()) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND * leftYScaler.getDouble(1.0),
            () -> -modifyAxis(m_controller.getLeftX()) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND * leftXScaler.getDouble(1.0),
            () -> -modifyAxis(m_controller.getRightX()) * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND * rightXScaler.getDouble(1.0)
    ));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    new Button(m_controller::getBackButton).whenPressed(m_drivetrainSubsystem::zeroGyroscope);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new InstantCommand();
  }

  private static double deadband(double value, double deadband) {
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

  private double modifyAxis(double value) {
    value = deadband(value, 0.05);

    value = Math.copySign(value * value, value);

    double scaler = scalerSlider.getDouble(1.0);

    value = value * scaler; //scale down value to slow robot

    return value;
  }
}
