package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.OI;
import org.usfirst.frc.team1153.robot.RobotMap;
import org.usfirst.frc.team1153.robot.lib.RebelDrive;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {

	/*
	 * Drive Talons Related
	 */
	private int rEncoderOffset = 0;
	private int lEncoderOffset = 0;

	private RebelDrive robotDrive;

	private WPI_TalonSRX leftFront;
	private WPI_TalonSRX leftBack;
	private WPI_TalonSRX rightFront;
	private WPI_TalonSRX rightBack;
	private WPI_TalonSRX leftFrontSlave;
	private WPI_TalonSRX rightFrontSlave;

	/*
	 * Transmission Shifting Related
	 */
	private DoubleSolenoid transmissionLeft;
	private DoubleSolenoid transmissionRight;

	public enum Shifter {
		High, Low
	}

	private boolean turboMode = false;

	/**
	 * Assigns what the Robot should instantiate every time the Drive subsystem
	 * initializes.
	 */
	public Drive() {
		leftFront = new WPI_TalonSRX(RobotMap.LEFT_FRONT_MOTOR);
		leftBack = new WPI_TalonSRX(RobotMap.LEFT_BACK_MOTOR);
		rightFront = new WPI_TalonSRX(RobotMap.RIGHT_FRONT_MOTOR);
		rightBack = new WPI_TalonSRX(RobotMap.RIGHT_BACK_MOTOR);
		rightFrontSlave = new WPI_TalonSRX(RobotMap.RIGHT_FRONT_MOTOR_SLAVE);
		leftFrontSlave = new WPI_TalonSRX(RobotMap.LEFT_FRONT_MOTOR_SLAVE);

		transmissionLeft = new DoubleSolenoid(RobotMap.TRANSMISSION_SOLENOID_LEFT_A,
				RobotMap.TRANSMISSION_SOLENOID_LEFT_B);
		transmissionRight = new DoubleSolenoid(RobotMap.TRANSMISSION_SOLENOID_RIGHT_A,
				RobotMap.TRANSMISSION_SOLENOID_RIGHT_B);

		robotDrive = RebelDrive.getInstance(leftFront, leftBack, rightFront, rightBack);
		
		setEncoderAsFeedback();

	}

	@Override
	public void initDefaultCommand() {
	}

	/**
	 * Code to set how our team controls the Joystick. Sets a move value and a
	 * rotate value that can be assigned to differnt Joystick inputs.
	 * 
	 * @param joystick
	 */
	public void drive(Joystick joystick) {
		double moveValue = -joystick.getRawAxis(OI.JOYSTICK_LEFT_Y);
		double rotateValue = joystick.getRawAxis(OI.JOYSTICK_RIGHT_X);
		robotDrive.arcadeDriveTurbo(moveValue, rotateValue, true);
	}

	/**
	 * Sets Talons 3 and 6 to the servitude of
	 */
	public void setIndenturedServants() {
		rightFrontSlave.follow(rightFront);
		leftFrontSlave.follow(leftBack);
	}
	
	public void setEncoderAsFeedback() {
		leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		//reverseSensor() for the left talon
		
		leftFront.setSensorPhase(true);
	//	leftFront.setInverted(false);

	}
	
	private int getRawTalonEncoderOutput(boolean right) {
		if (right) {
			int encoderPosition = rightFront.getSensorCollection().getQuadraturePosition();
			return encoderPosition;
		} else {
			int encoderPosition = leftFront.getSensorCollection().getQuadraturePosition();
			return encoderPosition;
		}
	}
	
	public void resetEncoders() {
//		rEncoderOffset = getRawTalonEncoderOutput(true);
//		lEncoderOffset = getRawTalonEncoderOutput(false);
		leftFront.getSensorCollection().setQuadraturePosition(0, 10);
		rightFront.getSensorCollection().setQuadraturePosition(0, 10);
	}
	
	public int getTalonEncoderOutput(boolean right) {
		if (right) {
			return (getRawTalonEncoderOutput(right) - rEncoderOffset);
		} else {
			return (getRawTalonEncoderOutput(right) - lEncoderOffset);
		}
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
		transmissionLeft.set(DoubleSolenoid.Value.kForward);
		transmissionRight.set(DoubleSolenoid.Value.kForward);

	}

	public void shiftLow() {
		transmissionLeft.set(DoubleSolenoid.Value.kReverse);
		transmissionRight.set(DoubleSolenoid.Value.kReverse);

	}

	public Shifter getGear() {
		DoubleSolenoid.Value currentState = transmissionLeft.get();
		if (currentState == DoubleSolenoid.Value.kForward) {
			return Shifter.High;
		} else {
			return Shifter.Low;
		}
	}

	public void testMotor(double speed) {
		rightFrontSlave.set(speed);
	}

}