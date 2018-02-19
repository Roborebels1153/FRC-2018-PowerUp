package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.subsystems.ArmsHorizontal;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollectorLeftRightOutCommand extends Command {

    public CollectorLeftRightOutCommand() {
        requires(Robot.collectorArmsHorizontal);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.collectorArmsHorizontal.setState(ArmsHorizontal.STATE_OUT);
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
