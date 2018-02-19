package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.Constants;
import org.usfirst.frc.team1153.robot.RobotMap;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem;
import org.usfirst.frc.team1153.robot.subsystems.Shooter.Power;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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

	private static final double COLLECT_MOTOR_POWER = 1;

	// TODO: Add motors for assembly actuation
	private WPI_TalonSRX motorA;
	private WPI_TalonSRX motorB;

	private DoubleSolenoid collectorUpDown;

	private DoubleSolenoid collectorLeftRight;
	
	public enum LeftRight {
		IN, OUT
	}
	
	public enum UpDown {
		UP, DOWN
	}
	
	private LeftRight leftRightState = LeftRight.OUT;
	
	private UpDown upDownState = UpDown.UP;

	public Collector() {
		motorA = new WPI_TalonSRX(7);
		motorB = new WPI_TalonSRX(8);

		// TODO: update mappings!!
		collectorUpDown = new DoubleSolenoid(4, 5);
		//
		collectorLeftRight = new DoubleSolenoid(6, 7);
		// collectorLeftRightB = new DoubleSolenoid(0, 0);

		registerState(STATE_COLLECTING);
		registerState(STATE_COLLECTED);
		registerState(STATE_RETRACT);
		registerState(STATE_REVERSE);
		registerState(STATE_EXTEND);

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
	
	public LeftRight toggleleftRightPistons() {
		if (LeftRight.IN.equals(leftRightState)) {
			leftRightState = LeftRight.OUT;
		} else {
			leftRightState = LeftRight.IN;
		}
		
		return leftRightState;
	}
	
	public UpDown toggleUpDownPistons() {
		if (UpDown.DOWN.equals(upDownState)) {
			upDownState = UpDown.UP;
		} else {
			upDownState = UpDown.DOWN;
		}
		
		return upDownState;
	}

	/**
	 * Collector Piston Shtuff. TODO: Brigham can change this to accodomate the
	 * states
	 */
	public void upDownPistonsIn() {
		collectorUpDown.set(Value.kReverse);
	}

	public void upDownPistonOut() {
		collectorUpDown.set(Value.kForward);
	}

	public void leftRightPistonsIn() {
		collectorLeftRight.set(Value.kForward);
	}

	public void leftRightPistonOut() {
		collectorLeftRight.set(Value.kReverse);
	}
	
	public double getMotorOutput(){
		return motorA.get();
	}

	public void setMotorPower(double in) {
		configCollectorMotorOutput();
		motorA.set(ControlMode.PercentOutput, in);
		motorB.set(ControlMode.PercentOutput, -in);
	}

	public void collectingInit() {
		configCollectorMotorOutput();
		setMotorPower(COLLECT_MOTOR_POWER);
	}

	public void reverseCollecting() {
		configCollectorMotorOutput();
		setMotorPower(-1 * COLLECT_MOTOR_POWER);
	}

	public void collectedInit() {
		setMotorPower(0);
	}

	public void retractInit() {
		configCollectorMotorOutput();
		setMotorPower(0);
	}

	public void reverseInit() {
		setMotorPower(-COLLECT_MOTOR_POWER);
	}

	public void extendInit() {
		setMotorPower(0);
	}

	public void collectingPeriodic() {

	}

	public void collectedPeriodic() {

	}

	public void retractPeriodic() {

	}

	public void reversePeriodic() {

	}

	public void extendPeriodic() {

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
