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
import org.usfirst.frc.team1153.robot.lib.CheesyDriveHelper;
import org.usfirst.frc.team1153.robot.lib.DriveSignal;
import org.usfirst.frc.team1153.robot.lib.DummyOutput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoDrive extends Subsystem {
	protected WPI_TalonSRX leftMaster;
	protected WPI_TalonSRX leftBackSlave;
	protected WPI_TalonSRX leftFrontSlave;
	protected WPI_TalonSRX rightMaster;
	protected WPI_TalonSRX rightBackSlave;
	protected WPI_TalonSRX rightFrontSlave;

	private ADXRS450_Gyro gyro;

	/*
	 * Transmission Shifting Related
	 */
	private DoubleSolenoid transmissionShifter;

	PIDController gyroPID;

	private DummyOutput gyroOutput;

	private Solenoid newShifter;

	public enum Shifter {
		High, Low
	}

	private boolean turboMode = false;

	private double turnValue;

	// DifferentialDrive drive;

	CheesyDriveHelper helper;

	/**
	 * Assigns what the Robot should instantiate every time the Drive subsystem
	 * initializes.
	 */
	public AutoDrive() {
		leftMaster = new WPI_TalonSRX(1);
		leftBackSlave = new WPI_TalonSRX(2);
		leftFrontSlave = new WPI_TalonSRX(3);
		rightMaster = new WPI_TalonSRX(4);
		rightBackSlave = new WPI_TalonSRX(6);
		rightFrontSlave = new WPI_TalonSRX(5);

		gyro = new ADXRS450_Gyro();
		double kP = 0.016;
		double kI = 0.00001;
		double kD = 0.045;

		gyroOutput = new DummyOutput();

		gyroPID = new PIDController(kP, kI, kD, gyro, gyroOutput);

		gyroPID.setInputRange(-180, 180);

		gyroPID.setContinuous();

		gyroPID.setOutputRange(-0.6, 0.6);

		transmissionShifter = new DoubleSolenoid(RobotMap.TRANSMISSION_SOLENOID_LEFT_A,
				RobotMap.TRANSMISSION_SOLENOID_LEFT_B);

		newShifter = new Solenoid(11, 0);

		helper = new CheesyDriveHelper();

		configTalonOutput();
		setEncoderAsFeedback();
		setFollowers();
	}

	public void resetGyro() {
		gyro.reset();
	}

	public void setGyroPID(double setpoint) {
		gyroPID.setSetpoint(gyro.getAngle() + setpoint);
	}

	public double getGyroOutput() {
		return gyroOutput.getOutput();
	}

	public void runGyroPID(boolean enabled) {
		if (enabled) {
			gyroPID.enable();
			leftMaster.set(gyroOutput.getOutput());
			rightMaster.set(gyroOutput.getOutput());
		} else {
			gyroPID.disable();
			DriveSignal autoDriveSignal = helper.cheesyDrive(0, 0, false, false);
			Robot.autoDrive.driveWithHelper(ControlMode.PercentOutput, autoDriveSignal);
		}
	}

	public boolean gyroWithinTolerance() {
		return (Math.abs(gyroPID.getError()) < 1);
	}

	public double gyroError() {
		return gyroPID.getError();
	}

	public double getGyroAngle() {
		return gyro.getAngle();
	}

	public void calibrateGyro() {
		gyro.calibrate();
	}

	// public void initializeDiffDrive() {
	// drive = new DifferentialDrive(leftMaster, rightMaster);
	// }
	//
	// public void removeDiffDrive() {
	// drive = null;
	// }

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

	public void turnWithLimelight(double motorValue) {
		leftMaster.set(motorValue);
		rightMaster.set(motorValue);
	}

	public void driveForward() {
		configTalonOutput();
		leftMaster.set(ControlMode.PercentOutput, -1);
		rightMaster.set(ControlMode.PercentOutput, 1);
		// rightBackSlave.set(ControlMode.PercentOutput, -.8);
		// rightFrontSlave.set(ControlMode.PercentOutput, -.8);

	}

	public void driveBackward() {
		configTalonOutput();
		leftMaster.set(ControlMode.PercentOutput, 1);
		rightMaster.set(ControlMode.PercentOutput, -1);
	}

	public void stop() {
		configTalonOutput();
		leftMaster.set(ControlMode.PercentOutput, 0);
		rightMaster.set(ControlMode.PercentOutput, 0);
		// rightBackSlave.set(ControlMode.PercentOutput, 0);
		// rightFrontSlave.set(ControlMode.PercentOutput, 0);
	}

	/**
	 * Below is drive code which is used in the cheesy Drive Command
	 */
	public void configDrive(ControlMode controlMode, double left, double right) {
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

		DriveSignal driveSignal = helper.cheesyDrive(-1 * moveValue, 0.7 * rotateValue, quickTurn, false);
		Robot.autoDrive.driveWithHelper(ControlMode.PercentOutput, driveSignal);

	}

	public void driveWithHelper(ControlMode controlMode, DriveSignal driveSignal) {
		// System.out.println(driveSignal.toString());

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
		DriveSignal driveSignal = helper.cheesyDrive(-1 * moveValue, rotateValue, false, false);
		Robot.autoDrive.driveWithHelper(ControlMode.PercentOutput, driveSignal);
	}

	// public void drive(Joystick joystick) {
	// drive.arcadeDrive(-1 * joystick.getY(), 1 * joystick.getRawAxis(4));
	// }

	public void configTalonOutput() {
		rightMaster.configNominalOutputForward(0, Constants.kTimeoutMs);
		rightMaster.configNominalOutputReverse(0, Constants.kTimeoutMs);
		rightMaster.configPeakOutputForward(1, Constants.kTimeoutMs);
		rightMaster.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		leftMaster.configNominalOutputForward(0, Constants.kTimeoutMs);
		leftMaster.configNominalOutputReverse(0, Constants.kTimeoutMs);
		leftMaster.configPeakOutputForward(1, Constants.kTimeoutMs);
		leftMaster.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		
		leftMaster.setNeutralMode(NeutralMode.Brake);
		rightMaster.setNeutralMode(NeutralMode.Brake);


		// rightMaster.enableCurrentLimit(true);
		// rightMaster.configPeakCurrentLimit(20, Constants.kTimeoutMs);
		//
		// leftMaster.enableCurrentLimit(true);
		// leftMaster.configPeakCurrentLimit(20, Constants.kTimeoutMs);
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

		// rightMaster.setSensorPhase(true);
		// leftMaster.setSensorPhase(true);

		// rightMaster.setInverted(true);
		// leftMaster.setInverted(true);

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
	public void turboOn() {
		turboMode = true;
	}

	public void turboOff() {
		turboMode = false;
	}

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

	public void configRightMotionMagic() {
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
		rightMaster.configMotionCruiseVelocity(1500, Constants.kTimeoutMs);
		rightMaster.configMotionAcceleration(2000, Constants.kTimeoutMs);
		/* zero the sensor */
		rightMaster.getSensorCollection().setQuadraturePosition(0, 10);
	}

	public void configLeftMotionMagic() {
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
		leftMaster.configMotionCruiseVelocity(1500, Constants.kTimeoutMs);
		leftMaster.configMotionAcceleration(2000, Constants.kTimeoutMs);
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