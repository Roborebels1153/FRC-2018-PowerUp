package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.commands.CarriageUpCommand;
import org.usfirst.frc.team1153.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team1153.robot.commands.FireShooterCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveForwardAndScore extends CommandGroup {
	
	double baselineToSwitchDistance = 112;
	
	public DriveForwardAndScore () {
		super();
		
		addSequential (new CarriageUpCommand());
		addSequential(new DriveDistanceCommand(baselineToSwitchDistance, -1  * baselineToSwitchDistance));
		addSequential(new FireShooterCommand());
		
	}

}
