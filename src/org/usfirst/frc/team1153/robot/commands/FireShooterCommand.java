package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.subsystems.ArmsHorizontal;
import org.usfirst.frc.team1153.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FireShooterCommand extends Command {
	
	long timeAtStart;
	long waitTime;


    public FireShooterCommand() {
    	requires (Robot.shooter);
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		this.waitTime = 0;
    }
    
    public FireShooterCommand(long wait) {
    	requires (Robot.shooter);
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		this.waitTime = wait;

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.collectorArmsHorizontal.setState(ArmsHorizontal.STATE_OUT);
    	Robot.shooter.setState(Shooter.STATE_FIRE);
    	timeAtStart = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() - timeAtStart > waitTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if ((waitTime != 0) && (System.currentTimeMillis() - this.timeAtStart > this.waitTime)) {
    		Robot.shooter.setState(Shooter.STATE_RETRACT);
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
