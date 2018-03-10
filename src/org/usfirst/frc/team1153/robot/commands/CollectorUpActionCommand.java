package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * The one that isn't tested yet
 */
public class CollectorUpActionCommand extends Command {
	
	private long startTime;

    public CollectorUpActionCommand() {
        requires(Robot.collectorArmsHorizontal);
        requires(Robot.collectorArmsVertical);
    }

    protected void initialize() {
    	startTime = System.currentTimeMillis();
    	Robot.collectorArmsHorizontal.inInit();
    	while (System.currentTimeMillis() - startTime < 300);
    	Robot.collectorArmsVertical.downInit();
    	while (System.currentTimeMillis() - startTime < 800);
		Robot.collectorArmsHorizontal.outInit();

    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
