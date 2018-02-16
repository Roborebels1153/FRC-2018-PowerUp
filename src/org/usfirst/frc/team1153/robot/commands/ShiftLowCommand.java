package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This Command shifts the robot into low gear!
 */
public class ShiftLowCommand extends Command {

    public ShiftLowCommand() {
        requires(Robot.autoDrive);
    }

    protected void initialize() {
    	Robot.autoDrive.shiftLow();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }


    protected void interrupted() {
    }
}
