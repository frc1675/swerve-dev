package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.RotateCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.util.JoystickModification;

public class RobotContainer {

  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();

  private final Joystick m_controller = new Joystick(Constants.DRIVER_CONTROLLER);
  private final JoystickButton aButton = new JoystickButton(m_controller, Constants.A_BUTTON);
  private final JoystickButton backButton = new JoystickButton(m_controller, Constants.BACK_BUTTON);

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
            () -> -JoystickModification.modifyAxis(m_controller.getRawAxis(Constants.LEFT_Y_AXIS)) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND * JoystickModification.getLeftYScaler(1.0),
            () -> -JoystickModification.modifyAxis(m_controller.getRawAxis(Constants.LEFT_X_AXIS)) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND * JoystickModification.getLeftXScaler(1.0),
            () -> -JoystickModification.modifyAxis(m_controller.getRawAxis(Constants.RIGHT_X_AXIS)) * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND * JoystickModification.getRightXScaler(1.0)
    ));

    backButton.whenPressed(m_drivetrainSubsystem::zeroGyroscope);
    aButton.whenHeld(new RotateCommand(m_drivetrainSubsystem, new Rotation2d(0)));
    }

  public Command getAutonomousCommand() {
    return new InstantCommand();
  }
}
