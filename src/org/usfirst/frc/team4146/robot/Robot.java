
package org.usfirst.frc.team4146.robot;


import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import org.usfirst.frc.team4146.robot.PID.*;


public class Robot extends SampleRobot{
	Controller drive_controller;
	Talon front_left;
	Talon rear_left;
	Talon front_right;
	Talon rear_right;
	RobotDrive drive;
	
	
	AHRS gyro;
	PID heading_pid;
    public Robot() {
    	drive_controller = new Controller( 0 );
    	
    	front_left  = new Talon( 5 );
		rear_left   = new Talon( 6 );
		front_right = new Talon( 7 );
		rear_right  = new Talon( 0 );
		
		front_left.setSafetyEnabled(false);
		rear_left.setSafetyEnabled(false);
		front_right.setSafetyEnabled(false);
		rear_right.setSafetyEnabled(false);
		
		
		gyro = new AHRS( SPI.Port.kMXP );
		gyro.reset();
		
		// Instantiate robot's drive with Talons
		drive      = new RobotDrive( front_left, rear_left, front_right, rear_right );
		heading_pid = new PID( new signal() {
			public double getValue() {
				return gyro.getAngle();
			}
		});
		heading_pid.set_pid( 1, 0, 0 );
		heading_pid.set_setpoint( 0 );
    }
    
    public void robotInit() {
        System.out.println("Hello from robotInit!");
    }
	
    public void autonomous() {
    	
    }
    
    public void operatorControl() {
    	//long last_time = 0;
    	double motor_speed = 0.0;
		double desired_speed = 0.0;
		double step_up = 0.0001;
		double step_down = -0.0001;
		
    	while ( isOperatorControl() && isEnabled() ) {
    		//last_time = System.nanoTime();
			// Reset the gyro with the A button.
//    		if ( drive_controller.get_a_button() ) {
//    			gyro.reset();
//    		}
//    		System.out.println( gyro.getAngle() );
    		// Initiate goto angle with left trigger.
//    		if ( drive_controller.get_left_trigger() ) {
//    			System.out.println( heading_pid.get() );
//    			drive.arcadeDrive( 0, -1 * heading_pid.get() );
//    		} else {
    			desired_speed = drive_controller.get_left_y_axis();
    			if( motor_speed <  desired_speed ){
    				motor_speed += step_up;
    			}else if( motor_speed >  desired_speed ){
    				motor_speed += step_down;
    			}
    			drive.arcadeDrive( -1 * motor_speed, -1 * drive_controller.get_right_x_axis() );
//    		}
    		//heading_pid.update( (double) ( last_time - System.nanoTime() ) * 1e-9 );
    	}
    }// end operatorControl

    public void test() {
    	
    }
}
