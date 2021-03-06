package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.commands.CarriageUpCommand;
import org.usfirst.frc.team1153.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team1153.robot.commands.FireShooterCommand;
import org.usfirst.frc.team1153.robot.commands.GyroTurnAbsoluteCommand;
import org.usfirst.frc.team1153.robot.commands.TimedDriveCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class FarLeftOppositeSideSwitchScore extends CommandGroup {
	
	double baselineToFirstTurnDistance = 215;
	double driveAcrossFieldDistance = 120;//264
	double driveBackTowardsBaselineDistance = 24;
	double driveToScoreDistance = 12;


	
	public FarLeftOppositeSideSwitchScore () {
		super();
		
		if (Robot.initialWait > 0.0) {
			addSequential(new WaitCommand(Robot.initialWait));
		}
	
		addSequential(new CarriageUpCommand());
		addSequential(new DriveDistanceCommand(baselineToFirstTurnDistance, -1 * baselineToFirstTurnDistance, 3));
		addSequential(new GyroTurnAbsoluteCommand(90));
		addSequential(new DriveDistanceCommand(driveAcrossFieldDistance, -1 * driveAcrossFieldDistance, 3));
		//TODO: add gyro check to make sure we are still straight after hitting the bump
		addSequential(new GyroTurnAbsoluteCommand(90));
		addSequential(new DriveDistanceCommand(driveBackTowardsBaselineDistance, -1 * driveBackTowardsBaselineDistance, 3));
		addSequential(new GyroTurnAbsoluteCommand(135));
		addSequential(new DriveDistanceCommand(driveToScoreDistance, -1 * driveToScoreDistance, 3));
		addSequential(new FireShooterCommand());


	}

}
