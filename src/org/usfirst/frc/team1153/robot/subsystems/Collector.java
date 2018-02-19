package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.Constants;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Manages the collect motors for the Collector assembly
 */
public class Collector extends StateSubsystem {

	/**
	 * Collector motors are running
	 */
	public static final StateSubsystem.State STATE_RUNNING = new StateSubsystem.State("running");

	/**
	 * Collector motors are not running
	 */
	public static final StateSubsystem.State STATE_IDLE = new StateSubsystem.State("idle");
	
	/**
	 * Collector motors are running in the reverse direction
	 */
	public static final StateSubsystem.State STATE_REVERSE = new StateSubsystem.State("reverse");

	private static final double COLLECT_MOTOR_POWER = 1;

	// TODO: Add motors for assembly actuation
	private WPI_TalonSRX motorA;
	private WPI_TalonSRX motorB;

	public Collector() {
		motorA = new WPI_TalonSRX(7);
		motorB = new WPI_TalonSRX(8);
		configCollectorMotorOutput();

		registerState(STATE_RUNNING);
		registerState(STATE_IDLE);
	}

	@Override
	public void initDefaultCommand() {
	}

	public void configCollectorMotorOutput() {
		motorA.configNominalOutputForward(0, Constants.kTimeoutMs);
		motorA.configNominalOutputReverse(0, Constants.kTimeoutMs);
		motorA.configPeakOutputForward(1, Constants.kTimeoutMs);
		motorA.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		motorB.configNominalOutputForward(0, Constants.kTimeoutMs);
		motorB.configNominalOutputReverse(0, Constants.kTimeoutMs);
		motorB.configPeakOutputForward(1, Constants.kTimeoutMs);
		motorB.configPeakOutputReverse(-1, Constants.kTimeoutMs);
	}

	public void setMotorPower(double in) {
		motorA.set(ControlMode.PercentOutput, in);
		motorB.set(ControlMode.PercentOutput, -in);
	}
	
	public void runningInit() {
		setMotorPower(COLLECT_MOTOR_POWER);
	}
	
	public void runningPeriodic() {
		
	}
	
	public void idleInit() {
		setMotorPower(0);
	}
	
	public void initPeriodic() {
		
	}
	
	public void reverseInit() {
		setMotorPower(-COLLECT_MOTOR_POWER);
	}
	
	public void reversePeriodic() {
		
	}

	@Override
	public StateSubsystem.State getDisabledDefaultState() {
		return STATE_IDLE;
	}

	@Override
	public StateSubsystem.State getTeleopDefaultState() {
		return STATE_IDLE;
	}

	@Override
	public StateSubsystem.State getAutoDefaultState() {
		return STATE_IDLE;
	}
}
