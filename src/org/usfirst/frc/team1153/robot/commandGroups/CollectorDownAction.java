package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.commands.CarriageDownCommand;
import org.usfirst.frc.team1153.robot.commands.CheckForLimitSwitchCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightInCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightOutCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorUpDownInCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorUpDownOutCommand;
import org.usfirst.frc.team1153.robot.commands.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectorDownAction extends CommandGroup {
	
	public CollectorDownAction () {
		super();
		
		
		
		addSequential(new CollectorLeftRightInCommand());
		addSequential(new WaitCommand(0.1));
		addSequential(new CollectorUpDownOutCommand());
		addSequential(new CheckForLimitSwitchCommand());
		addSequential(new WaitCommand(0.1));
		addSequential(new CollectorLeftRightOutCommand());

		
		
		
	
		
	}

}
