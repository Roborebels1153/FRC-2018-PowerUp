package org.usfirst.frc.team1153.robot.lib;

import edu.wpi.first.wpilibj.SpeedController;

/*
This is Differential Drive minus the problems (Hopefully)
*/
public class BasicDrive {
	private SpeedController left;
	private SpeedController right;
	
	public BasicDrive(SpeedController l, SpeedController r) {
		right = r;
		left = l;
	}
	
	public void arcadeDrive(double speed, double rotate, boolean sqInputs) {
		speed = limit(speed);
		rotate = limit(rotate);

	    if (sqInputs) {
	        speed = Math.copySign(speed * speed, speed);
	        rotate = Math.copySign(rotate * rotate, rotate);
	      }
	    
	    double leftMotorOutput;
	    double rightMotorOutput;

	    double maxInput = Math.copySign(Math.max(Math.abs(speed), Math.abs(rotate)), speed);

	    if (speed >= 0.0) {
	      // First quadrant, else second quadrant
	      if (rotate >= 0.0) {
	        leftMotorOutput = maxInput;
	        rightMotorOutput = speed - rotate;
	      } else {
	        leftMotorOutput = speed + rotate;
	        rightMotorOutput = maxInput;
	      }
	    } else {
	      // Third quadrant, else fourth quadrant
	      if (rotate >= 0.0) {
	        leftMotorOutput = speed + rotate;
	        rightMotorOutput = maxInput;
	      } else {
	        leftMotorOutput = maxInput;
	        rightMotorOutput = speed - rotate;
	      }
	    }

	    left.set(limit(leftMotorOutput));
	    right.set(-limit(rightMotorOutput));
		
	}
	
	private double limit(double c) {
		if (c > 1) {
			c = 1;
		} else if (c < -1) {
			c = -1;
		}
		return c;
	}
}

