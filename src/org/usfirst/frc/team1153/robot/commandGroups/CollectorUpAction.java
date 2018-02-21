package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightInCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightOutCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorUpDownInCommand;
import org.usfirst.frc.team1153.robot.commands.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectorUpAction extends CommandGroup {
	
	public CollectorUpAction () {
		super();
		
		addSequential(new CollectorLeftRightInCommand());
		addSequential(new CollectorUpDownInCommand());
		addSequential(new CollectorLeftRightOutCommand());

	}

}
