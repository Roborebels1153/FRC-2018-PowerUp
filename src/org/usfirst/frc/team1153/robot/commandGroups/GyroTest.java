package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.commands.GyroTurnRelativeCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GyroTest extends CommandGroup {

    public GyroTest() {
     super();
     
     addSequential(new GyroTurnRelativeCommand(-90));
     addSequential(new GyroTurnRelativeCommand(90));

     
    }
}
