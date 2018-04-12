/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1153.robot;

import org.usfirst.frc.team1153.robot.commandGroups.CollectorDownAction;
import org.usfirst.frc.team1153.robot.commandGroups.CollectorUpAction;
import org.usfirst.frc.team1153.robot.commands.CarriageToggleCommand;
import org.usfirst.frc.team1153.robot.commands.ClimberToggleCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightOutCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightToggleCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorOffCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorOnCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorReverseCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorUpDownToggleCommand;
import org.usfirst.frc.team1153.robot.commands.FireShooterCommand;
import org.usfirst.frc.team1153.robot.commands.PTOToggleCommand;
import org.usfirst.frc.team1153.robot.commands.RetractShooterCommand;
import org.usfirst.frc.team1153.robot.commands.ServoToggleCommand;
import org.usfirst.frc.team1153.robot.commands.ShiftHighCommand;
import org.usfirst.frc.team1153.robot.commands.ShiftLowCommand;
import org.usfirst.frc.team1153.robot.commands.ShiftToggleCommand;
import org.usfirst.frc.team1153.robot.lib.RebelTrigger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private static final int DRIVER_JOYSTICK = 0;
	private static final int OPERATOR_STICK = 1;

	public static final int JOYSTICK_LEFT_Y = 1;
	public static final int JOYSTICK_RIGHT_X = 4;

	private Joystick opStick = new Joystick(OPERATOR_STICK);
	private Joystick driverStick = new Joystick(DRIVER_JOYSTICK);

	public Button drTriggerL = new RebelTrigger(driverStick, 2);
	public Button drTriggerR = new RebelTrigger(driverStick, 3);

	public Button drButtonY = new JoystickButton(driverStick, 4);
	public Button drButtonA = new JoystickButton(driverStick, 1);
	public Button drButtonB = new JoystickButton(driverStick, 2);
	public Button drButtonX = new JoystickButton(driverStick, 3);

	public Button drBumperL = new JoystickButton(driverStick, 5);
	public Button drBumperR = new JoystickButton(driverStick, 6);

	public Button opTriggerL = new RebelTrigger(opStick, 2);
	public Button opTriggerR = new RebelTrigger(opStick, 3);

	public Button opButtonY = new JoystickButton(opStick, 4);
	public Button opButtonA = new JoystickButton(opStick, 1);
	public Button opButtonB = new JoystickButton(opStick, 2);
	public Button opButtonX = new JoystickButton(opStick, 3);

	public Button opBumperL = new JoystickButton(opStick, 5);
	public Button opBumperR = new JoystickButton(opStick, 6);

	public OI() {

//		drTriggerL.whenPressed(new ShiftLowCommand());
//		drTriggerL.whenReleased(new ShiftHighCommand());
		
		drTriggerR.whenPressed(new ShiftToggleCommand());

		drButtonY.whenPressed(new PTOToggleCommand());

		drButtonA.whenPressed(new ClimberToggleCommand());
		
		drButtonB.whenPressed(new ServoToggleCommand());

	
		opButtonA.whenPressed(new FireShooterCommand());
		opButtonA.whenReleased(new RetractShooterCommand());
		
		opButtonB.whenPressed(new CarriageToggleCommand());

		opButtonY.whileHeld(new CollectorOnCommand());
		opButtonY.whenReleased(new CollectorOffCommand());

		opButtonX.whileHeld(new CollectorReverseCommand());
		opButtonX.whenReleased(new CollectorOffCommand());

		opTriggerR.whenPressed(new CollectorLeftRightToggleCommand());

		opTriggerL.whenPressed(new CollectorUpDownToggleCommand());

		opBumperR.whenPressed(new CollectorDownAction());
		opBumperR.whenReleased(new CollectorLeftRightOutCommand());

		opBumperL.whenPressed(new CollectorUpAction());
		opBumperL.whenReleased(new CollectorLeftRightOutCommand());
	}
	

	public Joystick getOpStick() {
		return opStick;
	}

	public Joystick getDriverStick() {
		return driverStick;
	}
}
