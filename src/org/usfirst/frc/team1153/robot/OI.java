/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1153.robot;

import org.usfirst.frc.team1153.robot.commands.CarriageDownCommand;
import org.usfirst.frc.team1153.robot.commands.CarriageUpCommand;
import org.usfirst.frc.team1153.robot.commands.FireShooterCommand;
import org.usfirst.frc.team1153.robot.commands.ResetCommand;
import org.usfirst.frc.team1153.robot.commands.RetractShooterCommand;
import org.usfirst.frc.team1153.robot.commands.ShiftHighCommand;
import org.usfirst.frc.team1153.robot.commands.ShiftLowCommand;
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

    public OI() {
    	
    	drTriggerL.whenPressed(new ShiftHighCommand());
    	drTriggerL.whenReleased(new ShiftLowCommand());

    	drButtonY.whenActive(new ResetCommand());

    	drButtonB.whenPressed(new FireShooterCommand());
    	drButtonB.whenReleased(new RetractShooterCommand());
    	
    	drButtonA.whenPressed(new CarriageUpCommand());
    	drButtonA.whenReleased(new CarriageDownCommand());
    }
    
    
    public Joystick getOpStick() {
    	return opStick;
    }
    
    public Joystick getDriverStick() {
    	return driverStick;
    }
}
