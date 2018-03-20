package org.usfirst.frc.team1153.robot.commands;

import org.usfirst.frc.team1153.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SonarDriveCommand extends Command {
	
	double stopAt;
	double[] errorArray = new double[3];
	int samples = 0;
	long startTime;
	double timeOut;
	
	public SonarDriveCommand(double stopAt) {
		requires(Robot.autoDrive);

		this.stopAt = stopAt;
		Robot.autoDrive.getSonarPid().setSetpoint(stopAt);
	}

	protected void initialize() {
		startTime = System.currentTimeMillis();
		timeOut = 2;
		Robot.autoDrive.getSonarPid().setSetpoint(stopAt);
		Robot.autoDrive.getSonarPid().setEnabled(true);
		
		System.out.println("Sonar actiovated");
		
		SmartDashboard.putNumber("Sonar PID Error[0]", errorArray[0]);
		SmartDashboard.putNumber("Sonar PID Error[1]", errorArray[1]);
		SmartDashboard.putNumber("Sonar PID Error[2]", errorArray[2]);
		
		for (int c = 0; c <= 2; c++) {
			errorArray[c] = 0.0;
		}
	}

	protected void execute() {
		double pidOutput = Robot.autoDrive.getSonarPid().get();
		double error = Robot.autoDrive.getSonarPid().getError();
		errorArray[samples] = error;
		samples++;
		samples %= 3;
		
		SmartDashboard.putNumber("Sonar PID Output", pidOutput);
		SmartDashboard.putNumber("Sonar PID Error[0]", errorArray[0]);
		SmartDashboard.putNumber("Sonar PID Error[1]", errorArray[1]);
		SmartDashboard.putNumber("Sonar PID Error[2]", errorArray[2]);
		
		Robot.autoDrive.cheesyDriveWithoutJoysticks(pidOutput, 0);
	}

	private double avgError() {
		double totalError = 0.0;
		for (int c = 0; c <= 2; c++) {
			totalError += errorArray[c];
		}
		return totalError/3.0;
	}
	
	protected boolean isFinished() {
		
		if (this.avgError() <= 2.0 || ((System.currentTimeMillis() - startTime) > (timeOut * 1000) )) {
			Robot.autoDrive.cheesyDriveWithoutJoysticks(0, 0);
			return true;
		} else {
			return false;
		}
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
