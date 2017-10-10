package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;

import edu.wpi.first.wpilibj.Encoder;

public class MoveDistance {
	
	private PID moveDistancePID;
	
	public MoveDistance() {
		moveDistancePID = new PID(new signal(){
			public double getValue(){
				//return RobotMap.leftDriveEncoder.get();
				return (RobotMap.leftDriveEncoder.get() + RobotMap.rightDriveEncoder.get()) / 2;
				// Returns the average of the two encoders for theoretical more percision.
				// If no work just use commented code.
			}
		});
		moveDistancePID.set_pid(RobotMap.MoveDistance_KP, RobotMap.MoveDistance_KI, RobotMap.MoveDistance_KD);
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
		return ( ( RobotMap.leftDriveEncoder.get() + RobotMap.rightDriveEncoder.get() ) / 2 );
	}
	
	// Converts encoder ticks to feet
	private double ticksToFeet( double e ) { // SET THIS UP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! TODO
		System.out.println("ticksToFeet method not set up in Move Distance!!!!!");
		return 0.0;
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
	public void set_setpoint( double s ) {
		moveDistancePID.set_setpoint( s );
	}
	
	// Sets PID values to other than default
	public void set_pid( double p, double i, double d ) {
		moveDistancePID.set_pid( p, i ,d );
	}
}
