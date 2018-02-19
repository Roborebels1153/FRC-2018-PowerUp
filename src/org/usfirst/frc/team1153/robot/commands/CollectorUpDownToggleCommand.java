package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem;
import org.usfirst.frc.team1153.robot.subsystems.ArmsVertical;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollectorUpDownToggleCommand extends Command {

    public CollectorUpDownToggleCommand() {
        requires(Robot.collectorArmsVertical);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	StateSubsystem.State currState = Robot.collectorArmsVertical.getState();
    	if (ArmsVertical.STATE_UP.equals(currState)) {
    		Robot.collectorArmsVertical.setState(ArmsVertical.STATE_DOWN);
    	} else if (ArmsVertical.STATE_DOWN.equals(currState)) {
    		Robot.collectorArmsVertical.setState(ArmsVertical.STATE_UP);
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
