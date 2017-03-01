package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;

public class Lifting {
	
	private final double left_lifter_finger_servo_close = 0.0;	//Fill These with correct values
	private final double left_lifter_finger_servo_open = 0.0;		//Fill These with correct values
	private final double right_lifter_finger_servo_close = 0.0;	//Fill These with correct values
	private final double right_lifter_finger_servo_open = 0.0;	//Fill These with correct values
	private final double locking_servo_open = 0.0;				//Fill These with correct values
	private final double locking_servo_close = 0.0;				//Fill These with correct values
	
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
	
	Servo locking_servo;
	Servo left_lifter_finger_servo;
	Servo right_lifter_finger_servo;
	
	
	public Lifting(Controller lifting_controller, Talon lifter, Servo locking_servo, Servo left_lifter_finger_servo, Servo right_lifter_finger_servo) {
		//Getting passed objects
		this.lifting_controller = lifting_controller;
		
		this.lifter = lifter;
		
		this.locking_servo = locking_servo;
		this.left_lifter_finger_servo = left_lifter_finger_servo;
		this.right_lifter_finger_servo = right_lifter_finger_servo;
		
		//Setting servos to open
		this.locking_servo.set( locking_servo_open );
		this.left_lifter_finger_servo.set( left_lifter_finger_servo_open );
		this.right_lifter_finger_servo.set( right_lifter_finger_servo_open );
		
	}

	public void update( double dt ) {
		
		switch( state ) {
		
			case close:
				this.left_lifter_finger_servo.set( left_lifter_finger_servo_close );
				this.right_lifter_finger_servo.set( right_lifter_finger_servo_close );
				this.locking_servo.set( locking_servo_open );
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
				this.locking_servo.set( locking_servo_close );
				state = lifter_state.lock_wait;
				time_accumulator = 0.0; 
				break;
				
			case lock_wait:
				time_accumulator += dt;
				if( time_accumulator > 1.2 ) {	//Change Value to Servo Closing time plus a bit
					state = lifter_state.climb;
				}
				break;
				
			case climb: 
				if( this.lifting_controller.get_b_button() ) {
					this.lifter.set( 1.0 );
				}
				else
				{
					this.lifter.set( 0.0 );
				}
				
				if( this.lifting_controller.get_y_button() ) {
					state = lifter_state.idle;
					this.locking_servo.set( locking_servo_open );
					this.left_lifter_finger_servo.set( left_lifter_finger_servo_open );
					this.right_lifter_finger_servo.set( right_lifter_finger_servo_open );
				}
				break;
				
			case idle:
				if( this.lifting_controller.get_a_button() ) {
					state = lifter_state.close;
				}
				break;
		}
		
	}
	
}
