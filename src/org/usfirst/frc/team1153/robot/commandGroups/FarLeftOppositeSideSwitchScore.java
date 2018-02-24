package org.usfirst.frc.team1153.robot.commandGroups;

import org.usfirst.frc.team1153.robot.commands.CarriageUpCommand;
import org.usfirst.frc.team1153.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team1153.robot.commands.FireShooterCommand;
import org.usfirst.frc.team1153.robot.commands.GyroTurnAbsoluteCommand;
import org.usfirst.frc.team1153.robot.commands.TimedDriveCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FarLeftOppositeSideSwitchScore extends CommandGroup {
	
	double baselineToFirstTurnDistance = 219;
	double driveAcrossFieldDistance = 264;
	double driveBackTowardsBaselineDistance = 12;

	
	public FarLeftOppositeSideSwitchScore () {
		super();
	
		addSequential(new CarriageUpCommand());
		addSequential(new DriveDistanceCommand(baselineToFirstTurnDistance, -1 * baselineToFirstTurnDistance));
		addSequential(new GyroTurnAbsoluteCommand(90));
		addSequential(new DriveDistanceCommand(driveAcrossFieldDistance, -1 * driveAcrossFieldDistance));
		//TODO: add gyro check to make sure we are still straight after hitting the bump
		addSequential(new GyroTurnAbsoluteCommand(90));
		addSequential(new DriveDistanceCommand(driveBackTowardsBaselineDistance, -1 * driveBackTowardsBaselineDistance));
		addSequential(new GyroTurnAbsoluteCommand(90));
		addSequential(new TimedDriveCommand(0.65, 1));
		addSequential(new FireShooterCommand());


	}

}
