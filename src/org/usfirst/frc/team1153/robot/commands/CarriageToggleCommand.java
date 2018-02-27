package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem.State;
import org.usfirst.frc.team1153.robot.subsystems.ArmsHorizontal;
import org.usfirst.frc.team1153.robot.subsystems.Carriage;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CarriageToggleCommand extends Command {

    public CarriageToggleCommand() {
        requires(Robot.carriage);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	State currState = Robot.carriage.getState();
    	if (Carriage.STATE_DOWN.equals(currState)) {
    		Robot.carriage.setState(Carriage.STATE_UP);
    	} else if (Carriage.STATE_UP.equals(currState)) {
    		Robot.carriage.setState(Carriage.STATE_DOWN);
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
