package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.commands.CarriageDownCommand;
import org.usfirst.frc.team1153.robot.commands.CarriageUpCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightInCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightOutCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorOnCommand;
import org.usfirst.frc.team1153.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team1153.robot.commands.FireShooterCommand;
import org.usfirst.frc.team1153.robot.commands.GyroAbsOneSide;
import org.usfirst.frc.team1153.robot.commands.GyroTurnAbsoluteCommand;
import org.usfirst.frc.team1153.robot.commands.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FastCenterSwitch extends CommandGroup {

	/**
	 * @param degreesToTurn
	 * @param distance
	 * @param switchSide
	 */
	public FastCenterSwitch(int degreesToTurn, double distance, double turnBack) {

		if (Robot.initialWait > 0.0) {
			addSequential(new WaitCommand(Robot.initialWait));
		}

		//1st Cube
		Robot.autoDrive.resetGyro();
		addSequential(new CarriageUpCommand());
//		addSequential(new DriveDistanceCommand(12, -1 * 12, 2));
//		addSequential(new GyroTurnAbsoluteCommand(degreesToTurn, 2));
		addSequential(new GyroAbsOneSide(degreesToTurn, 2));
		addSequential(new DriveDistanceCommand(distance, -1 * distance, 2));
		addSequential(new FireShooterCommand());

		
		//Back to 2nd Cube
		addSequential(new DriveDistanceCommand(-1 * distance, distance, 2));
//		addSequential(new GyroTurnAbsoluteCommand(turnBack, 1));
		addSequential(new GyroAbsOneSide(turnBack, 1));
		addSequential(new CarriageDownCommand(0.5));
		addSequential(new CollectorDownAction());
		addParallel(new CollectorOnCommand());
		addSequential(new DriveDistanceCommand(65, -1* 65, 2));
		addSequential(new CollectorLeftRightInCommand(0.5));
		addSequential(new WaitCommand (0.5));
		addSequential(new CollectorUpAction());
		
		//score 2nd cube
		addSequential(new WaitCommand (0.5));
		addSequential(new DriveDistanceCommand(-1 * 63,  63, 2));
//		addSequential(new GyroTurnAbsoluteCommand(-1 * turnBack));
		addSequential(new GyroAbsOneSide(degreesToTurn, 2));
		addSequential(new DriveDistanceCommand(distance, -1 * distance, 2));

		
		


	}
}
