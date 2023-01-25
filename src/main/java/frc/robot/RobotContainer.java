package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.util.JoystickModification;

public class RobotContainer {

  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();

  private final XboxController m_controller = new XboxController(Constants.DRIVER_CONTROLLER);

  public RobotContainer() {
    // The controls are for field-oriented driving:
    // Left stick Y axis -> forward and backwards movement
    // Left stick X axis -> left and right movement
    // Right stick X axis -> rotation
    m_drivetrainSubsystem.setDefaultCommand(new DefaultDriveCommand(
            m_drivetrainSubsystem,
            () -> -JoystickModification.modifyAxis(m_controller.getLeftY()) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND * JoystickModification.getLeftYScaler(1.0),
            () -> -JoystickModification.modifyAxis(m_controller.getLeftX()) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND * JoystickModification.getLeftXScaler(1.0),
            () -> -JoystickModification.modifyAxis(m_controller.getRightX()) * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND * JoystickModification.getRightXScaler(1.0)
    ));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    new Button(m_controller::getBackButton).whenPressed(m_drivetrainSubsystem::zeroGyroscope);
  }

  public Command getAutonomousCommand() {
    return new InstantCommand();
  }
}
