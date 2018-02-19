package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.RobotMap;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Manages the up/down pistons for the Collector assembly
 */
public class ArmsVertical extends StateSubsystem {

	/**
	 * Collector arms are down
	 */
	public static final StateSubsystem.State STATE_DOWN = new StateSubsystem.State("down");

	/**
	 * Collector arms are up
	 */
	public static final StateSubsystem.State STATE_UP = new StateSubsystem.State("up");

	private DoubleSolenoid verticalSolenoid;

	public ArmsVertical() {
		verticalSolenoid = new DoubleSolenoid(RobotMap.ARM_VERTICAL_A, RobotMap.ARM_VERTICAL_B);

		registerState(STATE_DOWN);
		registerState(STATE_UP);
	}
	
	@Override
	protected void initDefaultCommand() {

	}

	public void downInit() {
		verticalSolenoid.set(Value.kReverse);
	}

	public void downPeriodic() {

	}

	public void upInit() {
		verticalSolenoid.set(Value.kForward);
	}

	public void upPeriodic() {

	}

	@Override
	protected State getDisabledDefaultState() {
		return STATE_UP;
	}

	@Override
	protected State getTeleopDefaultState() {
		return STATE_UP;
	}

	@Override
	protected State getAutoDefaultState() {
		return STATE_UP;
	}

}
