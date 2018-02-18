package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.lib.StateSubsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Carriage extends StateSubsystem {

	/**
	 * Carriage is in the down position with all pistons retracted
	 */
	public static final State STATE_DOWN = new StateSubsystem.State("down");

	/**
	 * Carriage is in the up position with all pistons extended
	 */
	public static final State STATE_UP = new StateSubsystem.State("up");

	private DoubleSolenoid articulator;

	public Carriage() {
		articulator = new DoubleSolenoid(2, 3);

		registerState(STATE_UP);
		registerState(STATE_DOWN);
	}

	@Override
	public void initDefaultCommand() {
	}

	public void setArticulatorPistonState(boolean state) {
		// articulator.set(Value.kForward);
		if (state == true) {
			articulator.set(Value.kForward);
		} else {
			articulator.set(Value.kReverse);
		}
	}

	/**
	 * Called when shooter first begins fire state
	 */
	public void upInit() {
		setArticulatorPistonState(true);
	}

	/**
	 * Called when shooter first begins retract state
	 */
	public void downInit() {
		setArticulatorPistonState(false);
	}

	/**
	 * Called when shooter first begins fire state
	 */
	public void upPeriodic() {

	}

	/**
	 * Called when shooter first begins retract state
	 */
	public void downPeriodic() {

	}

	@Override
	public StateSubsystem.State getDisabledDefaultState() {
		return STATE_UP;
	}

	@Override
	public StateSubsystem.State getTeleopDefaultState() {
		return STATE_UP;
	}

	@Override
	public StateSubsystem.State getAutoDefaultState() {
		return STATE_UP;
	}
}
