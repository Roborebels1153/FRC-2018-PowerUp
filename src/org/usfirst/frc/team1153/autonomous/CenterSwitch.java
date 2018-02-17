package org.usfirst.frc.team1153.autonomous;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.commands.GyroTurnCommand;
import org.usfirst.frc.team1153.robot.commands.TimedDriveCommand;
import org.usfirst.frc.team1153.robot.commands.VisionDriveSwitch;
import org.usfirst.frc.team1153.robot.commands.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterSwitch extends CommandGroup {

    public CenterSwitch(int degreesToTurn) {
    	addSequential (new WaitCommand(Robot.initialWait));
    	addSequential(new GyroTurnCommand(degreesToTurn));
    	addSequential (new WaitCommand(Robot.middleWait));
    	addSequential(new VisionDriveSwitch());
    	addSequential(new TimedDriveCommand(0.6, 0.5));

    }
}
