package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CheckForLimitSwitchCommand extends Command {
	
	

    public CheckForLimitSwitchCommand() {
        requires(Robot.collectorArmsVertical);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (!Robot.collectorArmsVertical.getRightLimitSwitchState()) && (!Robot.collectorArmsVertical.getLeftLimitSwitchState());
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Limit Switches Are True");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
