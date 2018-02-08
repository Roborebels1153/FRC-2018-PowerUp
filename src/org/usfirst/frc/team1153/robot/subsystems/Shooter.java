package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {

	private Solenoid shooterA;
	private Solenoid shooterB;
	private Solenoid shooterC;
	private Solenoid shooterD;

	public Shooter() {
		shooterA = new Solenoid(RobotMap.SHOOTER_SOLENOID_A);
		shooterB = new Solenoid(RobotMap.SHOOTER_SOLENOID_B);
		shooterC = new Solenoid(RobotMap.SHOOTER_SOLENOID_C);
		shooterD = new Solenoid(RobotMap.SHOOTER_SOLENOID_D);

		initCommand();
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {

	}

	public void initCommand() {
		shooterA.set(false);
		shooterB.set(false);
		shooterC.set(false);
		shooterD.set(false);
	}

	public void firePistonShooters() {
		shooterA.set(true);
		shooterB.set(true);
		shooterC.set(true);
		shooterD.set(true);
	}

	public void retractPistonShooters() {
		shooterA.set(false);
		shooterB.set(false);
		shooterC.set(false);
		shooterD.set(false);
	}
	
	public void getShooterSolenoidState() {
		shooterA.get();
	}
}
