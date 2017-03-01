package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;

public class Lifting {
	
	private double left_lifter_finger_servo_close = 0.0;	//Fill These with correct values
	private double left_lifter_finger_servo_open = 0.0;		//Fill These with correct values
	private double right_lifter_finger_servo_close = 0.0;	//Fill These with correct values
	private double right_lifter_finger_servo_open = 0.0;	//Fill These with correct values
	private double locking_servo_open = 0.0;				//Fill These with correct values
	private double locking_servo_close = 0.0;				//Fill These with correct values
	
	private double time_accumulator = 0.0;
	
	
	enum lifter_servo_state{
		
	}
	
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
	
	public Lifting(Controller lifting_controller) {
		this.lifting_controller = lifting_controller;
		
		lifter = new Talon( 7 );
		
		left_lifter_finger_servo = new Servo( 11 );
    	right_lifter_finger_servo = new Servo( 12 );
    	locking_servo = new Servo( 9 );
	}

	public void update(double dt){
		
		switch( state ) {
		
			case close:
				left_lifter_finger_servo.set( left_lifter_finger_servo_close );
				right_lifter_finger_servo.set( right_lifter_finger_servo_close );
				locking_servo.set( locking_servo_open );
				state = lifter_state.close_wait;
				time_accumulator = 0.0; 
				break;
				
			case close_wait:
				time_accumulator += dt;
				if(time_accumulator > 1.2) {	//Change Value to Servo Closing time plus a bit
					state = lifter_state.lock;
				}
				break;
				
			case lock:
				locking_servo.set( locking_servo_close );
				state = lifter_state.lock_wait;
				time_accumulator = 0.0; 
				break;
				
			case lock_wait:
				time_accumulator += dt;
				if(time_accumulator > 1.2) {	//Change Value to Servo Closing time plus a bit
					state = lifter_state.climb;
				}
				break;
				
			case climb: 
				if(this.lifting_controller.get_b_button()) {
					lifter.set(1.0);
				}
				else
				{
					lifter.set(0.0);
				}
				break;
				
			case idle:
				if(this.lifting_controller.get_a_button()) {
					state = lifter_state.close;
				}
				break;
		}
		
		
		//double lifter_speed = lifting_controller.get_y_button() ? -1.0 : 0.0;
		
		//lifter.set( lifter_speed );
		
//		if ( lifting_controller.get_y_button() ) {
//			lifter.set( -1.0 );
//		}
//		if ( !lifting_controller.get_y_button() ) {
//			lifter.set( 0.0 );
//		}
		
		/* Write code for the Servos */
		
		
	}
	
	
}
