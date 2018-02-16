package org.usfirst.frc.team1153.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class TeleDrive extends AutoDrive {
	
	 DifferentialDrive drive;

	
	public TeleDrive() {
		super();
		drive = new DifferentialDrive(leftMaster, rightMaster);
	}
	
	public void drive(Joystick joystick) {
		drive.arcadeDrive(-1 * joystick.getY(), 1 * joystick.getRawAxis(4));
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

