package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.commands.CarriageDownCommand;
import org.usfirst.frc.team1153.robot.commands.CheckForLimitSwitchCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightInCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightOutCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorUpDownOutCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectorDownAction extends CommandGroup {
	
	public CollectorDownAction () {
		super();
		
		
		addSequential(new CarriageDownCommand(0.25));
		addSequential(new CollectorLeftRightInCommand(0.2));
		addSequential(new CollectorUpDownOutCommand(0));
		//addSequential(new CheckForLimitSwitchCommand());
		addSequential(new CollectorLeftRightOutCommand());

		
		
		
	
		
	}

}
