package org.usfirst.frc.team1153.robot.lib;

import java.util.ArrayList;

public class StateScheduler {
	
	private static StateScheduler instance;
	
	private ArrayList<StateSubsystem> subsystems = new ArrayList<StateSubsystem>();
	
	public static StateScheduler getInstance() {
		if (instance == null) {
			instance = new StateScheduler();
		}
		
		return instance;
	}
	
	/**
	 * Adds a Subsystem to the schedulers list of handles if not
	 * already known
	 * @param in StateSubsystem to add
	 */
	public void addStateSubsystem(StateSubsystem in) {
		if (!subsystems.contains(in)) {
			subsystems.add(in);
		}
	}
	
	/**
	 * Runs a single iteration for all known subsystems
	 */
	public void runAll() {
		for (StateSubsystem a : subsystems) {
			a.run();
		}
	}
	
	/**
	 * Notifies all known subsystems that the robot has been
	 * disabled
	 */
	public void notifyDisabled() {
		for (StateSubsystem a : subsystems) {
			a.robotDisabled();
		}
	}
	
	/**
	 * Notifies all known subsystems that the robot is now in
	 * teleop
	 */
	public void notifyTeleop() {
		for (StateSubsystem a : subsystems) {
			a.robotTeleop();
		}
	}
	
	/**
	 * Notifies all known subsystems that the robot is now in
	 * auto
	 */
	public void notifyAuto() {
		for (StateSubsystem a : subsystems) {
			a.robotAuto();
		}
	}

}
