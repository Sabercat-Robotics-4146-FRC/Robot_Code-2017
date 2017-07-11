package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;

// Note: -60% stall torque for climber
public class Lifter {
	
	private final double right_lifter_finger_servo_close = 0.0;	//Fill These with correct values - 0.9 //0.0
	private final double right_lifter_finger_servo_open = 1.0;		//Fill These with correct values //1.0
	
	private final double left_lifter_finger_servo_close = 1.0;	//Fill These with correct values //1.0
	private final double left_lifter_finger_servo_open = 0.0;	//Fill These with correct values - 0.99 //0.0
	
//	private double time_accumulator = 0.0;
	
	private boolean leftFingerServoFlag = false;
	private boolean rightFingerServoFlag = false;


//	enum lifter_state{
//		close,
//		close_wait,
//		lock,
//		lock_wait,
//		climb,
//		idle
//	}
//	
//	lifter_state state = lifter_state.idle;
	
	Controller lifting_controller;
	
	Talon lifterA;
	Talon lifterB;
	
	Servo left_lifter_finger_servo;
	Servo right_lifter_finger_servo;
	
	public Lifter( Controller lifting_controller ) {
		this.lifting_controller = lifting_controller;
		
		lifterA = new Talon(14);
		lifterB = new Talon(15);
		lifterA.setInverted(true);
		lifterB.setInverted(true);
		
		left_lifter_finger_servo = new Servo( 12 );//was 12.
    	right_lifter_finger_servo = new Servo( 11 );
    	
    	left_lifter_finger_servo.set( left_lifter_finger_servo_open );
    	right_lifter_finger_servo.set( right_lifter_finger_servo_open );
	}

	public void update( double dt ) {
		
		if ( lifting_controller.get_left_bumper() && leftFingerServoFlag ) {
			left_lifter_finger_servo.set( left_lifter_finger_servo_close );
			leftFingerServoFlag = false;
		} 
		else if ( !lifting_controller.get_left_bumper() && !leftFingerServoFlag ) {
			left_lifter_finger_servo.set( left_lifter_finger_servo_open );
			leftFingerServoFlag = true;
		}
		
		if ( lifting_controller.get_right_bumper() && rightFingerServoFlag ) {
			right_lifter_finger_servo.set( right_lifter_finger_servo_close );
			rightFingerServoFlag = false;
		} 
		else if ( !lifting_controller.get_right_bumper() && !rightFingerServoFlag ) {
			right_lifter_finger_servo.set( right_lifter_finger_servo_open );
			rightFingerServoFlag = true;
		}
		
		
		if ( lifting_controller.get_b_button() ) {
			lifterA.set( 0.2 );
			lifterB.set( 0.2 );
		}
		else if( lifting_controller.get_a_button() ) {
			lifterA.set( 1.0 );
			lifterB.set( 1.0 );
		} 
		else if ( lifting_controller.get_x_button() ) {
			lifterA.set( 0.7 );
			lifterB.set( 0.7 );
		} 
		else if( lifting_controller.get_y_button() ) {
			lifterA.set( -0.7 );
			lifterB.set( -0.7 );
		}
		else {
			lifterA.set( 0.0 );
			lifterB.set( 0.0 );
		}
		
		
//		switch( state ) {
//		
//			case close:
//				System.out.println( "Close" );
//				left_lifter_finger_servo.set( left_lifter_finger_servo_close );
//				right_lifter_finger_servo.set( right_lifter_finger_servo_close );
//				//locking_servo.set( locking_servo_open );
//				state = lifter_state.close_wait;
//				time_accumulator = 0.0; 
//				break;
//				
//			case close_wait:
//				time_accumulator += dt;
//				if( time_accumulator > 1.2 ) {	//Change Value to Servo Closing time plus a bit
//					state = lifter_state.lock;
//				}
//				break;
//				
//			case lock:
//				System.out.println( "Lock" );
//				//locking_servo.set( locking_servo_close );
//				state = lifter_state.lock_wait;
//				time_accumulator = 0.0; 
//				break;
//				
//			case lock_wait:
//				time_accumulator += dt;
//				if( time_accumulator > 2.4 ) {	//Change Value to Servo Closing time plus a bit
//					left_lifter_finger_servo.set( left_lifter_finger_servo_open );
//			    	right_lifter_finger_servo.set( right_lifter_finger_servo_open );
//					state = lifter_state.climb;
//				}
//				break;
//				
//			case climb: 
//				if( this.lifting_controller.get_b_button() ) {
//					lifter.set( -1.0 );
//				} else if ( this.lifting_controller.get_x_button() ) {
//					lifter.set( -0.6 );
//				} else {
//					lifter.set( 0.0 );
//				}
//				
//				if( this.lifting_controller.get_y_button() ) {
//					left_lifter_finger_servo.set( left_lifter_finger_servo_open );
//			    	right_lifter_finger_servo.set( right_lifter_finger_servo_open );
//			    	//locking_servo.set( locking_servo_open );
//			    	state = lifter_state.idle;
//				}
//				break;
//				
//			case idle:
//				if( this.lifting_controller.get_a_button() ) {
//					state = lifter_state.close;
//				}
//				if ( this.lifting_controller.get_x_button() ) { // unwind
//					lifter.set( 0.25 );
//				} else if ( this.lifting_controller.get_b_button() ) {
//					lifter.set( -0.25 );
//				} else {
//					lifter.set( 0.0 );
//				}
//				break;
//		}//End of state
	}
}
