package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.subsystems.LimelightVision.Target;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionDriveSwitch extends Command {

	boolean bApproachedTarget = false;
	long startTime;
	
    public VisionDriveSwitch() {
        requires(Robot.autoDrive);
        requires(Robot.vision);
    }

    protected void initialize() {
    	System.out.println("Vision ENABLED");
    	startTime = System.currentTimeMillis();
    }

    protected void execute() {
    	Target target = Robot.vision.getTargetValues();
    	if (target != null) {
    		if (target.a > 2.0) {
    			bApproachedTarget = true;
    			Robot.autoDrive.cheesyDriveWithoutJoysticks(0, 0);
    		} else {
    			Robot.autoDrive.cheesyDriveWithoutJoysticks(-0.30, Robot.vision.getHorizontalAlignOutput() * -1);
    		}
    	} else {
    		Robot.autoDrive.cheesyDriveWithoutJoysticks(0, 0);
    		if ((System.currentTimeMillis() - startTime) > 1000) {
    			bApproachedTarget = true;
    		}
    	}
    }

    protected boolean isFinished() {
        return bApproachedTarget;
    }

    protected void end() {
    	
    	System.out.println("Vision finished");
    }


    protected void interrupted() {
    }
}
