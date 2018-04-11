package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroAbsOneSide extends Command {

	long startTime;
	double setpoint;
	double tolerance = 5;
	double motorTol = 0.05;
	
    public GyroAbsOneSide(double setpoint) {
    	this.setpoint = setpoint;
    }
    
    public GyroAbsOneSide(double setpoint, double tolerance) {
    	this.setpoint = setpoint;
    	this.tolerance = tolerance;
    }

    public GyroAbsOneSide(double setpoint, double tolerance, double motorTol) {
    	this.setpoint = setpoint;
    	this.tolerance = tolerance;
    	this.motorTol = motorTol;

    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.autoDrive.setGyroOnePID(setpoint);
    	startTime = System.currentTimeMillis();
    	Robot.autoDrive.configTalonOutput();
    	Robot.autoDrive.runGyroOnePID(true);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.autoDrive.runGyroOnePID(true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return System.currentTimeMillis() - startTime >= 1500 ||(Math.abs(Robot.autoDrive.getGyroAngle() - setpoint) < tolerance && Math.abs(Robot.autoDrive.getRightMotorOutputPercent()) < motorTol);/*(Robot.autoDrive.getGyroAngle() < setpoint + tolerance && Robot.autoDrive.getGyroAngle() > setpoint - tolerance);*/
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("GYRO TURN FINISHED");
    	System.out.println("Setpoint: " + this.setpoint + " Actual: " + Robot.autoDrive.getGyroAngle());
    	Robot.autoDrive.runGyroOnePID(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
