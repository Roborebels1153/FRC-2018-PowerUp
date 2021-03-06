package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Constants;
import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistanceCurvatureCommand extends Command {

	private double targetPosRight;
	private double targetPosLeft;

	private double leftScaleValue;
	private double rightScaleValue;

	
	long timeAtStart;

	public DriveDistanceCurvatureCommand(double setPoint, double leftCoefficient, double rightCoefficient) {
		requires(Robot.autoDrive);
		this.leftScaleValue = leftCoefficient;
		this.rightScaleValue = rightCoefficient;
		// right side must be inverted
		this.targetPosRight = Constants.TALON_TICKS_PER_INCH_RIGHT * setPoint * rightCoefficient * -1;
		this.targetPosLeft = Constants.TALON_TICKS_PER_INCH_LEFT * setPoint * leftCoefficient;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.autoDrive.resetEncoders();
		Robot.autoDrive.configRightMotionMagic(rightScaleValue);
		Robot.autoDrive.configLeftMotionMagic(leftScaleValue);

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
		// boolean rightTolerated =
		// Math.abs(Robot.autoDrive.getRightMotorSensorPosition()) > (targetPosRight *
		// 0.994)
		// && Math.abs(Robot.autoDrive.getRightMotorSensorPosition()) < (targetPosRight
		// * 1.006);
		// boolean leftTolerated =
		// Math.abs(Robot.autoDrive.getLeftMotorSensorPosition()) > (targetPosLeft *
		// 0.994)
		// && Math.abs(Robot.autoDrive.getLeftMotorSensorPosition()) < (targetPosLeft *
		// 1.006);

		boolean rightTolerated = Math.abs(Robot.autoDrive.getRightMotorSensorPosition()) > Math
				.abs(0.5 * targetPosRight);
		boolean leftTolerated = Math.abs(Robot.autoDrive.getLeftMotorSensorPosition()) > Math.abs(0.5 * targetPosLeft);

		boolean rightMotorsStopped = Math.abs(Robot.autoDrive.getRightMotorOutputPercent()) < 0.02;
		boolean leftMotorsStopped = Math.abs(Robot.autoDrive.getLeftMotorOutputPercent()) < 0.02;

		// return (rightMotorsStopped && leftMotorsStopped && rightTolerated &&
		// leftTolerated || System.currentTimeMillis() - timeAtStart > 2500);

		return (rightMotorsStopped && leftMotorsStopped && rightTolerated && leftTolerated);

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
