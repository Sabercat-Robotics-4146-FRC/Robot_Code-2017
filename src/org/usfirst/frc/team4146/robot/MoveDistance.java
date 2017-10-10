package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;

import edu.wpi.first.wpilibj.Encoder;

public class MoveDistance {
	
	public PID moveDistancePID;
	
	public MoveDistance() {
		moveDistancePID = new PID(new signal(){
			public double getValue(){
				//return RobotMap.leftDriveEncoder.get();
				return ticksToFeet(encoderTicks());
				// Returns the average of the two encoders for theoretical more percision.
				// If no work just use commented code.
			}
		});
		moveDistancePID.set_pid(RobotMap.MoveDistance_KP, RobotMap.MoveDistance_KI, RobotMap.MoveDistance_KD);
		moveDistancePID.setIntegralActivationRange(0.5);
	}
	
	int i = 0;
	public void update(double dt) {
		moveDistancePID.update(dt);
		
		if (i >= 20) {
			//System.out.println("Left Encoder: " + RobotMap.leftDriveEncoder.get());
			//System.out.println("Right Encoder: " + RobotMap.rightDriveEncoder.get());
			i = 0;
		}
		i++;
	}

	////// Encoder Related Methods //////

	// Returns average encoder ticks between the two drive encoders
	private double encoderTicks() {
		//return ((-RobotMap.leftDriveEncoder.getRaw() + RobotMap.rightDriveEncoder.getRaw()) / 2.0 );
		return (RobotMap.rightDriveEncoder.getRaw());
	}
	
	// Converts encoder ticks to feet 
	private double ticksToFeet( double e ) {
		return ( e + 134.9 ) / 1225.4;
	}
	
	// Resets Encoders
	public void resetEncoders() {
		RobotMap.leftDriveEncoder.reset();
		RobotMap.rightDriveEncoder.reset();
	}
	
	// A testing method because .get() returns encoder with scale factor whereas .getRaw does not. Important difference? // TEST ME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private int scaleFactor(){
		return RobotMap.leftDriveEncoder.getEncodingScale();
	}
	
	
	////// PID Related Methods //////
	
	// Returns the PID Output. Why do we need this??????????????????????????????????????????????????????????
	public double get() {
		return moveDistancePID.get();
	}
	
	// Sets Move Distance PID Setpoint
	public void setSetpoint(double s) {
		moveDistancePID.set_setpoint(s);
	}
	
	// Sets PID values to other than default
	public void setPID(double p, double i, double d) {
		moveDistancePID.set_pid(p, i ,d);
	}
	
	// 
	public boolean isNotInError(double timeOut, double timeElapsed){
		return ((moveDistancePID.get_error() > RobotMap.ACCEPTABLE_DISTANCE_ERROR) || (moveDistancePID.steady_state_error() > RobotMap.ACCEPTABLE_DISTANCE_ERROR)) && (timeElapsed < timeOut);
	}
}
