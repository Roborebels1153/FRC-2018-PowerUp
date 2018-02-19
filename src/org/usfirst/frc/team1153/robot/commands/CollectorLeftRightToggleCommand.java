package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.subsystems.Collector.LeftRight;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollectorLeftRightToggleCommand extends Command {

    public CollectorLeftRightToggleCommand() {
        requires(Robot.collector);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	LeftRight newState = Robot.collector.toggleleftRightPistons();
    	if (LeftRight.OUT.equals(newState)) {
    		Robot.collector.leftRightPistonOut();
    	} else if (LeftRight.IN.equals(newState)) {
    		Robot.collector.leftRightPistonsIn();
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
