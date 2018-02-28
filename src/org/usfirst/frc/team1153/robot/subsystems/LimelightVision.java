package org.usfirst.frc.team1153.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LimelightVision extends Subsystem {
	
	private NetworkTable table;
	
	private Target target;
	
	private PIDController horizontalAlignPid;
	
    public LimelightVision() {
    	table = NetworkTableInstance.getDefault().getTable("limelight");
    	PIDSource source = new PIDSource() {
    		
    		@Override
    		public double pidGet() {
    			return target.x;
    		}

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
    	};
    	PIDOutput output = new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				SmartDashboard.putNumber("PID Output", output);
			}
    		
    	};
    	horizontalAlignPid = new PIDController(0.04, 0.0015, 0.015, source, output);
    	//horizontalAlignPid = new PIDController(0.015, 0.001, 0.01, source, output);
    	horizontalAlignPid.setSetpoint(0);
    	horizontalAlignPid.setInputRange(-27, 27);
    	horizontalAlignPid.setOutputRange(-1, 1);
    }

	public void updateLimelightData() {
		NetworkTableEntry tv = table.getEntry("tv");
		boolean targetExists = tv.getDouble(0) > 0.0;
		
		if (!targetExists) {
			horizontalAlignPid.disable();
			target = null;
			return;
		}
		
		NetworkTableEntry tx = table.getEntry("tx");
		NetworkTableEntry ty = table.getEntry("ty");
		NetworkTableEntry ta = table.getEntry("ta");
		
		target = new Target();
		target.x = tx.getDouble(0);
		target.y = ty.getDouble(0);
		target.a = ta.getDouble(0);
		
		if (!horizontalAlignPid.isEnabled()) horizontalAlignPid.enable();
	}

	public void updateShuffleBoard() {
		SmartDashboard.putBoolean("haveTarget", target != null);
		if (target != null) {
			SmartDashboard.putNumber("targetX", target.x);
			SmartDashboard.putNumber("targetY", target.y);
			SmartDashboard.putNumber("targetA", target.a);
		}
	}

    public void initDefaultCommand() {
    }
    
    /**
     * Get most recent target parameters
     * @return a Target object with target parameters, otherwise null if no target is found
     */
    public Target getTargetValues() {
    	return target;
    }
    
    public double getHorizontalAlignOutput() {
    	return horizontalAlignPid.get();
    }
    
    public static class Target {
    	public double x;
    	public double y;
    	public double a;
    }
    
    public void turnOffLight() {
    	NetworkTableEntry ledMode = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode");
    	ledMode.setNumber(1);
    }
    
    public void turnOnLight() {
    	NetworkTableEntry ledMode = table.getEntry("ledMode");
    	ledMode.setNumber(0);
    }
    
    public void flushNetworkTables() {
		NetworkTableInstance.getDefault().flush();
	}
    
    public void setPipeline(int pipeline) {
		table = NetworkTableInstance.getDefault().getTable("limelight");
		NetworkTableEntry pipelineEntry = table.getEntry("pipeline");
		pipelineEntry.setNumber(pipeline);
		pipelineEntry.setDefaultNumber(pipeline);
		pipelineEntry.forceSetNumber(pipeline);
	}
    
    public void setCamMode(int camMode) {
		NetworkTableEntry camModeEntry = table.getEntry("camMode");
		camModeEntry.setNumber(camMode);

	}
}
