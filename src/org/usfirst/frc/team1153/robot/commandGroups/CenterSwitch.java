package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.commands.CarriageUpCommand;
import org.usfirst.frc.team1153.robot.commands.FireShooterCommand;
import org.usfirst.frc.team1153.robot.commands.GyroTurnAbsoluteCommand;
import org.usfirst.frc.team1153.robot.commands.GyroTurnRelativeCommand;
import org.usfirst.frc.team1153.robot.commands.TimedDriveCommand;
import org.usfirst.frc.team1153.robot.commands.VisionDriveSwitch;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterSwitch extends CommandGroup {

	/**
	 * @param degreesToTurn
	 */
	public CenterSwitch(int degreesToTurn) {
		
		/**
		 * Working code for 
		 */
		// addSequential (new WaitCommand(Robot.initialWait));
		addSequential(new GyroTurnRelativeCommand(degreesToTurn));
		addSequential( new TimedDriveCommand(0.4, 0.8));
		addSequential(new GyroTurnRelativeCommand(-(degreesToTurn/1.5)));
		addSequential(new VisionDriveSwitch());
		addSequential(new CarriageUpCommand());
		addSequential(new FireShooterCommand());

	}
}
