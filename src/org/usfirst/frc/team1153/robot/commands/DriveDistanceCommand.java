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

	public DriveDistanceCommand(double targetPosLeft, double targetPosRight) {
		requires(Robot.autoDrive);
		this.targetPosRight = Constants.TALON_TICKS_PER_INCH * targetPosRight;
		this.targetPosLeft = Constants.TALON_TICKS_PER_INCH * targetPosLeft;
		// Use requires() here to declare subsystem dependencies
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.autoDrive.resetEncoders();
		Robot.autoDrive.configRightMotionMagic();
		Robot.autoDrive.configLeftMotionMagic();

		timeAtStart = System.currentTimeMillis();

		System.out.println("Init Command is called");

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// sb.append("\tOut%:");
		// sb.append(motorSpeed);
		// sb.append("\tVel:");
		// sb.append(motorOutput);
		Robot.autoDrive.enactLeftMotorMotionMagic(targetPosLeft);
		Robot.autoDrive.enactRightMotorMotionMagic(targetPosRight);

		// error = Robot.drive.getRightMotorClosedLoopError();
		// sb.append("\terr:");
		// sb.append(error);
		// sb.append("\ttrg:");
		// sb.append(targetPosRight);
		//
		// if (++loops >= 10) {
		// loops = 0;
		// //System.out.println(sb.toString());
		// }

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// return false;
		// return (Math.abs(Robot.autoDrive.getLeftMotorClosedLoopError()) < 30 &&
		// Math.abs(Robot.autoDrive.getLeftMotorClosedLoopError()) < 30
		// && System.currentTimeMillis() - timeAtStart > 1000);
		boolean rightTolerated = Math.abs(Robot.autoDrive.getRightMotorSensorPosition()) > (targetPosRight * 0.994)
				&& Math.abs(Robot.autoDrive.getRightMotorSensorPosition()) < (targetPosRight *  1.006);
		boolean leftTolerated = Math.abs(Robot.autoDrive.getLeftMotorSensorPosition()) > (targetPosLeft * 0.994)
				&& Math.abs(Robot.autoDrive.getLeftMotorSensorPosition()) < (targetPosLeft * 1.006);

		return (rightTolerated && leftTolerated && System.currentTimeMillis() - timeAtStart > 500);

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
