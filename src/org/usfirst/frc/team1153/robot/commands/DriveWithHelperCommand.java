package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.OI;
import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.lib.CheesyDriveHelper;
import org.usfirst.frc.team1153.robot.lib.DriveSignal;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithHelperCommand extends Command {

	CheesyDriveHelper helper;

	public DriveWithHelperCommand() {
		requires(Robot.autoDrive);
		helper = new CheesyDriveHelper();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		/**
		 * Generic Code provided by BOB 319
		 */
		// boolean quickTurn = Robot.autoDrive.quickTurnController();
		// double moveValue = Robot.oi.getDriverStick().leftStick.getY();
		// double rotateValue = Robot.oi.driverController.rightStick.getX();
		// DriveSignal driveSignal = helper.cheesyDrive(-moveValue, rotateValue,
		// quickTurn, false);
		// Robot.autoDrive.drive(ControlMode.PercentOutput, driveSignal);
		
		boolean quickTurn = Robot.autoDrive.quickTurnController();
		double moveValue = Robot.oi.getDriverStick().getRawAxis(OI.JOYSTICK_LEFT_Y);
		double rotateValue =Robot.oi.getDriverStick().getRawAxis(OI.JOYSTICK_RIGHT_X);
		DriveSignal driveSignal = helper.cheesyDrive(-moveValue, rotateValue, quickTurn, false);
    	Robot.autoDrive.driveWithHelper(ControlMode.PercentOutput, driveSignal);

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
