package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem.State;
import org.usfirst.frc.team1153.robot.subsystems.Carriage;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CarriageDownCommand extends Command {

	long startTime;
	double waitTime;

	State currState;

	public CarriageDownCommand(double waitTime) {
		this.waitTime = waitTime;
		requires(Robot.carriage);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		startTime = System.currentTimeMillis();
		currState = Robot.carriage.getState();
		Robot.carriage.setState(Carriage.STATE_DOWN);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		System.out.println("IsFinished called");
		if (Carriage.STATE_DOWN.equals(currState)) {
			return true;
		} else {
			return (System.currentTimeMillis() - startTime) > (waitTime * 1000);
		}
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
