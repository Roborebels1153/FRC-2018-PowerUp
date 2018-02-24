package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.RobotMap;
import org.usfirst.frc.team1153.robot.lib.StateSubsystem;

import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 */
public class Climber extends StateSubsystem {

	Solenoid climber;
	Solenoid PTO;

	public static final StateSubsystem.State STATE_RETRACTED = new StateSubsystem.State("retracted");

	public static final StateSubsystem.State STATE_EXTENDED = new StateSubsystem.State("extended");
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Climber() {
		climber = new Solenoid(RobotMap.THIRD_PCM, 0);
		PTO = new Solenoid(RobotMap.THIRD_PCM,2);

		registerState(STATE_RETRACTED);
		registerState(STATE_EXTENDED);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void firePTO() {
		PTO.set(true);
	}
	
	public void retractPTO() {
		PTO.set(false);
	}

	// public void setPistonState(boolean value) {
	// climberA.set(value);
	// climberB.set(value);
	// }

	public void retractedInit() {
		climber.set(true);
	}

	public void extendedInit() {
		climber.set(false);
	}

	public void retractedPeriodic() {

	}

	public void extendedPeriodic() {

	}

	@Override
	protected State getDisabledDefaultState() {
		// TODO Auto-generated method stub
		return STATE_RETRACTED;
	}

	@Override
	protected State getTeleopDefaultState() {
		// TODO Auto-generated method stub
		return STATE_RETRACTED;
	}

	@Override
	protected State getAutoDefaultState() {
		// TODO Auto-generated method stub
		return STATE_RETRACTED;
	}

}
