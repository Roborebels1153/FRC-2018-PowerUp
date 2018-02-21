package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollectorSetRPMCommand extends Command {

    public CollectorSetRPMCommand() {
      requires(Robot.collector);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.collector.resetEncoder();
    	Robot.collector.configCollectorMotionMagic();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.collector.enactCollectorMotionMagic();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
