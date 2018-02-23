/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1153.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	
	
	public static final int LEFT_MASTER = 1;
	public static final int LEFT_BACK_SLAVE = 2;
	public static final int LEFT_FRONT_SLAVE = 3;
	public static final int RIGHT_MASTER = 4;
	public static final int RIGHT_BACK_SLAVE = 5;
	public static final int RIGHT_FRONT_SLAVE = 6;
	
	public static final int COLLECT_MOTOR_A = 7;
	public static final int COLLECT_MOTOR_B = 8;
	
	public static final int TRANSMISSION_SOLENOID_A = 0;
	public static final int TRANSMISSION_SOLENOID_B = 1;
	
	public static final int SHOOTER_SOLENOID_A = 2;
	public static final int SHOOTER_SOLENOID_B = 3;

	public static final int SHOOTER_ARTICULATOR_A = 2;
	public static final int SHOOTER_ARTICULATOR_B = 3;

	
	public static final int ARM_HORIZONTAL_A = 6;
	public static final int ARM_HORIZONTAL_B = 7;
	
	public static final int ARM_VERTICAL_A = 4;
	public static final int ARM_VERTICAL_B = 5;
	
	public static final int CLIMBER_PISTON_A = 0;
	public static final int CLIMBER_PISTON_B = 1;

	
	public static final int RIGHT_LIMIT_SWITCH = 3;
	public static final int LEFT_LIMIT_SWITCH = 2;
	
	public static final int TWENTY_FOUR_VOLT_PCM = 11;
	
	public static final int THIRD_PCM = 12;
	


}
