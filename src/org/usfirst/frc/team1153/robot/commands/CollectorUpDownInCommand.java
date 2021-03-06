package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.subsystems.ArmsVertical;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollectorUpDownInCommand extends Command {

	long startTime;
	double waitTime;
	
    public CollectorUpDownInCommand(double waitTime) {
    	this.waitTime = waitTime;
        requires(Robot.collectorArmsVertical);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = System.currentTimeMillis();
    	Robot.collectorArmsVertical.setState(ArmsVertical.STATE_UP);
    	System.out.println("Init command is called");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (System.currentTimeMillis() - startTime) > (waitTime * 1000);
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Done moving arms up");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
