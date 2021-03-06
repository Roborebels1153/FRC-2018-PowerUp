package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ServoToggleCommand extends Command {

	public ServoToggleCommand() {
		requires(Robot.autoDrive);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (!(Robot.autoDrive.getServoBAngle() > 130)) {
			Robot.autoDrive.setServoValue(180);
		} else {
			Robot.autoDrive.setServoValue(90);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
