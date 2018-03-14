package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.RobotMap;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
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
	
	private DigitalInput cubeLimitSwitch;

	public Carriage() {
		articulator = new DoubleSolenoid(RobotMap.SHOOTER_ARTICULATOR_A, RobotMap.SHOOTER_ARTICULATOR_B);

		cubeLimitSwitch = new DigitalInput(RobotMap.CUBE_LIGHT_SENSOR);
		
		registerState(STATE_UP);
		registerState(STATE_DOWN);
	}

	@Override
	public void initDefaultCommand() {
	}

	public void setArticulatorPistonState(boolean state) {
		// articulator.set(Value.kForward);
		if (state == true) {
			articulator.set(Value.kReverse);
		} else {
			articulator.set(Value.kForward);
		}
	}
	
	public boolean getCubeLimitSwitchState() {
		return cubeLimitSwitch.get();
	}
	
	public Value getSolenoidState() {
		Value value  = articulator.get();
		System.out.println(value);
		return value;
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
		//System.out.println("Collector up periodic");
	}

	/**
	 * Called when shooter first begins retract state
	 */
	public void downPeriodic() {
		//System.out.println("Collector down periodic");
	}

	@Override
	public StateSubsystem.State getTeleopDefaultState() {
		State currState = Robot.carriage.getState();
		return currState;
	}

	@Override
	public StateSubsystem.State getAutoDefaultState() {
		return STATE_UP;
	}
}
