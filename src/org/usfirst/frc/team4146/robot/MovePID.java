package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;

public class MovePID extends PID {
	public MovePID() {
		super(new signal() {
			public double getValue(){
				return ticksToFeet(encoderTicks());
			}
		});
		super.set_pid(0.002, 0.008, 0.0);
	}
//	@Override
//	public double getSystemError(){
//		return find_smallest_angle(RobotMap.gyro.getFusedHeading(), setpoint);
//	}
	
	//////Encoder Related Methods //////

	// Returns average encoder ticks between the two drive encoders
	private static double encoderTicks() { 
		return ((RobotMap.leftDriveEncoder.getRaw() + RobotMap.rightDriveEncoder.getRaw()) / 2.0 ); // Test this!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//return (RobotMap.leftDriveEncoder.getRaw());
	}
	
	// Converts encoder ticks to feet 
	private static double ticksToFeet( double e ) {
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
}