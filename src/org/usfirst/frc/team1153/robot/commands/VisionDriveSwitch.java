package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.subsystems.LimelightVision.Target;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionDriveSwitch extends Command {

	boolean bApproachedTarget = false;
	
    public VisionDriveSwitch() {
        requires(Robot.autoDrive);
        requires(Robot.vision);
    }

    protected void initialize() {
    	System.out.println("Vision ENABLED");
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
    	}
    }

    protected boolean isFinished() {
        return bApproachedTarget;
    }

    protected void end() {
    }


    protected void interrupted() {
    }
}
