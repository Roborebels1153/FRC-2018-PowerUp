package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.OI;
import org.usfirst.frc.team1153.robot.RobotMap;
import org.usfirst.frc.team1153.robot.lib.RebelDrive;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {

	private RebelDrive robotDrive;

	private SpeedController leftFront;
	private SpeedController leftBack;
	private SpeedController rightFront;
	private SpeedController rightBack;

	private DoubleSolenoid transmission;

	private boolean turboMode = false;

	public enum Shifter {
		High, Low
	}

	public Drive() {
		leftFront = new Victor(RobotMap.LEFT_FRONT_MOTOR);
		leftBack = new Victor(RobotMap.LEFT_BACK_MOTOR);
		rightFront = new Victor(RobotMap.RIGHT_FRONT_MOTOR);
		rightBack = new Victor(RobotMap.RIGHT_BACK_MOTOR);

		transmission = new DoubleSolenoid(RobotMap.TRANSMISSION_SOLENOID_A, RobotMap.TRANSMISSION_SOLENOID_B);

		robotDrive = RebelDrive.getInstance(leftFront, leftBack, rightFront, rightBack);
	}

	@Override
	public void initDefaultCommand() {
	}

	public void drive(Joystick joystick) {
		double moveValue = joystick.getRawAxis(OI.JOYSTICK_LEFT_Y);
		double rotateValue = joystick.getRawAxis(OI.JOYSTICK_RIGHT_X);
		robotDrive.arcadeDriveTurbo(moveValue, rotateValue, turboMode);
	}

	public void turboOn() {
		turboMode = true;
	}

	public void turboOff() {
		turboMode = false;
	}

	public void shiftHigh() {
		transmission.set(DoubleSolenoid.Value.kForward);
	}

	public void shiftLow() {
		transmission.set(DoubleSolenoid.Value.kReverse);
	}
	
	public Shifter getGear() {
		DoubleSolenoid.Value currentState = transmission.get();
		if (currentState == DoubleSolenoid.Value.kForward) {
			return Shifter.High;
		} else {
			return Shifter.Low;
		}
	}

}