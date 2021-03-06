package org.usfirst.frc.team1153.robot.lib;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class RebelDrive extends DifferentialDrive {

	public RebelDrive(SpeedController left, SpeedController right) {
		super(left, right);
	}
	
	public static RebelDrive getInstance(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor,
			SpeedController rearRightMotor) {
		SpeedControllerGroup groupLeft = new SpeedControllerGroup(frontLeftMotor, rearLeftMotor);
		SpeedControllerGroup groupRight = new SpeedControllerGroup(frontRightMotor, rearRightMotor);
		
		return new RebelDrive(groupLeft, groupRight);
	}
	
	public void arcadeDriveTurbo(double drive, double turn, boolean turbo) {
		double moveValue = (turbo ? 1 : 0.8) * drive;
		double rotateValue = 1.0 * turn;
		arcadeDrive(moveValue, rotateValue, true);
	}
}
