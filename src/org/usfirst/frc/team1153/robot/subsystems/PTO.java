package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.RobotMap;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem;

import edu.wpi.first.wpilibj.Solenoid;

public class PTO extends StateSubsystem {

	Solenoid PTO;
	
	public static final StateSubsystem.State STATE_ENGAGED = new StateSubsystem.State("engaged");
	
	public static final StateSubsystem.State STATE_DISENGAGED = new StateSubsystem.State("disengaged");

	

	
	public PTO() {
		PTO = new Solenoid(RobotMap.THIRD_PCM, 2);
		
		registerState(STATE_ENGAGED);
		registerState(STATE_DISENGAGED);

	}
	
	public void enagagedInit() {
		PTO.set(true);
	}
	
	public void engagedPeriodic() {
		
	}
	
	public void disengagedInit() {
		PTO.set(false);
	}
	
	public void disengagedPeriodic(){
		
	}
	
	
	@Override
	protected State getDisabledDefaultState() {
		// TODO Auto-generated method stub
		return STATE_DISENGAGED;
	}

	@Override
	protected State getTeleopDefaultState() {
		// TODO Auto-generated method stub
		return STATE_DISENGAGED;
	}

	@Override
	protected State getAutoDefaultState() {
		// TODO Auto-generated method stub
		return STATE_DISENGAGED;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
