/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1153.robot;

import org.usfirst.frc.team1153.robot.lib.StateScheduler;
import org.usfirst.frc.team1153.robot.subsystems.Collector;
import org.usfirst.frc.team1153.robot.subsystems.Drive;
import org.usfirst.frc.team1153.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
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

	public static Drive drive;
	public static OI oi;
	public static Shooter shooter;
	public static Collector collector;

	private SendableChooser<String> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		drive = new Drive();
		oi = new OI();
		shooter = new Shooter();
		collector = new Collector();

		StateScheduler.getInstance().addStateSubsystem(shooter);
		StateScheduler.getInstance().addStateSubsystem(collector);

		m_chooser.addDefault("Center", "Center");
		m_chooser.addDefault("Left", "Left");
		m_chooser.addDefault("Right", "Right");
		m_chooser.addDefault("Far Right", "Far Right");
		SmartDashboard.putData("Position", m_chooser);

		drive.setIndenturedServants();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		StateScheduler.getInstance().notifyDisabled();
	}

	@Override
	public void disabledPeriodic() {
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
	}

	@Override
	public void teleopInit() {
		StateScheduler.getInstance().notifyTeleop();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		StateScheduler.getInstance().runAll();

		drive.drive(oi.getDriverStick());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
