/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1153.robot;

import org.usfirst.frc.team1153.robot.lib.RebelTrigger;
import org.usfirst.frc.team1153.robot.lib.StateScheduler;
import org.usfirst.frc.team1153.robot.subsystems.AutoDrive;
import org.usfirst.frc.team1153.robot.subsystems.Collector;
import org.usfirst.frc.team1153.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	static int loops = 0;

	public static OI oi;
	public static AutoDrive autoDrive;
	public static Shooter shooter;
	public static Collector collector;

	private SendableChooser<String> m_chooser = new SendableChooser<>();

	Button drRightTrigger;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		autoDrive = new AutoDrive();
		shooter = new Shooter();
		collector = new Collector();
		oi = new OI();

		StateScheduler.getInstance().addStateSubsystem(shooter);
		StateScheduler.getInstance().addStateSubsystem(collector);

		m_chooser.addDefault("Center", "Center");
		m_chooser.addDefault("Left", "Left");
		m_chooser.addDefault("Right", "Right");
		m_chooser.addDefault("Far Right", "Far Right");
		SmartDashboard.putData("Position", m_chooser);

		drRightTrigger = new RebelTrigger(oi.getDriverStick(), 3);
	}

	public static void updateDashboard() {
		// if (++loops >= 10) {
		// loops = 0;
		// SmartDashboard.putNumber("Right Motor Motor Output",
		// autoDrive.getRightMotorOutputPercent());
		// SmartDashboard.putNumber("Right Motor Motor Speed",
		// autoDrive.getRightMotorSpeed());
		SmartDashboard.putNumber("Right Motor Motion Magic Error", autoDrive.getRightMotorClosedLoopError());
		// // SmartDashboard.putNumber("Right Motor Active Trajectory Position",
		// // drive.getRightMotorActiveTrajectoryPosition());
		// // SmartDashboard.putNumber("Right Motor Active Trajectory Velocity",
		// // drive.getRightMotorActiveTrajectoryVelocity());
		SmartDashboard.putNumber("Right Motor Sensor Position", autoDrive.getRightMotorSensorPosition());
		SmartDashboard.putNumber("Right Motor Sensor Velocity", autoDrive.getRightMotorSensorVelocity());
		//

		// SmartDashboard.putNumber("Left Motor Motor Output",
		// autoDrive.getLeftMotorOutputPercent());
		// SmartDashboard.putNumber("Left Motor Motor Speed",
		// autoDrive.getLeftMotorSpeed());
		SmartDashboard.putNumber("Left Motor Motion Magic Error", autoDrive.getLeftMotorClosedLoopError());
		// // SmartDashboard.putNumber("Left Motor Active Trajectory Position",
		// // drive.getLeftMotorActiveTrajectoryPosition());
		// // SmartDashboard.putNumber("Left Motor Active Trajectory Velocity",
		// // drive.getLeftMotorActiveTrajectoryVelocity());
		SmartDashboard.putNumber("Left Motor Sensor Position", autoDrive.getLeftMotorSensorPosition());
		SmartDashboard.putNumber("Left Motor Sensor Velocity", autoDrive.getLeftMotorSensorVelocity());

		SmartDashboard.putNumber("PID ERROR", autoDrive.gyroError());
		SmartDashboard.putNumber("PID Output", autoDrive.getGyroOutput());
		//
		// SmartDashboard.putNumber("Left Motor Sensor Position",
		// autoDrive.getLeftMotorSensorPosition());
		// SmartDashboard.putNumber("Left Motor Sensor Velocity",
		// autoDrive.getLeftMotorSensorVelocity());
		//
		// // SmartDashboard.putNumber("Left Motor Output Voltage",
		// // leftMaster.getMotorOutputVoltage());
		// // SmartDashboard.putNumber("Left Motor Bus Voltage",
		// // leftMaster.getBusVoltage());
		// // SmartDashboard.putNumber("Left Motor Output Signal",
		// // leftMaster.getMotorOutputVoltage() / leftMaster.getBusVoltage());
		// //
		// // SmartDashboard.putNumber("Right Motor Output Voltage",
		// // rightMaster.getMotorOutputVoltage());
		// // SmartDashboard.putNumber("Right Motor Bus Voltage",
		// // rightMaster.getBusVoltage());
		// // SmartDashboard.putNumber("Right Motor Output Signal",
		// // rightMaster.getMotorOutputVoltage() /
		// rightMaster.getBusVoltage());
		// }

		SmartDashboard.putNumber("AD Gyro Reading", autoDrive.getGyroAngle());
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		StateScheduler.getInstance().notifyDisabled();
	}

	@Override
	public void disabledPeriodic() {
		updateDashboard();
		Scheduler.getInstance().run();
		StateScheduler.getInstance().runAll();
	}

	private boolean robotPosEqual(String position) {
		return m_chooser.getSelected().equalsIgnoreCase(position);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		StateScheduler.getInstance().notifyAuto();

		autoDrive.resetGyro();

		autoDrive.setEncoderAsFeedback();
		autoDrive.configTalonOutput();
		autoDrive.setFollowers();
		autoDrive.resetEncoders();
		// DRIVE FORWARD
		//autoCommand = new DriveDistanceCommand(15000, -15000);

		// TODO With the new way Stuart has designed the choosers, commands can no longer be run in the manner that they are below
		
		// autoCommand = new DriveAndTurn();
		// autoCommand = new GyroTurnCommand(90);

		String autoPattern = DriverStation.getInstance().getGameSpecificMessage();
		char switchPos = autoPattern.charAt(0);

		if ((robotPosEqual("Right") && switchPos == 'R') || (robotPosEqual("Left") && switchPos == 'L')) {
			// continue driving (with vision)
			// TODO: Add forward default command
		} else if (robotPosEqual("Center") && switchPos == 'R') {
			// turn Right then use vision to target the switch
			// TODO: Add right center default command
		} else if (robotPosEqual("Center") && switchPos == 'L') {
			// turn Left then use vision to target the switch
			// TODO: Add left center default command
		} else if (robotPosEqual("Far Right") && switchPos == 'R') {
			// TODO: Add far right switch right default command
		} else if (robotPosEqual("Far Right") && switchPos == 'L') {
			// TODO; Add far right switch left default command
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		StateScheduler.getInstance().runAll();

		updateDashboard();
	}

	@Override
	public void teleopInit() {
		StateScheduler.getInstance().notifyTeleop();

		autoDrive.resetEncoders();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		StateScheduler.getInstance().runAll();

		// TODO Integrate drive code from TeleDrive, talk with subclassing TeleDrive
		//autoDrive.drive(oi.getDriverStick());

		// drive.drive(oi.getDriverStick());
		updateDashboard();
		// autoDrive.drive(oi.getDriverStick());
		// autoDrive.resetEncoders();
		/**
		 * Test to make sure individual talons are working
		 * if(oi.getDriverStick().getRawButtonPressed(1)) { drive.testMotor(1); } else
		 * if (oi.getDriverStick().getRawButtonPressed(2)) { drive.testMotor(-1); }
		 */
		// autoDrive.drive(oi.getDriverStick());

		if (oi.getDriverStick().getRawButtonPressed(2)) {
			autoDrive.shiftHighTest();
		} else if (oi.getDriverStick().getRawButtonReleased(2)) {
			autoDrive.shiftLowTest();
		}

		if (drRightTrigger.get()) {
			autoDrive.shiftHigh();
		} else {
			autoDrive.shiftLow();
		}

		// if (oi.getDriverStick().getRawButtonPressed(2)) {
		// autoDrive.resetEncoders();
		// }

		if (oi.getDriverStick().getRawButtonPressed(3)) {
			autoDrive.stop();
		} else if (oi.getDriverStick().getRawButtonPressed(4)) {
			autoDrive.driveForward();
		} else if (oi.getDriverStick().getRawButtonPressed(1)) {
			autoDrive.driveBackward();
		}

		// autoDrive.drive(oi.getDriverStick());

		autoDrive.createDriveSignal();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
