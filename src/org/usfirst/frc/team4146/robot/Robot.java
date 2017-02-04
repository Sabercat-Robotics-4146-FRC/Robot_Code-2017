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

import org.usfirst.frc.team4146.robot.PID.*;

public class Robot extends SampleRobot {
	
	Controller drive_controller;
	
	Talon front_left;
	Talon rear_left;
	Talon front_right;
	Talon rear_right;
	
	RobotDrive drive;
	
	AHRS gyro;
	
	Encoder right_drive_encoder;
	Encoder left_drive_encoder;
	
    public Robot() {
    	try {
    		drive_controller = new Controller( 0 );
    	
    		front_left  = new Talon( 5 );
    		rear_left   = new Talon( 6 );
    		front_right = new Talon( 7 );
    		rear_right  = new Talon( 0 );
    		
    		// Instantiate robot's drive with Talons
    		drive = new RobotDrive( front_left, rear_left, front_right, rear_right );
    		
    		gyro = new AHRS( SPI.Port.kMXP );
    		
    	} catch (RuntimeException ex ) {
    		DriverStation.reportWarning("Error instantiating: " + ex.getMessage(), true);
    	}
    }
    
    public void robotInit() {
    	
    	front_left.setSafetyEnabled(false);
		rear_left.setSafetyEnabled(false);
		front_right.setSafetyEnabled(false);
		rear_right.setSafetyEnabled(false);
    	
		gyro.reset();
		
		//robotHeading.set_vars(0.5, 0.0, 0.0, 0.0);// p, i, d, setPoint
		
		//robotMove.set_vars(0.5, 0.0, 0.0, 0.0);// p, i, d, setPoint
    }
	
    public void autonomous() {
    	
    }
    
    public void operatorControl() {
    	
    	//Ramp_Drive dTrain = new Ramp_Drive( drive_controller, drive );    	
    	Heading robotHeading;
    	//Move_Distance robotMove;
    	

    	
    	robotHeading = new Heading( gyro );
    	
    	//robotMove = new Move_Distance( right_drive_encoder, left_drive_encoder );

    	
    	Preferences prefs = Preferences.getInstance();
    	double p_pref = 0.0;
    	double i_pref = 0.0;
    	double d_pref = 0.0;
    	double turn_angle = 30.0;
    	double turnism = 0.0;
    	
    	boolean aLast = true;
    	boolean bLast = true;
    	boolean yLast = true;
    	while ( isOperatorControl() && isEnabled() ) {
    		
    		//dTrain.ramp_drive();
    		
    		if(drive_controller.get_a_button() && aLast) {
    			robotHeading.set_heading();
    			aLast = false;
    		}
    		else if( !drive_controller.get_a_button() ) {
    			aLast = true;
    		}
    		
    		if(drive_controller.get_b_button() && bLast) {
//    			robotHeading.rel_angle_turn(-drive_controller.get_deadband_right_x_axis() * 180);
    			robotHeading.rel_angle_turn(turn_angle);

    			bLast = false;
    		}
    		else if( !drive_controller.get_b_button() ) {
    			bLast = true;
    		}
    		

    		if(drive_controller.get_y_button() && yLast) {
    			p_pref = prefs.getDouble("PValue", 0.0);
    			i_pref = prefs.getDouble("IValue", 0.0);
    			d_pref = prefs.getDouble("DValue", 0.0);
    			turn_angle = prefs.getDouble("TurnAngle", 0.0);
    			robotHeading.set_vars(p_pref, i_pref, d_pref, 0.0);
    			SmartDashboard.putNumber("P", p_pref);
    			SmartDashboard.putNumber("I", i_pref);
    			SmartDashboard.putNumber("D", d_pref);
    			SmartDashboard.putNumber("turnAngle", turn_angle);
    			robotHeading.set_heading();
    			yLast = false;
    		}
    		else if( !drive_controller.get_y_button() ) {
    			yLast = true;
    		}
    		
    		turnism = robotHeading.heading();

			SmartDashboard.putNumber("gyroFusedHeading", gyro.getFusedHeading());
			SmartDashboard.putNumber("HeadingOutput", turnism);

			
    		drive.arcadeDrive( drive_controller.get_deadband_left_y_axis(), -turnism  );
    		Timer.delay( 0.005 );// possibly Useless

    		
    	}
    }// end operatorControl

    public void test() {
    	
    }
}
