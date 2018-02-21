package org.usfirst.frc.team1153.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class WaitCommand extends Command {

	double waitTime;
	long startTime;

	public WaitCommand(double waitTime) {
		this.waitTime = waitTime;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		//setTimeout(waitTime);
		startTime = System.currentTimeMillis();
		System.out.println("Waiting " + waitTime + " second(s)");
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}
	
	protected boolean isFinished() {
		return (System.currentTimeMillis() - startTime)/1000 > waitTime;
 	}

	// Make this return true when this Command no longer needs to run execute()

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Done Waiting " + waitTime + " second(s)");

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
