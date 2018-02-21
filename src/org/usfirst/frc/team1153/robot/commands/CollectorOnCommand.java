package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollectorOnCommand extends Command {

	public CollectorOnCommand() {
		requires(Robot.collector);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.collector.setState(Collector.STATE_RUNNING);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("end clause reached");
		Robot.collector.stopMotors();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
