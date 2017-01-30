package org.usfirst.frc.team4146.robot;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team4146.robot.PID.*;

public class Robot extends SampleRobot {
	
	Controller drive_controller;
	Talon front_left;
	Talon rear_left;
	Talon front_right;
	Talon rear_right;
	RobotDrive drive;
	
	AHRS gyro;
	PID heading_pid;
	
    public Robot() {
    	try {
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
    		drive = new RobotDrive( front_left, rear_left, front_right, rear_right );
		
    		heading_pid = new PID( new signal() {
    			public double getValue() {
    				return gyro.getAngle();
    			}
    		});
    		heading_pid.set_pid( 1, 0, 0 );
    		heading_pid.set_setpoint( 0 );
    	} catch (RuntimeException ex ) {
    		DriverStation.reportError("Error instantiating: " + ex.getMessage(), true);
    	}
    }
    
    public void robotInit() {
        
    }
	
    public void autonomous() {
    	
    }
    
    public void operatorControl() {
    	
    	Ramp_Drive dTrain = new Ramp_Drive( drive_controller, drive );
    	
    	while ( isOperatorControl() && isEnabled() ) {
    		dTrain.ramp_drive();
    	}
    }// end operatorControl

    public void test() {
    	
    }
}
