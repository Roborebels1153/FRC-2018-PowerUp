package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.subsystems.LimelightVision.Target;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionDriveSwitch extends Command {

	private char side;
	
	boolean bApproachedTarget = false;
	long startTime;
	
    public VisionDriveSwitch(char side) {
        requires(Robot.autoDrive);
        requires(Robot.vision);
        
        this.side = side;
    }

    protected void initialize() {
    	System.out.println("Vision ENABLED");
    	startTime = System.currentTimeMillis();
		Robot.vision.turnOnLight();

    	if (side == 'L') {
    		Robot.vision.setPipeline(0);
    	} else if (side =='R') {
    		Robot.vision.setPipeline(1);

    		System.out.println(side);
    	}
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
    	
    	System.out.println("Executing Limelight vision");
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
