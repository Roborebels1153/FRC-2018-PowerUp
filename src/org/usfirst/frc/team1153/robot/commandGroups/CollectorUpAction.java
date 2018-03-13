package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.commands.CarriageDownCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightInCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightOutCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorUpDownInCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectorUpAction extends CommandGroup {
	
	public CollectorUpAction () {
		super();
		
		
		addSequential(new CarriageDownCommand(0.5));
		addSequential(new CollectorLeftRightInCommand(0.1));
		addSequential(new CollectorUpDownInCommand(0.4));
		addSequential(new CollectorLeftRightOutCommand());

	}

}
