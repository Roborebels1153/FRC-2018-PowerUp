package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroTurnAbsoluteCommand extends Command {

	long startTime;
	double setpoint;
	int error;
	
    public GyroTurnAbsoluteCommand(double setpoint) {
    	this.setpoint = setpoint;
    	this.error = 5;
    }
    
    public GyroTurnAbsoluteCommand(double setpoint, int error) {
    	this.setpoint = setpoint;
    	this.error = error;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.autoDrive.setGyroPID(setpoint);
    	startTime = System.currentTimeMillis();
    	Robot.autoDrive.configTalonOutput();
    	Robot.autoDrive.runGyroPID(true);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.autoDrive.runGyroPID(true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return System.currentTimeMillis() - startTime >= 2000 || (Robot.autoDrive.getGyroAngle() < setpoint + error && Robot.autoDrive.getGyroAngle() > setpoint - error);
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("GYRO TURN FINISHED");
    	Robot.autoDrive.runGyroPID(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
