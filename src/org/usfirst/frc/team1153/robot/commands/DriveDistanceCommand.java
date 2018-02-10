package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistanceCommand extends Command {

	private double targetPosRight;
	private double targetPosLeft;

	StringBuilder sb = new StringBuilder();

	public DriveDistanceCommand(double targetPosLeft, double targetPosRight) {
		requires(Robot.autoDrive);
		this.targetPosRight = targetPosRight;
		this.targetPosLeft = targetPosLeft;
		// Use requires() here to declare subsystem dependencies
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.autoDrive.configRightMotionMagic();
		Robot.autoDrive.configLeftMotionMagic();
		Robot.autoDrive.resetEncoders();
		
		Robot.autoDrive.enactRightMotorMotionMagic(targetPosRight);
		Robot.autoDrive.enactLeftMotorMotionMagic(targetPosLeft);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// sb.append("\tOut%:");
		// sb.append(motorSpeed);
		// sb.append("\tVel:");
		// sb.append(motorOutput);

		
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
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
