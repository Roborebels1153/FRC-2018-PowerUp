package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroTurnCommand extends Command {

    public GyroTurnCommand(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	Robot.autoDrive.setGyroPID(setpoint);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.autoDrive.runGyroPID(true);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.autoDrive.gyroWithinTolerance();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.autoDrive.runGyroPID(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
