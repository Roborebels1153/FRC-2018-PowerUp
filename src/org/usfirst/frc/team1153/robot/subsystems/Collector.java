package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.Constants;
import org.usfirst.frc.team1153.robot.RobotMap;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
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

	private static final double COLLECT_MOTOR_RPM = 14000;

	// TODO: Add motors for assembly actuation
	private WPI_TalonSRX motorA;
	private WPI_TalonSRX motorB;

	public Collector() {
		motorA = new WPI_TalonSRX(RobotMap.COLLECT_MOTOR_A);
		motorB = new WPI_TalonSRX(RobotMap.COLLECT_MOTOR_B);
		configCollectorMotorOutput();

		registerState(STATE_RUNNING);
		registerState(STATE_IDLE);
		registerState(STATE_REVERSE);


		setMotorFeedBack();
	}

	@Override
	public void initDefaultCommand() {
	}

	public void configCollectorMotorOutput() {
		motorA.configNominalOutputForward(0, Constants.kTimeoutMs);
		motorA.configNominalOutputReverse(0, Constants.kTimeoutMs);
		motorA.configPeakOutputForward(1, Constants.kTimeoutMs);
		motorA.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		motorA.setInverted(true);

		motorB.configNominalOutputForward(0, Constants.kTimeoutMs);
		motorB.configNominalOutputReverse(0, Constants.kTimeoutMs);
		motorB.configPeakOutputForward(1, Constants.kTimeoutMs);
		motorB.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		motorB.setInverted(true);
	}

	
	public void setMotorVelocity(double in) {
//		motorA.set(ControlMode.PercentOutput, in);
//		motorB.set(ControlMode.PercentOutput, -in);
		
		configCollectorMotorOutput();
		motorA.config_kF(Constants.kPIDLoopIdx, 0.09307, Constants.kTimeoutMs);
		//motorA.config_kP(Constants.kPIDLoopIdx, 0.038, Constants.kTimeoutMs);
		//motorA.config_kP(Constants.kPIDLoopIdx, 0.0038, Constants.kTimeoutMs);
		motorA.config_kP(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
		motorA.config_kI(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
		motorA.config_kD(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
		
		motorB.config_kF(Constants.kPIDLoopIdx, 0.09307, Constants.kTimeoutMs);
		//motorB.config_kP(Constants.kPIDLoopIdx, 0.038, Constants.kTimeoutMs);
		//motorB.config_kP(Constants.kPIDLoopIdx, 0.0038, Constants.kTimeoutMs);
		motorB.config_kP(Constants.kPIDLoopIdx, 0.0, Constants.kTimeoutMs);
		motorB.config_kI(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
		motorB.config_kD(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
		
		motorA.set(ControlMode.Velocity, in);
		motorB.set(ControlMode.Velocity, -in);

	}

	public void setMotorFeedBack() {
		motorA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kSlotIdx,
				Constants.kTimeoutMs);
		motorB.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kSlotIdx,
				Constants.kTimeoutMs);

	}

	public void resetEncoder() {
		motorA.getSensorCollection().setQuadraturePosition(0, 10);
		motorB.getSensorCollection().setQuadraturePosition(0, 10);
	}

	
	
	public void setMotorPower(double value) {
		configCollectorMotorOutput();
		System.out.println("stop motors");
		motorA.set(ControlMode.PercentOutput, value);
		motorB.set(ControlMode.PercentOutput, -value);
	}
	
	public int getMotorASensorVelocity() {
		return motorA.getSelectedSensorVelocity(0);
	}

	public double getMotorASensorPosition() {
		return motorA.getSelectedSensorPosition(0);
	}

	public double getMotorAActiveTrajectoryPosition() {
		return motorA.getActiveTrajectoryPosition();
	}

	public double getMotorAActiveTrajectoryVelocity() {
		return motorA.getActiveTrajectoryVelocity();
	}
	
	public int getMotorBSensorVelocity() {
		return motorB.getSelectedSensorVelocity(0);
	}

	public double getMotorBSensorPosition() {
		return motorB.getSelectedSensorPosition(0);
	}

	public double getMotorBActiveTrajectoryPosition() {
		return motorB.getActiveTrajectoryPosition();
	}

	public double getMotorBActiveTrajectoryVelocity() {
		return motorB.getActiveTrajectoryVelocity();
	}

	public void runningInit() {
		setMotorVelocity(COLLECT_MOTOR_RPM);
		//setMotorPower(1);
	}

	public void runningPeriodic() {

	}

	public void idleInit() {
		setMotorVelocity(0);
	}

	public void idlePeriodic() {

	}

	public void reverseInit() {
		setMotorPower(-1);
	}

	public void reversePeriodic() {

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
