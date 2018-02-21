package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team1153.robot.commands.FireShooterCommand;
import org.usfirst.frc.team1153.robot.commands.GyroTurnCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class FarLeftSwitchScore extends CommandGroup {

	double baselineToSideSwitchDistance = 152;
	double turnToSwitchDistance = 45;

	public FarLeftSwitchScore() {
		super();

		addSequential(new DriveDistanceCommand(baselineToSideSwitchDistance, -1 * baselineToSideSwitchDistance));
		addSequential(new WaitCommand(1));
		addSequential(new GyroTurnCommand(90));
		addSequential(new DriveDistanceCommand(turnToSwitchDistance, -1 * turnToSwitchDistance));
		addSequential(new FireShooterCommand());
	}

}