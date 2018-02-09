package org.usfirst.frc.team1153.robot.lib;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Provides an outline for a typical state-driven Subsystem
 */
public abstract class StateSubsystem extends Subsystem {

	/**
	 * Holds the current state of the subsystem
	 */
	protected int state;
	/**
	 * Whether or not the latest state has been initialized; used
	 * determine whether or not we should call init or periodic
	 */
	protected boolean isInit = false;
    
    /**
	 * Called from all periodic methods in robot to update hardware
	 * components based on their state
	 */
	public abstract void run();
	
	/**
	 * Getter for the current state of the subsystem
	 * @return an int representing the current state
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * Setter for the current state of the subsystem
	 * @param in state to enter
	 */
	public void setState(int in) {
		isInit = false;
		state = in;
	}
	
	/**
	 * Set the state of the subsystem when robot is disabled
	 * @return the int state id for disabled
	 */
	protected abstract int getDisabledDefaultState();
	
	/**
	 * Set the state of the subsystem when robot is in teleop
	 * @return the int state id for teleop
	 */
	protected abstract int getTeleopDefaultState();
	
	/**
	 * Set the state of the subsystem when robot is in auto
	 * @return the int state id for auto
	 */
	protected abstract int getAutoDefaultState();
	
	/**
	 * Called when the robot first enters the disabled state
	 */
	public void robotDisabled() {
		setState(getDisabledDefaultState());
	}
	
	/**
	 * Called when the robot first enters the teleop state
	 */
	public void robotTeleop() {
		setState(getTeleopDefaultState());
	}
	
	/**
	 * Called when the robot first enters the auto state
	 */
	public void robotAuto() {
		setState(getAutoDefaultState());
	}
}

