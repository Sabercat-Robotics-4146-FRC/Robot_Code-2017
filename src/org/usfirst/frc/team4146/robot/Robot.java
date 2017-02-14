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
    		
    		gyro = new AHRS( SPI.Port.kMXP );
    		
    		robotHeading = new Heading( gyro );
    		
    		left_drive_encoder = new Encoder( 0 , 1 );
    		right_drive_encoder = new Encoder( 2 , 3 );
    		
    		left_drive_encoder.setReverseDirection(true);
    		right_drive_encoder.setReverseDirection(false);
    		
    		robotMove = new Move_Distance( right_drive_encoder, left_drive_encoder );
    		
    	//} catch (RuntimeException ex ) {
    	//	DriverStation.reportWarning("Problem instantiating: " + ex.getMessage(), true);
    	//} 
    }
    
    public void robotInit() {
    	//try {
    	front_left.setSafetyEnabled(false);
		rear_left.setSafetyEnabled(false);
		front_right.setSafetyEnabled(false);
		rear_right.setSafetyEnabled(false);
    	
		gyro.reset();
		

		
		robotHeading.set_vars(0.0, 0.0, 0.0, 0.0);// p, i, d, setPoint
		
		robotMove.set_vars(0.00184, 0.0, 0.0, 0.0);// p, i, d, setPoint
    	//} catch (RuntimeException ex ) {
    		//DriverStation.reportWarning("Problem in init: " + ex.getMessage(), true);
    	//}
    }
	
    public void autonomous() {
    	
    	Autonomous auto = new Autonomous(robotHeading, robotMove, drive);
    	auto.autoRun();
    }
    
    public void operatorControl() {
//    	
//    	Ramp_Drive drive_train = new Ramp_Drive( drive_controller, drive );    	
//    	Iterative_Timer timer = new Iterative_Timer();
//    	NetworkTable table = NetworkTable.getTable( "SmartDashboard" );
//    	PID vision_pid = new PID( new signal() {
//    		public double getValue() {
//    			double x = table.getNumber( "gear_x", 0.0);
//    			if ( x == 0.0 ) {
//    				return 0;
//    			}
//    			return x - 160.0;
//    		}
//    	});
//    	vision_pid.set_pid( 0.008, 0.0, 0.0 );
//    	applyPID plant = new applyPID(){
//			public void apply( double output ) {
//				drive.arcadeDrive( 0.0, output );
//			}
//		};
//    	PID_Tuning vision_tuner = new PID_Tuning( "vis", vision_pid, drive_controller, plant);
//    	Vision gear_vision = new Vision( "gear", table );
//    	
    	while ( isOperatorControl() && isEnabled() ) {
    		SmartDashboard.putNumber("Left Encoder", left_drive_encoder.get());
    		SmartDashboard.putNumber("Right Encoder", right_drive_encoder.get());
    		drive.arcadeDrive( drive_controller.get_deadband_left_y_axis(), drive_controller.get_deadband_right_x_axis());
//    		timer.update();
//    		double dt = timer.getDt();
//    		gear_vision.update( dt );
//    		
//    		if ( drive_controller.get_left_trigger() ) {
//    			drive.arcadeDrive( 0.0, gear_vision.get_center_out() );
//    		}
//    		
//    		if ( drive_controller.get_right_trigger() ) {
//    			robotMove.move_distance(dt);
//    			drive.arcadeDrive( robotMove.move_distance(dt), 0.0 );
//    		}
//    		//vision_pid.update( dt );
//    		vision_tuner.update( dt );
////    		table.putNumber( "D\'", (184 * 5)/ table.getNumber("gear_w",0.0)+4.0 );
//    		table.putNumber( "D\'", 23368/table.getNumber("gear_h",1.0) );
////    		if ( drive_controller.get_a_button() ) {
////    			drive.arcadeDrive( 0.0, vision_pid.get() );
////    		} else {
////    			drive_train.ramp_drive( dt );
////    		}
    	}
    }	
    //End of operatorControl

    public void test() {
//    	PID_Tuning pidTune = new PID_Tuning(robotHeading, drive_controller, gyro);
//    	
    	while ( isTest() && isEnabled() ) {
//    		
    		SmartDashboard.putNumber("Left Encoder", left_drive_encoder.get());
    		SmartDashboard.putNumber("Right Encoder", right_drive_encoder.get());
//    		pidTune.checkButtons();
    		drive.arcadeDrive( drive_controller.get_deadband_left_y_axis(), drive_controller.get_deadband_right_x_axis());
//    		Timer.delay( 0.005 );// possibly Useless
//
//    		
    	}
    }
    //End of test
}
