package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team1153.robot.commands.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveForwardNoScore extends CommandGroup {
	
	double baselineToSwitchDistance = 145;

	
	public DriveForwardNoScore () {
		super();
		
		if (Robot.initialWait > 0.0) {
			addSequential(new WaitCommand(Robot.initialWait));
		}
		addSequential(new DriveDistanceCommand(baselineToSwitchDistance, -1  * baselineToSwitchDistance, 3));
	}

}
