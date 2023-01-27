package frc.robot;

import java.util.Map;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.RotateCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

public class RobotContainer {

  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();

  private final Joystick m_controller = new Joystick(Constants.DRIVER_CONTROLLER);
  private final JoystickButton aButton = new JoystickButton(m_controller, Constants.A_BUTTON);
  private final JoystickButton backButton = new JoystickButton(m_controller, Constants.BACK_BUTTON);

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
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    // The controls are for field-oriented driving:
    // Left stick Y axis -> forward and backwards movement
    // Left stick X axis -> left and right movement
    // Right stick X axis -> rotation
    m_drivetrainSubsystem.setDefaultCommand(new DefaultDriveCommand(
            m_drivetrainSubsystem,
            () -> -modifyAxis(m_controller.getRawAxis(Constants.LEFT_Y_AXIS)) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND * leftYScaler.getDouble(1.0),
            () -> -modifyAxis(m_controller.getRawAxis(Constants.LEFT_X_AXIS)) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND * leftXScaler.getDouble(1.0),
            () -> -modifyAxis(m_controller.getRawAxis(Constants.RIGHT_X_AXIS)) * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND * rightXScaler.getDouble(1.0)
    ));

    backButton.whenPressed(m_drivetrainSubsystem::zeroGyroscope);
    aButton.whenHeld(new RotateCommand(m_drivetrainSubsystem, new Rotation2d(0)));
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
