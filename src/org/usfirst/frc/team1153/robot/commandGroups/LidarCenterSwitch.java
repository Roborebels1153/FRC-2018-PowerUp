package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.commands.CarriageDownCommand;
import org.usfirst.frc.team1153.robot.commands.CarriageUpCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightInCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorLeftRightOutCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorOffCommand;
import org.usfirst.frc.team1153.robot.commands.CollectorOnCommand;
import org.usfirst.frc.team1153.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team1153.robot.commands.DriveDistanceCommandWithLidar;
import org.usfirst.frc.team1153.robot.commands.DriveDistanceSonarCommand;
import org.usfirst.frc.team1153.robot.commands.FireShooterCommand;
import org.usfirst.frc.team1153.robot.commands.GyroAbsOneSide;
import org.usfirst.frc.team1153.robot.commands.GyroAbsWithLidar;
import org.usfirst.frc.team1153.robot.commands.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LidarCenterSwitch extends CommandGroup {

	/**
	 * @param degreesToTurn
	 * @param distance
	 * @param switchSide
	 */
	public LidarCenterSwitch(int degreesToTurn, double distance, double turnBack, double driveToCube) {

		if (Robot.initialWait > 0.0) {
			addSequential(new WaitCommand(Robot.initialWait));
		}

		//1st Cube
		Robot.autoDrive.resetGyro();
		addSequential(new CarriageUpCommand());
//		addSequential(new DriveDistanceCommand(12, -1 * 12, 2));
//		addSequential(new GyroTurnAbsoluteCommand(degreesToTurn, 2));
		addSequential(new GyroAbsOneSide(degreesToTurn, 4, 0.2));
		addSequential(new DriveDistanceCommand(distance, -1 * distance, 2));
		addSequential(new FireShooterCommand(50));

		
		//Back to 2nd Cube
		addParallel(new CarriageDownCommand(0.5));
		addParallel(new CollectorDownAction());
		addSequential(new DriveDistanceCommand(-1 * (distance-5), (distance-5), 2, 0.7));
		addSequential(new CollectorLeftRightOutCommand());
		//addSequential(new GyroAbsOneSide(turnBack, 2, 0.1));
		addSequential(new GyroAbsWithLidar(turnBack, 1, 0.1));
		addParallel(new CollectorOnCommand());
//		addParallel(new CollectorDownAction());
//		addParallel(new CollectorOnCommand());
		
		//Turn These 2 back on if no Lidar
//		addSequential(new DriveDistanceCommand(driveToCube, -1* driveToCube, 2, 0.5));
//		addSequential(new DriveDistanceSonarCommand(0.75, 0.5));
		addSequential(new DriveDistanceCommandWithLidar(2, 0.5, 7));
		addSequential(new CollectorLeftRightInCommand(0.4));
		//addSequential(new WaitCommand (0.5));
		addSequential(new DriveDistanceCommand(-1* 5, 5, 2, 0.25));
		addSequential(new CollectorUpAction());
		///addSequential(new DriveDistanceCommand(-1* 10, 10, 2, 0.6));
		addParallel(new CarriageUpCommand());
		addParallel(new CollectorOffCommand());
		
		//score 2nd cube
		//addSequential(new WaitCommand (0.5));
//		addSequential(new DriveDistanceCommand(-1 * 58,  58, 2));
		addSequential(new DriveDistanceCommand(-1 * 43,  43, 2));

//		addSequential(new GyroTurnAbsoluteCommand(-1 * turnBack));
		addSequential(new GyroAbsOneSide((degreesToTurn * .95) + Math.copySign(1, degreesToTurn) + 0, 2));
		addSequential(new DriveDistanceCommand((distance + 5), -1 * (distance + 5), 3));
		addSequential(new FireShooterCommand(50));


		
		


	}
}
