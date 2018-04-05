package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Constants;
import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistanceCommand extends Command {

	private double targetPosRight;
	private double targetPosLeft;

	StringBuilder sb = new StringBuilder();
	long timeAtStart;
	double waitTime;
	
	double speedCoe = 1;
	
	public DriveDistanceCommand(double targetPosLeft, double targetPosRight, double wait) {
		requires(Robot.autoDrive);
		this.targetPosRight = Constants.TALON_TICKS_PER_INCH_RIGHT * targetPosRight;
		this.targetPosLeft = Constants.TALON_TICKS_PER_INCH_LEFT * targetPosLeft;
		this.waitTime = wait;
	}

	public DriveDistanceCommand(double targetPosLeft, double targetPosRight, double wait, double speedCoe) {
		requires(Robot.autoDrive);
		this.targetPosRight = Constants.TALON_TICKS_PER_INCH_RIGHT * targetPosRight;
		this.targetPosLeft = Constants.TALON_TICKS_PER_INCH_LEFT * targetPosLeft;
		this.waitTime = wait;
		this.speedCoe = speedCoe;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.autoDrive.resetEncoders();
		Robot.autoDrive.configRightMotionMagic(speedCoe);
		Robot.autoDrive.configLeftMotionMagic(speedCoe);

		timeAtStart = System.currentTimeMillis();

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
//		
		
		boolean rightTolerated = Math.abs(Robot.autoDrive.getRightMotorSensorPosition()) > Math.abs(0.5 * targetPosRight);
		boolean leftTolerated  = Math.abs(Robot.autoDrive.getLeftMotorSensorPosition())> Math.abs(0.5 * targetPosLeft);

		
		boolean rightMotorsStopped = Math.abs(Robot.autoDrive.getRightMotorOutputPercent()) < 0.02;
		boolean leftMotorsStopped = Math.abs(Robot.autoDrive.getLeftMotorOutputPercent()) < 0.02;


//		return (rightMotorsStopped && leftMotorsStopped && rightTolerated && leftTolerated || System.currentTimeMillis() - timeAtStart > 2500);
		
		//return (rightMotorsStopped && leftMotorsStopped && rightTolerated && leftTolerated || ((System.currentTimeMillis() - timeAtStart) > (waitTime * 1000)));
		return false;

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
