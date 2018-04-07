package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Constants;
import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistanceCommandWithLidar extends Command {

	private double targetPosRight;
	private double targetPosLeft;
	
	int takeAway;

	
	long timeAtStart;
	double waitTime;
	
	double speedCoe = 1;
	
	public DriveDistanceCommandWithLidar(double wait, int takeAway) {
		requires(Robot.autoDrive);
		this.waitTime = wait;
		this.takeAway = takeAway;
	}

	public DriveDistanceCommandWithLidar(double wait, double speedCoe, int takeAway) {
		requires(Robot.autoDrive);
		this.waitTime = wait;
		this.speedCoe = speedCoe;
		this.takeAway = takeAway;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.autoDrive.resetEncoders();
		Robot.autoDrive.configRightMotionMagic(speedCoe);
		Robot.autoDrive.configLeftMotionMagic(speedCoe);

		timeAtStart = System.currentTimeMillis();

		targetPosLeft = ((Robot.lidar.distance(false))/2.5 - takeAway) * Constants.TALON_TICKS_PER_INCH_LEFT;
		targetPosRight = -1 * ((Robot.lidar.distance(false))/2.5 -takeAway) * Constants.TALON_TICKS_PER_INCH_RIGHT;

		System.out.println("Init Command is called");
		System.out.println(targetPosRight);
		System.out.println(targetPosLeft);


	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		Robot.autoDrive.enactLeftMotorMotionMagic(targetPosLeft);
		Robot.autoDrive.enactRightMotorMotionMagic(targetPosRight);

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
//		boolean rightTolerated = Math.abs(Robot.autoDrive.getRightMotorSensorPosition()) > (targetPosRight * 0.994)
//				&& Math.abs(Robot.autoDrive.getRightMotorSensorPosition()) < (targetPosRight * 1.006);
//		boolean leftTolerated = Math.abs(Robot.autoDrive.getLeftMotorSensorPosition()) > (targetPosLeft * 0.994)
//				&& Math.abs(Robot.autoDrive.getLeftMotorSensorPosition()) < (targetPosLeft * 1.006);
		
		boolean rightTolerated = Math.abs(Robot.autoDrive.getRightMotorSensorPosition()) > Math.abs(0.5 * targetPosRight);
		boolean leftTolerated  = Math.abs(Robot.autoDrive.getLeftMotorSensorPosition())> Math.abs(0.5 * targetPosLeft);

		
		boolean rightMotorsStopped = Math.abs(Robot.autoDrive.getRightMotorOutputPercent()) < 0.02;
		boolean leftMotorsStopped = Math.abs(Robot.autoDrive.getLeftMotorOutputPercent()) < 0.02;


//		return (rightMotorsStopped && leftMotorsStopped && rightTolerated && leftTolerated || System.currentTimeMillis() - timeAtStart > 2500);
		
		return (rightMotorsStopped && leftMotorsStopped && rightTolerated && leftTolerated || ((System.currentTimeMillis() - timeAtStart) > (waitTime * 1000)));

	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("MotionMagic Finished");
		Robot.autoDrive.stopMotionMagic();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
