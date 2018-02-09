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
	public static final int STATE_FIRE = 0;
	/**
	 * Shooter is in the retracted position with all pistons
	 * retracted
	 */
	public static final int STATE_RETRACT = 1;

	private Solenoid shooterA;
	private Solenoid shooterB;
	private Solenoid shooterC;
	private Solenoid shooterD;

	public Shooter() {
		shooterA = new Solenoid(RobotMap.SHOOTER_SOLENOID_A);
		shooterB = new Solenoid(RobotMap.SHOOTER_SOLENOID_B);
		shooterC = new Solenoid(RobotMap.SHOOTER_SOLENOID_C);
		shooterD = new Solenoid(RobotMap.SHOOTER_SOLENOID_D);
	}

	@Override
	public void run() {
		switch (state) {
		case STATE_FIRE:
			if (!isInit) {
				fireInit();
				isInit = true;
			} else {
				firePeriodic();
			}
			break;
		case STATE_RETRACT:
			if (!isInit) {
				retractInit();
				isInit = true;
			} else {
				retractPeriodic();
			}
			break;
		}
	}

	@Override
	public void initDefaultCommand() {
	}
	
	/**
	 * Simultaneously sets the state of all pistons
	 */
	private void setAllPistonState(boolean state) {
		shooterA.set(state);
		shooterB.set(state);
		shooterC.set(state);
		shooterD.set(state);
	}

	/**
	 * Called when shooter first begins fire state
	 */
	private void fireInit() {
		setAllPistonState(true);
	}
	
	/**
	 * Called periodically while shooter is in fire state
	 */
	private void firePeriodic() {
		
	}

	
	/**
	 * Called when shooter first begins retract state
	 */
	private void retractInit() {
		setAllPistonState(false);
	}
	
	/**
	 * Called periodically while shooter is in retract state
	 */
	private void retractPeriodic() {
		
	}

	
	@Override
	public int getDisabledDefaultState() {
		return STATE_RETRACT;
	}
	

	@Override
	public int getTeleopDefaultState() {
		return STATE_RETRACT;
	}
	

	@Override
	public int getAutoDefaultState() {
		return STATE_RETRACT;
	}
}
