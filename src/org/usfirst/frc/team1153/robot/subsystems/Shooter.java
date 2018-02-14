package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.RobotMap;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Manages all hardware for the Shooter (shopping basket) assembly
 */
public class Shooter extends StateSubsystem {

	/**
	 * Shooter is in the fire position and all pistons are out
	 */
	public static final State STATE_FIRE = new StateSubsystem.State("fire");
	/**
	 * Shooter is in the retracted position with all pistons retracted
	 */
	public static final State STATE_RETRACT = new StateSubsystem.State("retract");

	public enum Power {
		FULL, HALF
	}

	private Power power = Power.FULL;

	private Solenoid shooterA;
	private Solenoid shooterB;
	private Solenoid shooterC;
	private Solenoid shooterD;

	public Shooter() {
		shooterA = new Solenoid(RobotMap.SHOOTER_SOLENOID_A);
		shooterB = new Solenoid(RobotMap.SHOOTER_SOLENOID_B);
		shooterC = new Solenoid(RobotMap.SHOOTER_SOLENOID_C);
		shooterD = new Solenoid(RobotMap.SHOOTER_SOLENOID_D);

		registerState(STATE_FIRE);
		registerState(STATE_RETRACT);
	}

	@Override
	public void initDefaultCommand() {
	}

	/**
	 * Simultaneously sets the state of all pistons
	 * 
	 * @param state
	 *            State to set to pistons
	 */
	private void setAllPistonState(boolean state) {
		setPrimaryPistonState(state);
		if (power == Power.FULL) {
			setSecondaryPistonState(state);
		}
	}

	/**
	 * Sets the state of the primary pistons only
	 * 
	 * @param state
	 *            State to set to primary pistons
	 */
	private void setPrimaryPistonState(boolean state) {
		shooterA.set(state);
		shooterB.set(state);
	}

	/**
	 * Sets the state of the secondary pistons only
	 * 
	 * @param state
	 *            State to set to secondary pistons
	 */
	private void setSecondaryPistonState(boolean state) {
		shooterC.set(state);
		shooterD.set(state);
	}

	/**
	 * Called when shooter first begins fire state
	 */
	public void fireInit() {
		setAllPistonState(true);
	}

	/**
	 * Called when shooter first begins retract state
	 */
	public void retractInit() {
		setAllPistonState(false);
	}

	/**
	 * Used to set whether or not we use all or half of the pistons
	 * 
	 * @param power
	 *            Power to change the subsystem to
	 */
	public void setPower(Power power) {
		this.power = power;

		if (power == Power.HALF) {
			setSecondaryPistonState(false);
		}
	}

	/**
	 * Used to determine whether or not we are using all or half of the pistons
	 * 
	 * @return Current power of the subsystem
	 */
	public Power getPower() {
		return power;
	}

	@Override
	public StateSubsystem.State getDisabledDefaultState() {
		return STATE_RETRACT;
	}

	@Override
	public StateSubsystem.State getTeleopDefaultState() {
		return STATE_RETRACT;
	}

	@Override
	public StateSubsystem.State getAutoDefaultState() {
		return STATE_RETRACT;
	}
}
