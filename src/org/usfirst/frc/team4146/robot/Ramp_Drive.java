package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;

public class Ramp_Drive {
	
	// Constants
	private static final double accelerate_rate = 0.65;
	private static final double decelerate_rate = 1.0;
	private static final double mech_deadband = 0.3;
	private static final double mSlope = ( (1.0 - mech_deadband) / (1.0 - Controller.ctrl_deadband) );
	private static final double bIntercept = ( (-Controller.ctrl_deadband * mSlope) + mech_deadband );
	// Variables
	private long this_time = 0;
	private long last_time = System.nanoTime();
	private double speed = 0.0;
	private double dt;
	private double targetSpeed;
	// Objects
	private Controller drive_controller;
	private RobotDrive drive;
	
	Ramp_Drive(Controller dc, RobotDrive d) {
		drive_controller = dc;
		drive = d;
	}
	
	public void ramp_drive() {
		this_time = System.nanoTime();
		//dt = ( this_time - last_time ) * 1e-9;
		last_time = this_time;
		double left_y = -drive_controller.get_deadband_left_y_axis(); // Inverted because controller is inverted by default
		
		if ( speed < left_y ) {
			speed += accelerate_rate * dt;
		}
		if ( speed > left_y ) {
			speed -= accelerate_rate * dt;
		}
		//check_speed( left_y );
		drive.arcadeDrive( speed, -drive_controller.get_deadband_right_x_axis() ); // Inverted because controller is inverted by default
		System.out.printf( "% 5.2f -- % 5.2f -- % 5.2f \n", left_y, targetSpeed, speed );
		dt = (System.nanoTime() - last_time) * 1e-9; 
		Timer.delay( 0.005 );
	}
	
	private void check_speed( double left_y ) {
		mechanical_deadband_escape( left_y );
		targetSpeed = find_target_speed( left_y );
		if( (targetSpeed == 0.0) && (speed > -mech_deadband) && (speed < mech_deadband) ) {
			speed = 0.0;
		}
		else if( (targetSpeed > speed) && (targetSpeed > 0) ) { //Traveling forward but speed is not fast enough
			speed +=  accelerate_rate * dt;
		}
		else if( (targetSpeed < speed) && (targetSpeed < 0) ) { //Traveling backwards but speed is not fast enough
			speed -= accelerate_rate * dt;
		}
		else if( (targetSpeed < speed) && (targetSpeed >= 0) ) { //Traveling forwards but speed is too fast
			speed -= decelerate_rate * dt;
		}
		else if( (targetSpeed > speed) && (targetSpeed <= 0) ) { //Traveling backwards but speed is too fast
			speed +=  decelerate_rate * dt;
		}
	}

	private void mechanical_deadband_escape( double left_y ) { // I want to change the name of this 
		if( (left_y > 0) && (speed == 0.0) ) {
			speed = mech_deadband;
		}
		else if( (left_y < 0) && (speed == 0.0) ) {
			speed = -mech_deadband;
		}
	}
	private double find_target_speed( double left_y ) {
		if( left_y == 0.0 ) {
			return 0.0;
		}
		else if( left_y > 0.0 ) {
			return ( (mSlope * left_y) + bIntercept );
		}
		else if(left_y < 0.0) {
			return ( (mSlope * left_y) - bIntercept );
		}
		return 0.0;
	}
}
