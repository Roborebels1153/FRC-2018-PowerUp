package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.subsystems.ArmsHorizontal;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollectorLeftRightInCommand extends Command {
	
	long startTime;
	double waitTime;

    public CollectorLeftRightInCommand(double waitTime) {
    	this.waitTime = waitTime;
        requires(Robot.collectorArmsHorizontal);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = System.currentTimeMillis();
    	Robot.collectorArmsHorizontal.setState(ArmsHorizontal.STATE_IN);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("IsFinished called");
        return (System.currentTimeMillis() - startTime) > (waitTime * 1000);
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Ended Collector Left Right in Command");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
