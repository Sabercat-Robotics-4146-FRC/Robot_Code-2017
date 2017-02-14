package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;

public class Ramp_Drive {
	
	// Constants
	private static final double accelerate_rate = 0.65;
	private static final double decelerate_rate = 1.0;
	private static final double mech_deadband = 0.3;
	private static final double mSlope = ((1.0 - mech_deadband) / (1.0 - Controller.ctrl_deadband));
	private static final double bIntercept = ((-Controller.ctrl_deadband * mSlope) + mech_deadband);
	// Variables
	public double speed = 0.0;
	private double targetSpeed;
	
	private Controller drive_controller;
	private RobotDrive drive;
	
	Ramp_Drive(Controller dc, RobotDrive d) {												//Constructor brings drive controller to get joystick values and drive object to use arcade drive
		drive_controller = dc;
		drive = d;
	}
	
	public void ramp_drive(double dt) { //Given dt, which should come from Iterative_Timer
		double left_y = drive_controller.get_deadband_left_y_axis();						//creates variable left_y which stores the value of the left y axis joystick. 
		
		left_y = check_speed(left_y, dt);														//Main function which does ramping and preliminary checks
		drive.arcadeDrive( speed, -1 * drive_controller.get_deadband_right_x_axis() );		//Sends value 
	  //System.out.printf( "% 5.2f -- % 5.2f -- % 5.2f \n", left_y, targetSpeed, speed );	//Print Values for testing
		//Timer.delay( 0.005 );																//Possibly Useless
	}
	
	public double check_speed(double left_y, double dt ) {
		mechanical_deadband( left_y );														//Method sets speed to outside mechanical deadband speed if it is within deadband and joystick is out of deadband
		targetSpeed = find_target_speed( left_y );
		if((speed > -mech_deadband) && (speed < mech_deadband)) {							//If speed is within the deadband, just set it to zero
			speed = 0.0;
		}
		else if( (targetSpeed > speed) && (targetSpeed > 0) ) {								//Traveling forward but speed is not fast enough
			speed +=  accelerate_rate * dt;
		}
		else if( (targetSpeed < speed) && (targetSpeed < 0) ) {								//Traveling backwards but speed is not fast enough
			speed -= accelerate_rate * dt;
		}
		else if( (targetSpeed < speed) && (targetSpeed >= 0) ) {							//Traveling forwards but speed is too fast
			speed -= decelerate_rate * dt;
		}
		else if( (targetSpeed > speed) && (targetSpeed <= 0) ) {							//Traveling backwards but speed is too fast
			speed +=  decelerate_rate * dt;
		}
		
		return left_y;																		//Return value just for printf statement
	}

	private void mechanical_deadband(double left_y) {										//Does mechanical deadband
		if( (left_y > 0) && (speed == 0.0) ) {
			speed = mech_deadband;
		}
		else if( (left_y < 0) && (speed == 0.0) ) {
			speed = -mech_deadband;
		}
	}
	private double find_target_speed( double left_y ) {										//Does deadband scaling
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
