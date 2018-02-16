package org.usfirst.frc.team1153.robot.lib;

import edu.wpi.first.wpilibj.PIDOutput;

public class DummyOutput implements PIDOutput{
	double output;
	
	public DummyOutput() {
		output = 0;
	}
	
	public void pidWrite(double output) {
		this.output = output;
	}

	public double getOutput() {
		return output;
	}

}
