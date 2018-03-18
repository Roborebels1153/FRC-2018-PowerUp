package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroTurnAbsoluteCommand extends Command {

	long startTime;
	double setpoint;
	double tolerance;
	
    public GyroTurnAbsoluteCommand(double setpoint) {
    	this.setpoint = setpoint;
    	this.tolerance = 5;
    }
    
    public GyroTurnAbsoluteCommand(double setpoint, double tolerance) {
    	this.setpoint = setpoint;
    	this.tolerance = tolerance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.autoDrive.setGyroTwoPID(setpoint);
    	startTime = System.currentTimeMillis();
    	Robot.autoDrive.configTalonOutput();
    	Robot.autoDrive.runGyroTwoPID(true);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.autoDrive.runGyroTwoPID(true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return System.currentTimeMillis() - startTime >= 2000 ||(Math.abs(Robot.autoDrive.getGyroAngle() - setpoint) < tolerance && Math.abs(Robot.autoDrive.getRightMotorOutputPercent()) < 0.05);/*(Robot.autoDrive.getGyroAngle() < setpoint + tolerance && Robot.autoDrive.getGyroAngle() > setpoint - tolerance);*/
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("GYRO TURN FINISHED");
    	Robot.autoDrive.runGyroTwoPID(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
