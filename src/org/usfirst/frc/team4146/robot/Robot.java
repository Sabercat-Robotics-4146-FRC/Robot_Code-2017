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
	
	Heading robotHeading;
	
	Encoder right_drive_encoder;
	Encoder left_drive_encoder;

	Move_Distance robotMove;
	
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
    		
    		robotHeading = new Heading( gyro );
    		
    		robotMove = new Move_Distance( right_drive_encoder, left_drive_encoder );
    		
    	} catch (RuntimeException ex ) {
    		DriverStation.reportWarning("Problem instantiating: " + ex.getMessage(), true);
    	}
    }
    
    public void robotInit() {
    	
    	front_left.setSafetyEnabled(false);
		rear_left.setSafetyEnabled(false);
		front_right.setSafetyEnabled(false);
		rear_right.setSafetyEnabled(false);
    	
		gyro.reset();
		

		
		//robotHeading.set_vars(0.005, 0.0, 0.0, 0.0);// p, i, d, setPoint
		
		//robotMove.set_vars(0.005, 0.0, 0.0, 0.0);// p, i, d, setPoint
    }
	
    public void autonomous() {
    	
    	Autonomous auto = new Autonomous(robotHeading, robotMove, drive);
    	auto.autoRun();
    }
    
    public void operatorControl() {
    	
    	Ramp_Drive dTrain = new Ramp_Drive( drive_controller, drive );    	
    	Iterative_Timer opControlDt = new Iterative_Timer();
    	
    	while ( isOperatorControl() && isEnabled() ) {
    		opControlDt.update();
    		dTrain.ramp_drive(opControlDt.getDt());
    		Timer.delay( 0.005 );// possibly Useless
    	}
    }	
    //End of operatorControl

    public void test() {
    	PID_Tuning pidTune = new PID_Tuning(robotHeading, drive_controller, gyro);
    	
    	while ( isTest() && isEnabled() ) {
    		
    		pidTune.checkButtons();
    		drive.arcadeDrive( drive_controller.get_deadband_left_y_axis(), -pidTune.getTurnism()  );
    		Timer.delay( 0.005 );// possibly Useless

    		
    	}
    }
    //End of test
}
