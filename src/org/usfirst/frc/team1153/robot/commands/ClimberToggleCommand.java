package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem.State;
import org.usfirst.frc.team1153.robot.subsystems.ArmsHorizontal;
import org.usfirst.frc.team1153.robot.subsystems.ArmsVertical;
import org.usfirst.frc.team1153.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberToggleCommand extends Command {

	public ClimberToggleCommand() {
		requires(Robot.climber);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		State currState = Robot.climber.getState();
		if (Climber.STATE_EXTENDED.equals(currState)) {
			Robot.climber.setState(Climber.STATE_RETRACTED);
		} else if (Climber.STATE_RETRACTED.equals(currState)) {
			Robot.climber.setState(Climber.STATE_EXTENDED);
		} else if (currState == null) {
			Robot.climber.setState(Climber.STATE_RETRACTED);
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
