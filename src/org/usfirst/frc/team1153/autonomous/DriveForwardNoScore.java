package org.usfirst.frc.team1153.autonomous;

import org.usfirst.frc.team1153.robot.commands.DriveDistanceCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveForwardNoScore extends CommandGroup {
	
	double baselineToSwitchDistance = 112;

	
	public DriveForwardNoScore () {
		super();
		
		addSequential(new DriveDistanceCommand(baselineToSwitchDistance, -1  * baselineToSwitchDistance));
	}

}
