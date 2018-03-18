/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.Constants;
import org.usfirst.frc.team1153.robot.OI;
import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.RobotMap;
import org.usfirst.frc.team1153.robot.lib.RebelDriveHelper;
import org.usfirst.frc.team1153.robot.lib.DriveSignal;
import org.usfirst.frc.team1153.robot.lib.DummyOutput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoDrive extends Subsystem {
	protected WPI_TalonSRX leftMaster;
	protected WPI_TalonSRX leftBackSlave;
	protected WPI_TalonSRX leftFrontSlave;
	protected WPI_TalonSRX rightMaster;
	protected WPI_TalonSRX rightBackSlave;
	protected WPI_TalonSRX rightFrontSlave;

	private Servo ServoA;
	private Servo ServoB;

	private ADXRS450_Gyro gyro;

	private DoubleSolenoid transmissionShifter;

	PIDController gyroTwoPID;
	PIDController gyroOnePID;

	private DummyOutput gyroTwoOutput;
	private DummyOutput gyroOneOutput;

	private Solenoid newShifter;

	public enum Shifter {
		High, Low
	}

	RebelDriveHelper helper;

	public enum RobotID {
		PROTO, FINAL
	}

	public RobotID robotId;

	/**
	 * Assigns what the Robot should instantiate every time the Drive subsystem
	 * initializes.
	 */
	public AutoDrive() {
		leftMaster = new WPI_TalonSRX(RobotMap.LEFT_MASTER);
		leftBackSlave = new WPI_TalonSRX(RobotMap.LEFT_BACK_SLAVE);
		leftFrontSlave = new WPI_TalonSRX(RobotMap.LEFT_FRONT_SLAVE);
		rightMaster = new WPI_TalonSRX(RobotMap.RIGHT_MASTER);
		rightBackSlave = new WPI_TalonSRX(RobotMap.RIGHT_BACK_SLAVE);
		rightFrontSlave = new WPI_TalonSRX(RobotMap.RIGHT_FRONT_SLAVE);

		gyro = new ADXRS450_Gyro();
		double kTwoP = 0.0376;//0.02;
		double kTwoI = 0;//0;
		double kTwoD = 0.0039;//0.00376;//0.028;//0.015;
		
		double kOneP = 0.07;//.03
		double kOneI = 0;
		double kOneD = 0;

		gyroTwoOutput = new DummyOutput();
		gyroTwoPID = new PIDController(kTwoP, kTwoI, kTwoD, gyro, gyroTwoOutput);
		gyroTwoPID.setInputRange(-180, 180);
		gyroTwoPID.setContinuous();
		gyroTwoPID.setOutputRange(-0.6, 0.6);
		
		gyroOneOutput = new DummyOutput();
		gyroOnePID = new PIDController(kOneP, kOneI, kOneD, gyro, gyroOneOutput);
		gyroOnePID.setInputRange(-180, 180);
		gyroOnePID.setContinuous();
		gyroOnePID.setOutputRange(-0.6, 0.6);
		
		transmissionShifter = new DoubleSolenoid(RobotMap.TRANSMISSION_SOLENOID_A, RobotMap.TRANSMISSION_SOLENOID_B);

		newShifter = new Solenoid(11, 0);

		helper = new RebelDriveHelper();

		ServoA = new Servo(3);
		ServoB = new Servo(2);

		// robotDrive = new DifferentialDrive(leftMaster, rightMaster);

		robotId = RobotID.PROTO;

		configTalonOutput();
		setEncoderAsFeedback();
		setFollowers();
	}

	public void setServoValue(double value) {
		ServoA.setAngle(-value);
		ServoB.setAngle(value);
	}

	public double getServoAAngle() {
		return ServoA.getAngle();
	}

	public double getServoBAngle() {
		return ServoB.getAngle();
	}

	public void resetGyro() {
		gyro.reset();
	}

	public void setGyroTwoPID(double setpoint) {
		gyroTwoPID.setSetpoint(setpoint);
	}
	
	public void setGyroOnePID(double setpoint) {
		gyroOnePID.setSetpoint(setpoint);

	}
	
	public double getGyroOutput() {
		return gyroTwoOutput.getOutput();
	}

	public void runGyroTwoPID(boolean enabled) {
		if (enabled) {
			gyroTwoPID.enable();
			leftMaster.set(ControlMode.PercentOutput, gyroTwoOutput.getOutput());
			rightMaster.set(ControlMode.PercentOutput, gyroTwoOutput.getOutput());
		} else {
			gyroTwoPID.disable();
			DriveSignal autoDriveSignal = helper.rebelDrive(0, 0, false, false);
			Robot.autoDrive.driveWithHelper(ControlMode.PercentOutput, autoDriveSignal);
		}
	}
	
	public void runGyroOnePID(boolean enabled) {
		if (enabled) {
			gyroOnePID.enable();
			if (gyroOnePID.getError() >= 0) {
				leftMaster.set(ControlMode.PercentOutput, gyroOneOutput.getOutput());
			} else {
				rightMaster.set(ControlMode.PercentOutput, gyroOneOutput.getOutput());
			}
			
		} else {
			gyroOnePID.disable();
			DriveSignal autoDriveSignal = helper.rebelDrive(0, 0, false, false);
			Robot.autoDrive.driveWithHelper(ControlMode.PercentOutput, autoDriveSignal);
		}
	}

	public double gyroError() {
		return gyroTwoPID.getError();
	}

	public double getGyroAngle() {
		return gyro.getAngle();
	}

	public void calibrateGyro() {
		gyro.calibrate();
	}

	/**
	 * New shifter test Code
	 */

	public void shiftHighTest() {
		newShifter.set(true);
	}

	public void shiftLowTest() {
		newShifter.set(false);
	}

	@Override
	public void initDefaultCommand() {
	}

	/**
	 * Below is drive code which is used in the cheesy Drive Command
	 */
	public void configDrive(ControlMode controlMode, double left, double right) {
		System.out.println("left:" + left);
		System.out.println("right:" + right);
		leftMaster.set(controlMode, left);
		rightMaster.set(controlMode, -right);
	}

	/**
	 * Adjusting cheesy drive
	 * 
	 * @param value
	 * @param deadband
	 * @return
	 */
	protected double applyDeadband(double value, double deadband) {
		if (Math.abs(value) > deadband) {
			if (value > 0.0) {
				return (value - deadband) / (1.0 - deadband);
			} else {
				return (value + deadband) / (1.0 - deadband);
			}
		} else {
			return 0.0;
		}
	}

	public void createDriveSignal(boolean squaredInputs) {
		boolean quickTurn = Robot.autoDrive.quickTurnController();
		double rawMoveValue = Robot.oi.getDriverStick().getRawAxis(OI.JOYSTICK_LEFT_Y);
		double rawRotateValue = Robot.oi.getDriverStick().getRawAxis(OI.JOYSTICK_RIGHT_X);

		double moveValue = 0;
		double rotateValue = 0;
		if (squaredInputs == true) {
			double deadBandMoveValue = applyDeadband(rawMoveValue, 0.02);
			double deadBandRotateValue = applyDeadband(rawRotateValue, 0.02);
			moveValue = Math.copySign(deadBandMoveValue * deadBandMoveValue, deadBandMoveValue);
			rotateValue = Math.copySign(deadBandRotateValue * deadBandRotateValue, deadBandRotateValue);
		} else {
			rawMoveValue = moveValue;
			rotateValue = rawRotateValue;
		}

		DriveSignal driveSignal = helper.rebelDrive(-1 * moveValue, 0.8 * rotateValue, true, false);
		Robot.autoDrive.driveWithHelper(ControlMode.PercentOutput, driveSignal);

	}

	public void driveWithHelper(ControlMode controlMode, DriveSignal driveSignal) {
		this.configDrive(controlMode, driveSignal.getLeft(), driveSignal.getRight());
	}

	public boolean quickTurnController() {
		if (Robot.oi.getDriverStick().getRawAxis(OI.JOYSTICK_LEFT_Y) < 0.2
				&& Robot.oi.getDriverStick().getRawAxis(OI.JOYSTICK_LEFT_Y) > -0.2) {
			return true;
		} else {
			return false;
		}
	}

	public void cheesyDriveWithoutJoysticks(double move, double rotate) {
		double moveValue = move;
		double rotateValue = rotate;
		DriveSignal driveSignal = helper.rebelDrive(-1 * moveValue, rotateValue, false, false);
		Robot.autoDrive.driveWithHelper(ControlMode.PercentOutput, driveSignal);
	}

	public void configTalonOutput() {
		leftMaster.set(ControlMode.PercentOutput, 0);
		rightMaster.set(ControlMode.PercentOutput, 0);

		rightMaster.configNominalOutputForward(0, Constants.kTimeoutMs);
		rightMaster.configNominalOutputReverse(0, Constants.kTimeoutMs);
		rightMaster.configPeakOutputForward(1, Constants.kTimeoutMs);
		rightMaster.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		leftMaster.configNominalOutputForward(0, Constants.kTimeoutMs);
		leftMaster.configNominalOutputReverse(0, Constants.kTimeoutMs);
		leftMaster.configPeakOutputForward(1, Constants.kTimeoutMs);
		leftMaster.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		final int kPeakCurrentAmps = 0; /* threshold to trigger current limit */
		final int kPeakTimeMs = 0; /*
									 * how long after Peak current to trigger current limit
									 */
		final int kContinCurrentAmps = 0; /*
											 * hold current after limit is triggered
											 */

//		leftMaster.configPeakCurrentLimit(kPeakCurrentAmps, 10);
//		leftMaster.configPeakCurrentDuration(kPeakTimeMs, 50); /*
//																 * this is a necessary call to avoid errata.
//																 */
//		leftMaster.configContinuousCurrentLimit(kContinCurrentAmps, 10);
//		leftMaster.enableCurrentLimit(true); /* honor initial setting */

		leftMaster.setNeutralMode(NeutralMode.Coast);

//		rightMaster.configPeakCurrentLimit(kPeakCurrentAmps, 10);
//		rightMaster.configPeakCurrentDuration(kPeakTimeMs, 50); /*
//																 * this is a necessary call to avoid errata.
//																 */
//		rightMaster.configContinuousCurrentLimit(kContinCurrentAmps, 10);
//		rightMaster.enableCurrentLimit(true); /* honor initial setting */

		rightMaster.setNeutralMode(NeutralMode.Coast);

		 leftMaster.configOpenloopRamp(0, 10);
		 rightMaster.configOpenloopRamp(0, 10);

	}

	public void setFollowers() {
		leftFrontSlave.follow(leftMaster);
		leftBackSlave.follow(leftMaster);
		rightFrontSlave.follow(rightMaster);
		rightBackSlave.follow(rightMaster);
	}

	public void setEncoderAsFeedback() {
		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx,
				Constants.kTimeoutMs);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx,
				Constants.kTimeoutMs);

