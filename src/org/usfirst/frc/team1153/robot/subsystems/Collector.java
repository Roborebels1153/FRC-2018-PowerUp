package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.RobotMap;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Manages all hardware for the Collector assembly
 */
public class Collector extends StateSubsystem {

	/**
	 * Collector is extended, and the motors are running
	 */
	public static final StateSubsystem.State STATE_COLLECTING = new StateSubsystem.State("collecting");
	
	/**
	 * Collector is extended, but motors are not running
	 */
	public static final StateSubsystem.State STATE_COLLECTED = new StateSubsystem.State("collected");
	
	/**
	 * Collector is fully retracted and disabled
	 */
	public static final StateSubsystem.State STATE_RETRACT = new StateSubsystem.State("retract");
	
	/**
	 * Collector is extended, and the motors are running in reverse
	 */
	public static final StateSubsystem.State STATE_REVERSE = new StateSubsystem.State("reverse");
	
	/**
	 * Collector is extended, but the motors are not running
	 */
	public static final StateSubsystem.State STATE_EXTEND = new StateSubsystem.State("extend");
	
	private static final double COLLECT_MOTOR_POWER = 0.8;

	// TODO: Add motors for assembly actuation
	private WPI_TalonSRX motorA;
	private WPI_TalonSRX motorB;

	public Collector() {
		motorA = new WPI_TalonSRX(RobotMap.COLLECT_MOTOR_A);
		motorB = new WPI_TalonSRX(RobotMap.COLLECT_MOTOR_B);
		
		registerState(STATE_COLLECTING);
		registerState(STATE_COLLECTED);
		registerState(STATE_RETRACT);
		registerState(STATE_REVERSE);
		registerState(STATE_EXTEND);
	}

	@Override
	public void initDefaultCommand() {
	}
	
	private void setMotorPower(double in) {
		motorA.set(in);
		motorB.set(-in);
	}

	public void collectingInit() {
		setMotorPower(COLLECT_MOTOR_POWER);
	}
	
	public void collectedInit() {
		setMotorPower(0);
	}
	
	public void retractInit() {
		setMotorPower(0);
	}
	
	public void reverseInit() {
		setMotorPower(-COLLECT_MOTOR_POWER);
	}
	
	public void extendInit() {
		setMotorPower(0);
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
