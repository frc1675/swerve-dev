package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DrivetrainSubsystem;

public class RotateCommand extends CommandBase{
    private DrivetrainSubsystem drive;
    private Rotation2d target;
    private boolean rotationDirection;

    private PIDController pid = new PIDController(3, 0.5, 1);

    public RotateCommand(DrivetrainSubsystem drive, Rotation2d target) {
        this.drive = drive;
        this.target = target;
        addRequirements(drive);
    }

    @Override 
    public void initialize() {
        Rotation2d diff = drive.getGyroscopeRotation().minus(target);
        if(diff.getDegrees() < 0) {
            rotationDirection = true;
        }else {
            rotationDirection = false;
        }
    }

    @Override
    public boolean isFinished() {
        return withinAcceptableOffset();
    }

    private boolean withinAcceptableOffset() {
        if(drive.getGyroscopeRotation().equals(target)) {
            return true;
        }

        if(Math.abs(drive.getGyroscopeRotation().minus(target).getDegrees()) <= Constants.ACCEPTABLE_AUTOROTATE_ERROR_DEGREES) {
            return true;
        } 

        return false;
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("radians away", drive.getGyroscopeRotation().minus(target).getRadians());
        drive.rotate(rotationDirection, pid.calculate(Math.abs(drive.getGyroscopeRotation().minus(target).getRadians())) * -1);
    }

    @Override 
    public void end(boolean interrupted) {
        drive.drive(new ChassisSpeeds(0, 0, 0));
    }
}

