package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GyroAbsWithLidar extends Command {

	long startTime;
	double setpoint;
	double tolerance = 5;
	double motorTol = 0.05;
	
	int threshold = 200;
	boolean gotFirstEdge = false;
	boolean gotSecondEdge = false;
	boolean gotCenterCubeAngle = false;
	double firstEdgeAngle = 0.0;
	double secondEdgeAngle = 0.0;
	double centerCubeAngle = 0.0;
	
	int loopCountFirstEdge = 0;
	int loopCountSecondEdge = 0;
	int lidarLoopCount = 0;
	
	int lidarDistance;
	
	long timeOutMillis = 15000;
	
    public GyroAbsWithLidar(double setpoint) {
    	this.setpoint = setpoint;
    }
    
    public GyroAbsWithLidar(double setpoint, double tolerance) {
    	this.setpoint = setpoint;
    	this.tolerance = tolerance;
    }

    public GyroAbsWithLidar(double setpoint, double tolerance, double motorTol) {
    	this.setpoint = setpoint;
    	this.tolerance = tolerance;
    	this.motorTol = motorTol;

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
    	
    	// lidar measurement
    	lidarLoopCount++;
    	if (lidarLoopCount % 100 == 0) {
    		lidarDistance = Robot.lidar.distance(true);
    		System.out.println("Lidar Correlated Distance = " + lidarDistance);
    	} else {
    		lidarDistance = Robot.lidar.distance(false);
    		System.out.println("Lidar Distance = " + lidarDistance);
    	}
    	
    	// Ignore false values
    	if (lidarDistance < 0) {
    		return;
    	}
    	SmartDashboard.putNumber("Lidar Distance", lidarDistance);
    	// When we reach the first edge, save the current robot angle
    	if (!gotFirstEdge && lidarDistance < threshold) {
    		loopCountFirstEdge = lidarLoopCount;
    		System.out.print("First edge: " + loopCountFirstEdge);
    		gotFirstEdge = true;
    		firstEdgeAngle = Robot.autoDrive.getGyroAngle();
    	} 
    	
    	// When we reach the second edge, save the current robot angle
    	if (gotFirstEdge && !gotSecondEdge && lidarDistance > threshold) {
    		loopCountSecondEdge = lidarLoopCount;
    		System.out.print("Second edge: " + loopCountSecondEdge);
    		gotSecondEdge = true;
    		secondEdgeAngle = Robot.autoDrive.getGyroAngle();
    	} 
    	
    	if (!gotCenterCubeAngle && gotFirstEdge && gotSecondEdge) {
    		centerCubeAngle = (secondEdgeAngle + firstEdgeAngle)/2;
    		System.out.println("Got both edges, turn angle = " + centerCubeAngle);
    		gotCenterCubeAngle = true;
    		Robot.autoDrive.runGyroTwoPID(false);
    		Robot.autoDrive.setGyroTwoPID(centerCubeAngle);
    		this.setpoint = centerCubeAngle;
    		Robot.autoDrive.runGyroTwoPID(true);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean angleWithinTolerance = false;
    	boolean motorOutputPercentWithinTolerance = false;
    	double gyroAngle = Robot.autoDrive.getGyroAngle();
    	double motorOutputPercent = Robot.autoDrive.getRightMotorOutputPercent();
 
    	if (Math.abs(gyroAngle - setpoint) < tolerance) {
    		angleWithinTolerance = true;
    	} else {
    		//System.out.println("GyroAbsOneSideLidar target angle: " + setpoint + " actual angle: " + gyroAngle);
    	}
    	if (Math.abs(motorOutputPercent) < motorTol) {
    		motorOutputPercentWithinTolerance = true;
    	} else {
    		//System.out.println("GyroAbsOneSideLidar motor output limit: " + motorTol + " actual: " + motorOutputPercent);
    	}
    	
    	if (gotFirstEdge && gotSecondEdge && angleWithinTolerance && motorOutputPercentWithinTolerance) {
    		System.out.println("GyroAbsOneSideLidar, angle: " + gyroAngle + " angle tol:" + tolerance + " motor output percent: " + motorOutputPercent + " output tol: " + motorTol);
    		
    		lidarDistance = Robot.lidar.distance(false);
    		return true;
    	} else {
    	   	if (System.currentTimeMillis() - startTime >= timeOutMillis) {
        		System.out.println("GyroAbsOneSideLidar timed out");
        		return true;
        	} else {
        		return false;
        	}
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("GYRO TURN FINISHED");
    	System.out.println("Setpoint: " + this.setpoint + " Actual: " + Robot.autoDrive.getGyroAngle() + " Motor Output: " + Robot.autoDrive.getRightMotorOutputPercent());
    	Robot.autoDrive.runGyroTwoPID(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
