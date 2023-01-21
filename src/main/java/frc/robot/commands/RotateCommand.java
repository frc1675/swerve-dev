package frc.robot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DrivetrainSubsystem;

public class RotateCommand extends CommandBase{
    private DrivetrainSubsystem drive;
    private Rotation2d target;

    private final ShuffleboardTab tab = Shuffleboard.getTab("Tab 4");
    private final NetworkTableEntry netTableDiff = tab.add("Diff", 0).withWidget(BuiltInWidgets.kTextView).getEntry();
    private final NetworkTableEntry withinOffsets = tab.add("Within offsets", false).withWidget(BuiltInWidgets.kTextView).getEntry();

    public RotateCommand(DrivetrainSubsystem drive, Rotation2d target) {
        this.drive = drive;
        this.target = target;
        addRequirements(drive);
    }

    @Override 
    public void initialize() {
        Rotation2d diff;
        if(RobotBase.isSimulation()) {
            diff = drive.getGyroscopeRotation().minus(new Rotation2d(15));
        }else {
            diff = drive.getGyroscopeRotation().minus(target);
        }

        netTableDiff.setDouble(diff.getDegrees());

        if(diff.getDegrees() < 0) {
            drive.rotate(true);
        }else {
            drive.rotate(false);
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
        withinOffsets.setBoolean(withinAcceptableOffset());
    }

    @Override 
    public void end(boolean interrupted) {
        drive.rotate();
    }
}

