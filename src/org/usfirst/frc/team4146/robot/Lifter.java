package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;


// Note: 25% stall torque for climber
public class Lifter {
	
	private final double left_lifter_finger_servo_close = 0.8;	//Fill These with correct values
	private final double left_lifter_finger_servo_open = 0.0;		//Fill These with correct values
	
	private final double right_lifter_finger_servo_close = 0.1;	//Fill These with correct values
	private final double right_lifter_finger_servo_open = 0.99;	//Fill These with correct values
	private final double locking_servo_open = 0.8;				//Fill These with correct values
	private final double locking_servo_close = 0.2;			//Fill These with correct values
	
	private double time_accumulator = 0.0;
	
	
	enum lifter_state{
		close,
		close_wait,
		lock,
		lock_wait,
		climb,
		idle
	}
	
	lifter_state state = lifter_state.idle;
	
	
	Controller lifting_controller;
	
	
	Talon lifter;
	
	Servo left_lifter_finger_servo;
	Servo right_lifter_finger_servo;
	Servo locking_servo;
	
	
	public Lifter( Controller lifting_controller ) {
		this.lifting_controller = lifting_controller;
		
		lifter = new Talon( 7 );
		
		left_lifter_finger_servo = new Servo( 11 );
    	right_lifter_finger_servo = new Servo( 12 );
    	locking_servo = new Servo( 9 );
    	
    	left_lifter_finger_servo.set( left_lifter_finger_servo_open );
    	right_lifter_finger_servo.set( right_lifter_finger_servo_open );
    	
    	
    	//locking_servo.set( locking_servo_open );
	}

	public void update( double dt ) {
		switch( state ) {
		
			case close:
				System.out.println( "Close" );
				left_lifter_finger_servo.set( left_lifter_finger_servo_close );
				right_lifter_finger_servo.set( right_lifter_finger_servo_close );
				locking_servo.set( locking_servo_open );
				state = lifter_state.close_wait;
				time_accumulator = 0.0; 
				break;
				
			case close_wait:
				time_accumulator += dt;
				if( time_accumulator > 1.2 ) {	//Change Value to Servo Closing time plus a bit
					state = lifter_state.lock;
				}
				break;
				
			case lock:
				System.out.println( "Lock" );
				locking_servo.set( locking_servo_close );
				state = lifter_state.lock_wait;
				time_accumulator = 0.0; 
				break;
				
			case lock_wait:
				time_accumulator += dt;
				if( time_accumulator > 2.8 ) {	//Change Value to Servo Closing time plus a bit
					left_lifter_finger_servo.set( left_lifter_finger_servo_open );
			    	right_lifter_finger_servo.set( right_lifter_finger_servo_open );
					state = lifter_state.climb;
				}
				break;
				
			case climb: 
				if( this.lifting_controller.get_b_button() ) {
					lifter.set( -1.0 );
				} else if ( this.lifting_controller.get_x_button() ) {
					lifter.set( 1.0 );
				} else {
					lifter.set( 0.0 );
				}
				
				if( this.lifting_controller.get_y_button() ) {
					left_lifter_finger_servo.set( left_lifter_finger_servo_open );
			    	right_lifter_finger_servo.set( right_lifter_finger_servo_open );
			    	locking_servo.set( locking_servo_open );
			    	state = lifter_state.idle;
				}
				break;
				
			case idle:
				if( this.lifting_controller.get_a_button() ) {
					state = lifter_state.close;
				}
				if ( this.lifting_controller.get_x_button() ) {
					lifter.set( 0.25 );
				} else if ( this.lifting_controller.get_b_button() ) {
					lifter.set( -0.25 );
				} else {
					lifter.set( 0.0 );
				}
				break;
		}
		
	}
	
	
}