//		if (robotId == RobotID.PROTO) {
//			rightMaster.setSensorPhase(true);
//			leftMaster.setSensorPhase(true);
//		} else {
			rightMaster.setSensorPhase(true);
			leftMaster.setSensorPhase(true);
		//}
	}
	
	public void resetEncoders() {
		leftMaster.getSensorCollection().setQuadraturePosition(0, 10);
		rightMaster.getSensorCollection().setQuadraturePosition(0, 10);
	}

	/**
	 * Code for turning on and off the ball-shifter transmissions. This code also
	 * functions as a variable setter to allow us to autonomously control the stage
	 * that the transmissions are on.
	 */

	public void shiftHigh() {
		transmissionShifter.set(DoubleSolenoid.Value.kReverse);

	}

	public void shiftLow() {
		transmissionShifter.set(DoubleSolenoid.Value.kForward);

	}

	public Shifter getGear() {
		DoubleSolenoid.Value currentState = transmissionShifter.get();
		if (currentState == DoubleSolenoid.Value.kForward) {
			return Shifter.High;
		} else {
			return Shifter.Low;
		}
	}

	public double getRightMotorOutputPercent() {
		return rightMaster.getMotorOutputPercent();
	}

	public double getRightMotorBusVoltage() {
		return rightMaster.getBusVoltage();
	}

	public double getRightMotorSpeed() {
		return rightMaster.getSelectedSensorVelocity(0);
	}

	public void configRightMotionMagic(double rightCoefficient) {
		/* Set relevant frame periods to be at least as fast as periodic rate */
		rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		/* set the peak and nominal outputs */
		rightMaster.configNominalOutputForward(0, Constants.kTimeoutMs);
		rightMaster.configNominalOutputReverse(0, Constants.kTimeoutMs);
		rightMaster.configPeakOutputForward(1, Constants.kTimeoutMs);
		rightMaster.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		rightMaster.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		/* set acceleration and vcruise velocity - see documentation */
		int cruiseVelocity = (int) (rightCoefficient * 1800);
		System.out.println("Setting right cruiseVelocity to " + cruiseVelocity);
		rightMaster.configMotionCruiseVelocity(cruiseVelocity, Constants.kTimeoutMs);
		rightMaster.configMotionAcceleration(1800, Constants.kTimeoutMs);
		/* zero the sensor */
		rightMaster.getSensorCollection().setQuadraturePosition(0, 10);
	}

	public void configLeftMotionMagic(double leftCoefficient) {
		/* Set relevant frame periods to be at least as fast as periodic rate */
		leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		/* set the peak and nominal outputs */
		leftMaster.configNominalOutputForward(0, Constants.kTimeoutMs);
		leftMaster.configNominalOutputReverse(0, Constants.kTimeoutMs);
		leftMaster.configPeakOutputForward(1, Constants.kTimeoutMs);
		leftMaster.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		leftMaster.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		/* set acceleration and vcruise velocity - see documentation */
		int cruiseVelocity = (int) (leftCoefficient * 1800);
		System.out.println("Setting left cruiseVelocity to " + cruiseVelocity);
		leftMaster.configMotionCruiseVelocity(cruiseVelocity, Constants.kTimeoutMs);
		leftMaster.configMotionAcceleration(1800, Constants.kTimeoutMs);
		// 3400
		/* zero the sensor */
		leftMaster.getSensorCollection().setQuadraturePosition(0, 10);
	}

	public void enactRightMotorMotionMagic(double targetPos) {
		rightMaster.set(ControlMode.MotionMagic, targetPos);
	}

	public double getRightMotorClosedLoopError() {
		return rightMaster.getClosedLoopError(0);
	}

	public int getRightMotorSensorVelocity() {
		return rightMaster.getSelectedSensorVelocity(0);
	}

	public double getRightMotorSensorPosition() {
		return rightMaster.getSelectedSensorPosition(0);
	}

	public double getRightMotorActiveTrajectoryPosition() {
		return rightMaster.getActiveTrajectoryPosition();
	}

	public double getRightMotorActiveTrajectoryVelocity() {
		return rightMaster.getActiveTrajectoryVelocity();
	}

	public double getRightMotorActiveTrajectoryHeading() {
		return rightMaster.getActiveTrajectoryHeading();
	}

	public double getLeftMotorOutputPercent() {
		return leftMaster.getMotorOutputPercent();
	}

	public double getLeftMotorBusVoltage() {
		return leftMaster.getBusVoltage();
	}

	public double getLeftMotorSpeed() {
		return leftMaster.getSelectedSensorVelocity(0);
	}
	
	public double getLeftMotorCurrent() {
		return leftMaster.getOutputCurrent();
	}
	
	public double getRightMotorCurrent() {
		return rightMaster.getOutputCurrent();
	}
	
	public double getLeftMotorOutputVoltage() {
		return leftMaster.getMotorOutputVoltage();
	}
	
	public double getRightMotorOutputVoltage() {
		return rightMaster.getMotorOutputVoltage();
	}

	public void enactLeftMotorMotionMagic(double targetPos) {
		leftMaster.set(ControlMode.MotionMagic, targetPos);
	}

	public void stopMotionMagic() {
		leftMaster.set(ControlMode.PercentOutput, 0);
		rightMaster.set(ControlMode.PercentOutput, 0);
	}

	public double getLeftMotorClosedLoopError() {
		return leftMaster.getClosedLoopError(0);
	}

	public int getLeftMotorSensorVelocity() {
		return leftMaster.getSelectedSensorVelocity(0);
	}

	public double getLeftMotorSensorPosition() {
		return leftMaster.getSelectedSensorPosition(0);
	}

	public double getLeftMotorActiveTrajectoryPosition() {
		return leftMaster.getActiveTrajectoryPosition();
	}

	public double getLeftMotorActiveTrajectoryVelocity() {
		return leftMaster.getActiveTrajectoryVelocity();
	}

	public double getLeftMotorActiveTrajectoryHeading() {
		return leftMaster.getActiveTrajectoryHeading();
	}
}