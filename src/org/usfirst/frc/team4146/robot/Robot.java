package org.usfirst.frc.team4146.robot;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.networktables.*;

import org.usfirst.frc.team4146.robot.PID.*;

public class Robot extends SampleRobot {
	
	Controller drive_controller;
	
	Talon front_left;
	Talon rear_left;
	Talon front_right;
	Talon rear_right;
	
	RobotDrive drive;
	
	AHRS gyro;
	
	Heading robotHeading;
	
	Encoder right_drive_encoder;
	Encoder left_drive_encoder;
	Ramp_Drive smooth_drive;
	Move_Distance robotMove;
	
    public Robot() {
    	//try {
    		drive_controller = new Controller( 0 );
    	
    		front_left  = new Talon( 5 );
    		rear_left   = new Talon( 6 );
    		front_right = new Talon( 7 );
    		rear_right  = new Talon( 0 );
    		
    		// Instantiate robot's drive with Talons
    		drive = new RobotDrive( front_left, rear_left, front_right, rear_right );
    		smooth_drive = new Ramp_Drive( drive_controller, drive );
    		gyro = new AHRS( SPI.Port.kMXP );
    		
    		robotHeading = new Heading( gyro );
    		
    		left_drive_encoder = new Encoder( 0 , 1 );
    		right_drive_encoder = new Encoder( 2 , 3 );
    		
    		left_drive_encoder.setReverseDirection(true);
    		right_drive_encoder.setReverseDirection(false);
    		
    		robotMove = new Move_Distance( right_drive_encoder, left_drive_encoder );
    }
    
    public void robotInit() {
    	front_left.setSafetyEnabled(false);
		rear_left.setSafetyEnabled(false);
		front_right.setSafetyEnabled(false);
		rear_right.setSafetyEnabled(false);
    	
		
		
		gyro.reset();
    }
	
    public void autonomous() {
    	Autonomous auto = new Autonomous(robotHeading, robotMove, drive);
//    	auto.turn_to_angle( 90.0, 10.0 );
    }
    
    public void operatorControl() {
    	double dt;
    	Iterative_Timer timer = new Iterative_Timer();
    	timer.reset();
    	Heading heading = new Heading( gyro );
    	heading.set_vars( 0.08, 0.0, 0.0 );
    	heading.rel_angle_turn( 90 );
    	
    	
    	Preferences prefs = Preferences.getInstance();
    	String name = "Heading";
    	prefs.putDouble( name + "_p", 0.0 );
		prefs.putDouble( name + "_i", 0.0 );
		prefs.putDouble( name + "_d", 0.0 );
		
    	
       	while ( isOperatorControl() && isEnabled() ) {
    		timer.update();
    		dt = timer.get_dt();
    		
    		
    		if ( drive_controller.get_b_button() ) {
    			double p = prefs.getDouble( name + "_p", 0.0 );
    			double i = prefs.getDouble( name + "_i", 0.0 );
    			double d = prefs.getDouble( name + "_d", 0.0 );
    			
    			heading.set_vars( p, i, d );
    			
    		}
    		
    		SmartDashboard.putNumber("Left Encoder", left_drive_encoder.get());
    		SmartDashboard.putNumber("Right Encoder", right_drive_encoder.get());
    		SmartDashboard.putNumber("Angle", gyro.getFusedHeading() );
    		if( drive_controller.get_a_button() ) {
    			heading.update( dt );
    			SmartDashboard.putNumber("Angle", heading.get());
    			drive.arcadeDrive( 0.0, PID.clamp(heading.get(), 0.5));
    		} else {
    			smooth_drive.ramp_drive( dt );
    		}
    	}
    }	
    //End of operatorControl

    public void test() {
    	while ( isTest() && isEnabled() ) {
    		SmartDashboard.putNumber("Left Encoder", left_drive_encoder.get());
    		SmartDashboard.putNumber("Right Encoder", right_drive_encoder.get());
    		drive.arcadeDrive( drive_controller.get_deadband_left_y_axis(), drive_controller.get_deadband_right_x_axis());
    	}
    }
    //End of test
}
