package org.usfirst.frc.team1153.robot;

public class Constants {

	/**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
	public static final int kSlotIdx = 0;

	/*
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;

	/*
	 * set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
	public static final int kTimeoutMs = 10;
	
//	public static final int TALON_TICKS_PER_INCH_LEFT = 136;
//	
//	public static final int TALON_TICKS_PER_INCH_RIGHT = 134;
	
	
//public static final int TALON_TICKS_PER_INCH_LEFT = 205;
//	
//	public static final int TALON_TICKS_PER_INCH_RIGHT = 203;
	
public static final int TALON_TICKS_PER_INCH_LEFT = 209;
	
	public static final int TALON_TICKS_PER_INCH_RIGHT = 207;

}