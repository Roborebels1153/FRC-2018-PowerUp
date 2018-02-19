package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.subsystems.ArmsVertical;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollectorUpDownOutCommand extends Command {

    public CollectorUpDownOutCommand() {
        requires(Robot.collectorArmsVertical);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.collectorArmsVertical.setState(ArmsVertical.STATE_DOWN);
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
