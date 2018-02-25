package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem;
import org.usfirst.frc.team1153.robot.subsystems.PTO;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PTOToggleCommand extends Command {

    public PTOToggleCommand() {
        requires(Robot.pto);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	StateSubsystem.State currState = Robot.pto.getState();
    	if (PTO.STATE_ENGAGED.equals(currState)) {
    		Robot.pto.setState(PTO.STATE_DISENGAGED);
    	} else if (PTO.STATE_DISENGAGED.equals(currState)) {
    		Robot.pto.setState(PTO.STATE_ENGAGED);
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
