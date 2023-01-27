package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DrivetrainSubsystem;

public class DefaultDriveCommand extends CommandBase {
    private final DrivetrainSubsystem m_drivetrainSubsystem;

    private final DoubleSupplier m_translationXSupplier;
    private final DoubleSupplier m_translationYSupplier;
    private final DoubleSupplier m_rotationSupplier;

    private final PIDController pid = new PIDController(Constants.PROPORTIONAL_COEFFICENT, Constants.INTEGRAL_COEFFICENT, Constants.DERIVATIVE_COEFFICENT);
    private Rotation2d target;
    private boolean updateTarget = true;

    public DefaultDriveCommand(DrivetrainSubsystem drivetrainSubsystem, DoubleSupplier translationXSupplier,
            DoubleSupplier translationYSupplier, DoubleSupplier rotationSupplier) {
        this.m_drivetrainSubsystem = drivetrainSubsystem;
        this.m_translationXSupplier = translationXSupplier;
        this.m_translationYSupplier = translationYSupplier;
        this.m_rotationSupplier = rotationSupplier;

        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void execute() {
        updateTarget();
        m_drivetrainSubsystem.drive(
                ChassisSpeeds.fromFieldRelativeSpeeds(
                        m_translationXSupplier.getAsDouble(),
                        m_translationYSupplier.getAsDouble(),
                        getRotation(),
                        m_drivetrainSubsystem.getGyroscopeRotation()));
    }

    private void updateTarget() {
        if(m_rotationSupplier.getAsDouble() == 0) {
            if (updateTarget) {
                target = m_drivetrainSubsystem.getGyroscopeRotation();
                updateTarget = false;
            }
        }else {
            updateTarget = true;
        }
        
    }

    private double getRotation() {
        if (m_rotationSupplier.getAsDouble() == 0 && (m_translationXSupplier.getAsDouble() != 0 || m_translationYSupplier.getAsDouble() != 0) && target != null) {
            return -pid.calculate(m_drivetrainSubsystem.getGyroscopeRotation().minus(target).getRadians());
        } else {
            return m_rotationSupplier.getAsDouble();
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_drivetrainSubsystem.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    }
}
