package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.commands.CarriageUpCommand;
import org.usfirst.frc.team1153.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team1153.robot.commands.FireShooterCommand;
import org.usfirst.frc.team1153.robot.commands.GyroTurnCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class FarRightSwitchScore extends CommandGroup {

	double baselineToSideSwitchDistance = 152;
	double turnToSwitchDistance = 12;

	public FarRightSwitchScore() {
		super();

		addSequential (new CarriageUpCommand());
		addSequential(new DriveDistanceCommand(baselineToSideSwitchDistance, -1 * baselineToSideSwitchDistance));
		addSequential(new WaitCommand(1));
		addSequential(new GyroTurnCommand(-90));
		addSequential(new DriveDistanceCommand(turnToSwitchDistance, -1 * turnToSwitchDistance));
		addSequential(new FireShooterCommand());
		
	}

}
