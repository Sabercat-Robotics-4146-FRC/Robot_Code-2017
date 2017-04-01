package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;


public class Gear {

	public static final double GEAR_IN = 0.35;
	public static final double GEAR_OUT = 0.64;
	//public static final double TILT_IN;
	//public static final double TILT_OUT;
	public final double TILT_IN_SPEED = -0.4;
	public final double TILT_OUT_SPEED = 0.4;
	double tilt_motor_speed = 0.0;
	
	Servo gear_servo;
	Spark tilt_motor;
	Controller drive_controller;
	
	/*Gear Servo State Machine*/
	enum gear_state {
		out,
		in
	}
	
	gear_state gear;
	boolean x_button_toggle;
	
	public Gear( Servo gear_servo, Spark tilt_motor, Controller drive_controller ) {
		this.gear_servo = gear_servo;
		this.tilt_motor = tilt_motor;
		this.drive_controller = drive_controller;
		
		gear = gear_state.out; //Should be first state to run since gear servo starts closed
		
		x_button_toggle = true;
		
	}
	
	public void update( double dt ){
		
		// handle gear servo
		if ( drive_controller.get_x_button() && x_button_toggle ) {
			x_button_toggle = false;
			switch ( gear ) {
				case in:
					System.out.println( "Moving In! / Closing" );
					gear_servo.set( GEAR_IN ); // In number
					break;
				case out:
					System.out.println( "Moving Out! / Opening" );
					gear_servo.set( GEAR_OUT ); // Out number
					break;
				default:
					break;
			}
			if ( gear == gear_state.in ) {
				gear = gear_state.out;
			} else {
				gear = gear_state.in;
			}
		}
		if ( !drive_controller.get_x_button() ) {
			x_button_toggle = true;
		}
		
//		tilt_motor_speed = TILT_IN_SPEED;
//		if ( drive_controller.get_left_bumper() ){
//			tilt_motor_speed = TILT_OUT_SPEED;
//		}
//		tilt_motor.set( tilt_motor_speed );
	}
}
