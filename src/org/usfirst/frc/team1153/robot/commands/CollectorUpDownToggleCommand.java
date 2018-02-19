package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.subsystems.Collector.LeftRight;
import org.usfirst.frc.team1153.robot.subsystems.Collector.UpDown;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollectorUpDownToggleCommand extends Command {

    public CollectorUpDownToggleCommand() {
        requires(Robot.collector);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	UpDown newState = Robot.collector.toggleUpDownPistons();
    	if (UpDown.UP.equals(newState)) {
    		Robot.collector.upDownPistonsIn();
    	} else if (UpDown.DOWN.equals(newState)) {
    		Robot.collector.upDownPistonOut();
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
