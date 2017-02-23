package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;

public class Lifting {
	
	enum lifter_servo_state{
		
	}
	
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

	public void update(){
		
		double lifter_speed = lifting_controller.get_y_button() ? -1.0 : 0.0;
		
		lifter.set( lifter_speed );
		
//		if ( lifting_controller.get_y_button() ) {
//			lifter.set( -1.0 );
//		}
//		if ( !lifting_controller.get_y_button() ) {
//			lifter.set( 0.0 );
//		}
		
		/* Write code for the Servos */
		
		
	}
}
