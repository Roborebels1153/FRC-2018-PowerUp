package org.usfirst.frc.team1153.robot.lib;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Provides an outline for a typical state-driven Subsystem
 */
public abstract class StateSubsystem extends Subsystem {

	/**
	 * Holds the current state of the subsystem
	 */
	private StateSubsystem.State state;

	/**
	 * Holds references to known states
	 */
	private ArrayList<StateSubsystem.State> knownStates = new ArrayList<>();

	/**
	 * Whether or not the latest state has been initialized; used determine whether
	 * or not we should call init or periodic
	 */
	private boolean isInit = false;

	/**
	 * Calls the appropriate statePeriodic and stateInit methods
	 */
	public void run() {
		try {
			if (!isInit) {
				getClass().getMethod(state.name + "Init").invoke(this);
				isInit = true;
			} else {
				getClass().getMethod(state.name + "Periodic").invoke(this);
			}
		} catch (NoSuchMethodException e) {
			// Subclasses of StateSubsystem are not required to implement either method
			// e.printStackTrace();
		} catch (InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException("Invalid subclass of StateSubsystem , " + getClass());
		}

	}

	/**
	 * Used in the constructor to register new states for the underlying
	 * StateSubsystem manager
	 */
	protected void registerState(StateSubsystem.State state) {
		knownStates.add(state);
	}

	/**
	 * Getter for the current state of the subsystem
	 * 
	 * @return an int representing the current state
	 */
	public StateSubsystem.State getState() {
		return state;
	}

	/**
	 * Setter for the current state of the subsystem
	 * 
	 * @param in
	 *            state to enter
	 */
	public void setState(StateSubsystem.State in) {
		if (!knownStates.contains(in)) {
			throw new IllegalArgumentException("Invalid state (" + in.name + ", " + in.getClass()
					+ "), maybe missing calls to registerState(StateSubsystem.State)?");
		}
		// Only call the initialize methods if the state has actually changed
		if (state != in) {
			isInit = false;
		}
		state = in;
	}

	/**
	 * Set the state of the subsystem when robot is disabled
	 * 
	 * @return the int state id for disabled
	 */
	protected abstract StateSubsystem.State getDisabledDefaultState();

	/**
	 * Set the state of the subsystem when robot is in teleop
	 * 
	 * @return the state for teleop
	 */
	protected abstract StateSubsystem.State getTeleopDefaultState();

	/**
	 * Set the state of the subsystem when robot is in auto
	 * 
	 * @return the state for auto
	 */
	protected abstract StateSubsystem.State getAutoDefaultState();

	/**
	 * Called when the robot first enters the disabled state
	 */
	public void robotDisabled() {
		State state = getDisabledDefaultState();
		if (state != null) {
			setState(state);
		}
	}

	/**
	 * Called when the robot first enters the teleop state
	 */
	public void robotTeleop() {
		State state = getTeleopDefaultState();
		if (state != null) {
			setState(state);
		}
	}

	/**
	 * Called when the robot first enters the auto state
	 */
	public void robotAuto() {
		State state = getAutoDefaultState();
		if (state != null) {
			setState(state);
		}
	}

	public static class State {

		public final String name;

		public State(String name) {
			this.name = name;
		}
	}
}
