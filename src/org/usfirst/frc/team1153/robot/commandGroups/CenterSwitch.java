package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.commands.CarriageUpCommand;
import org.usfirst.frc.team1153.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team1153.robot.commands.FireShooterCommand;
import org.usfirst.frc.team1153.robot.commands.GyroTurnRelativeCommand;
import org.usfirst.frc.team1153.robot.commands.TimedDriveCommand;
import org.usfirst.frc.team1153.robot.commands.VisionDriveSwitch;
import org.usfirst.frc.team1153.robot.commands.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterSwitch extends CommandGroup {

	/**
	 * @param degreesToTurn
	 */
	public CenterSwitch(int degreesToTurn, double distance, double turnDiviser, char switchSide) {
		
		if (Robot.initialWait > 0.0) {
			addSequential(new WaitCommand(Robot.initialWait));
		}
		addSequential(new CarriageUpCommand());
		addSequential(new DriveDistanceCommand(12, -1 * 12));
		addSequential(new GyroTurnRelativeCommand(degreesToTurn));
		addSequential(new DriveDistanceCommand(distance, -1 * distance));
		addSequential(new GyroTurnRelativeCommand(-(degreesToTurn/turnDiviser)));
		//addSequential(new VisionDriveSwitch(switchSide));
		addSequential(new DriveDistanceCommand(48, -1 * 48));
		addSequential(new WaitCommand(0.1));
		addSequential(new FireShooterCommand());

	}
}
