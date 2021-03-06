package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.Robot;
import org.usfirst.frc.team1153.robot.RobotMap;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Manages the in/out pistons for the Collector assembly
 */
public class ArmsHorizontal extends StateSubsystem {
	
	/**
	 * Collector arms are in
	 */
	public static final StateSubsystem.State STATE_IN = new StateSubsystem.State("in");
	
	/**
	 * Collector arms are out
	 */
	public static final StateSubsystem.State STATE_OUT = new StateSubsystem.State("out");
	
	private DoubleSolenoid horizontalSolenoid;
	
	public ArmsHorizontal() {
		horizontalSolenoid = new DoubleSolenoid(RobotMap.ARM_HORIZONTAL_A, RobotMap.ARM_HORIZONTAL_B);
		
		registerState(STATE_IN);
		registerState(STATE_OUT);
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
	
	public void inInit() {
		horizontalSolenoid.set(Value.kForward);
	}
	
	public void inPeriodic() {
		
	}
	
	public void outInit() {
		horizontalSolenoid.set(Value.kReverse);
	}
	
	public void outPeriodic() {
		
	}

	@Override
	protected State getTeleopDefaultState() {
		State currState = Robot.collectorArmsHorizontal.getState();
		return currState;
	}

	@Override
	protected State getAutoDefaultState() {
		return STATE_OUT;
	}

}
