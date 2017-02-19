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
import edu.wpi.first.wpilibj.Servo;
import org.usfirst.frc.team4146.robot.PID.*;

public class Robot extends SampleRobot {
	enum servo_state {
		extending,
		retracting
	}
	Controller drive_controller;
	
	Talon front_left;
	Talon rear_left;
	Talon front_right;
	Talon rear_right;
	
	RobotDrive drive;
	
	AHRS gyro;
	Servo s;
	Heading robot_heading;
	
	Encoder right_drive_encoder;
	Encoder left_drive_encoder;
	RampDrive smooth_drive;
	MoveDistance robot_move;
	
    public Robot() {
    	//try {
    		drive_controller = new Controller( 0 );
    	
    		front_left  = new Talon( 5 );
    		rear_left   = new Talon( 6 );
    		front_right = new Talon( 7 );
    		rear_right  = new Talon( 0 );
    		
    		// Instantiate robot's drive with Talons
    		drive = new RobotDrive( front_left, rear_left, front_right, rear_right );
    		smooth_drive = new RampDrive( drive_controller);
    		gyro = new AHRS( SPI.Port.kMXP );
    		
    		robot_heading = new Heading( gyro );
    		
    		left_drive_encoder = new Encoder( 0 , 1 );
    		right_drive_encoder = new Encoder( 2 , 3 );
    		
    		left_drive_encoder.setReverseDirection(true);
    		right_drive_encoder.setReverseDirection(false);
    		
    		robot_move = new MoveDistance( right_drive_encoder, left_drive_encoder );
    		s = new Servo( 9 );
    }
    
    public void robotInit() {
    	front_left.setSafetyEnabled(false);
		rear_left.setSafetyEnabled(false);
		front_right.setSafetyEnabled(false);
		rear_right.setSafetyEnabled(false);
    	robot_move.set_pid(0.1, 0.0, 0.0);
		
		
		gyro.reset();
    }
	
    public void autonomous() {
    	Autonomous auto = new Autonomous(robot_heading, robot_move, drive);
    	auto.move_forward(10.0);
    }
    
    public void operatorControl() {
    	double dt;
    	double driveTrainTurn;
    	double driveTrainMove;
    	int servoState = 0;
    	boolean aButtonLastPress = false;
    	boolean flyWheelOn = false;
    	boolean isServoForward = false;
    	boolean isOscillationOn = false;
    	boolean bFlag = true;
    	boolean xFlag = true;
    	boolean yFlag = true;
    	long lastTime = System.nanoTime();
    	long thisTime = System.nanoTime();
    	IterativeTimer timerMain = new IterativeTimer();
    	Heading heading = new Heading( gyro );
    	
    	double time_accumulator = 0;
    	servo_state linear_servo_state = servo_state.extending;
    	
    	while ( isOperatorControl() && isEnabled() ) {
    		timerMain.update();
    		dt = timerMain.get_dt();
    		 
    		smooth_drive.update(dt);
    		
    		driveTrainTurn = drive_controller.get_deadband_right_x_axis(); //Use Heading
    		driveTrainMove = smooth_drive.getSpeed();
    		
    		drive.arcadeDrive( driveTrainMove, driveTrainTurn);
    		
    		flyWheelOn = (( drive_controller.get_a_button() ) && (!(aButtonLastPress))) ? (!flyWheelOn) : (flyWheelOn);
    		aButtonLastPress = drive_controller.get_a_button();
    		
    		if(drive_controller.get_b_button() && bFlag) {
    			bFlag = false;
    			isOscillationOn = false;
    		} else if(!drive_controller.get_b_button()) {
    			bFlag = true;
    		}
    		
    		if(drive_controller.get_x_button() && xFlag) {
    			xFlag = false;
    			isOscillationOn = true;
    		} else if(!drive_controller.get_x_button()) {
    			xFlag = true;
    		}
    		if(drive_controller.get_y_button() && yFlag) {
    			yFlag = false;
    			System.out.println(time_accumulator);
    		} else if(!drive_controller.get_y_button()) {
    			yFlag = true;
    		}
    		time_accumulator += dt;

    		if( true ) {
    			if( time_accumulator > 5.5 ) {
    				switch ( linear_servo_state ) {
    					case extending:
    						s.set( 0.2 );
    						time_accumulator = 0.0;
    						linear_servo_state = servo_state.retracting;
    						break;
    					case retracting:
    						s.set( 0.8 );
    						time_accumulator = 0.0;
    						linear_servo_state = servo_state.extending;
    						break;
    					default:
    						System.out.println( "Defaulted!" );
    						break;
    				}
    			}
    		}
    		
    	}
    	/*
    	
    	
    	
    	Preferences prefs = Preferences.getInstance();
    	String name = "Heading";
    	prefs.putDouble( name + "_p", 0.0 );
		prefs.putDouble( name + "_i", 0.0 );
		prefs.putDouble( name + "_d", 0.0 );
		boolean pressed = true;
    	boolean shooter_button = false;
    	boolean shoot_toggle = false;
       	while ( isOperatorControl() && isEnabled() ) {
    		timer.update();
    		dt = timer.get_dt();
    		//smooth_drive.ramp_drive(dt);
    		if ( drive_controller.get_a_button() && pressed ) {
    			double d = drive_controller.get_right_y_axis();
    			s.set( d );
    			System.out.println( d );
    			pressed = false;
    		} else {
    			pressed = true;
    		}
    		if ( drive_controller.get_b_button() && (!(shooter_button)) ) {
    			shooter_button = true;
    			shoot_toggle = !shoot_toggle;
    		} else if(!(drive_controller.get_b_button())) {
    			shooter_button = false;
    		}
    		
    		if(shoot_toggle) {
    			//shooter code
    		}
    		
    	}*/
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
