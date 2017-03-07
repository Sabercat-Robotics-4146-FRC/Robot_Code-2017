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
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.CANSpeedController;

import com.ctre.CANTalon;
import com.ctre.CANTalon.*;

import org.usfirst.frc.team4146.robot.PID.*;

public class Robot extends SampleRobot {
	
	// Servo state machine
	enum servo_state {
		extending,
		retracting
	}
	enum gear_state {
		out,
		in
	}
	// Robot State Machine
	enum robot_state {
		shooting,
		testing_shooter,
		intaking,
		gear_tracking,
		sicem, // Growl mode
		idle
	}
	
	// Shooter RPM parameters
	static double shooter_rpm_tolerance = 10;
	static double shooter_rpm_setpoint  = -2200.0;
	static double shooter_intake_speed  = -0.4;
	Controller drive_controller;
	Controller lifter_controller;
	
	
	// Motor Controller init
	Talon front_left;
	Talon rear_left;
	
	Talon front_right;
	Talon rear_right;
	
	Talon ball_intake;
	Talon shooter_intake;
	Talon vibrator;
		
	CANTalon master_shooter;
	CANTalon slave_shooter;
	
	RobotDrive drive;
	
	AHRS gyro;
	
	//Heading robotHeading;
	
	Encoder right_drive_encoder;
	Encoder left_drive_encoder;
	Ramp_Drive smooth_drive;
	Move_Distance robotMove;
	
	Servo linear_servo;
	Servo gear_servo;
	
	NetworkTable network_table;
	
	Vision gear_vision;
	
	Lifter big_lifter;
	
    public Robot() {
    	gyro = new AHRS( SPI.Port.kMXP );
    	//Initialize network tables
    	network_table = NetworkTable.getTable( "SmartDashboard" );
    	
    	//Initialize Vision Processing
    	gear_vision = new Vision( "gear", network_table );
    	
    	//Controller Initialization 
    	drive_controller = new Controller( 0 );
    	lifter_controller = new Controller( 1 );
    	
    	//Initializing Lifter Process.
    	big_lifter = new Lifter( lifter_controller );
    	
    	//Talon SR Initialization 
    	front_left  	= new Talon( 0 );
    	rear_left   	= new Talon( 1 );
    	
    	front_right 	= new Talon( 2 );
    	rear_right 		= new Talon( 3 );
    	
    	ball_intake	 	= new Talon( 4 );
    	shooter_intake 	= new Talon( 5 );
    	
    	vibrator		= new Talon( 6 );
    	
    	
    	
    	//Talon SRX Initialization 
    	master_shooter 	= new CANTalon( 0 );
    	slave_shooter 	= new CANTalon( 1 );
    		
    	master_shooter.setFeedbackDevice( FeedbackDevice.CtreMagEncoder_Relative );
    	master_shooter.reverseSensor(false);
    		
    	master_shooter.configNominalOutputVoltage(+0.0f, -0.0f);
    	master_shooter.configPeakOutputVoltage(+12.0f, -12.0f);
    	
    	master_shooter.setProfile( 0 );
    	master_shooter.changeControlMode( TalonControlMode.Speed );
//    	master_shooter.setF( 0.0 ); // was 0.1097
//        master_shooter.setP( 0.0 ); // was 0.22
//        master_shooter.setI( 0.0002 ); // was 0
//        master_shooter.setD( 0.01 );
    		
    	//Setting slave_talon 
    	slave_shooter.changeControlMode( CANTalon.TalonControlMode.Follower );
    	slave_shooter.set( master_shooter.getDeviceID() );
    	
    	// Instantiate robot's drive with Talons
    	drive = new RobotDrive( front_left, rear_left, front_right, rear_right );
    	smooth_drive = new Ramp_Drive( drive_controller, drive );
    		
    	linear_servo = new Servo( 10 );
    	gear_servo = new Servo( 8 );
    	
    	right_drive_encoder = new Encoder( 0, 1, false, Encoder.EncodingType.k4X );
    	left_drive_encoder = new Encoder( 2, 3, true, Encoder.EncodingType.k4X );
    }
    
    public void robotInit() {
    	front_left.setSafetyEnabled(false);
		rear_left.setSafetyEnabled(false);
		front_right.setSafetyEnabled(false);
		rear_right.setSafetyEnabled(false);
		right_drive_encoder.reset();
		left_drive_encoder.reset();
    }
	
    public void autonomous() {
    	//Autonomous auto = new Autonomous(robotHeading, robotMove, drive);
//    	auto.turn_to_angle( 90.0, 10.0 );
    	
    }
    
    
    double time_accumulator = 0.0;
    servo_state linear_servo_state = servo_state.extending;
    
    public void oscillate_servo () {
    	// Linear servo 
    	if( time_accumulator > 5.5 ) {
			switch ( linear_servo_state ) {
				case extending:
					linear_servo.set( 0.2 );
					time_accumulator = 0.0;
					linear_servo_state = servo_state.retracting;
					break;
				case retracting:
					linear_servo.set( 0.7 );
					time_accumulator = 0.0;
					linear_servo_state = servo_state.extending;
					break;
				default:
					System.out.println( "Defaulted in linear_servo_state!" );
					break;
			}
		}
    }
    public void operatorControl() {
    	double dt;
    	boolean x_button_toggle = true;
    	
    	Iterative_Timer timer = new Iterative_Timer();
    	timer.reset();
    	
    	robot_state state = robot_state.idle;
    	gear_state gear = gear_state.in;
    	
    	// Resets the servo in the beginning of Operator Control
    	if ( linear_servo.get() >= 0.5 ) {
    		linear_servo.set( 0.2 );
    	} else {
    		linear_servo.set( 0.9 );
    	}
    	
    	double forward_torque;
    	double spin_torque;
    	//gear_servo.set( 0.2 );
    	// out = 0.45
    	//  in = 0.2
    	while ( isOperatorControl() && isEnabled() ) {
    		
    		timer.update();
    		dt = timer.get_dt();
    		gear_vision.update( dt );
    		big_lifter.update( dt );
    		forward_torque = smooth_drive.ramp_drive( dt );
    		spin_torque = -1 * drive_controller.get_deadband_right_x_axis();
    		
    		time_accumulator += dt;
    		network_table.putNumber( "Right_Encoder", right_drive_encoder.getRaw() );
    		network_table.putNumber( "Left_Encoder", left_drive_encoder.getRaw() );
    		network_table.putNumber( "Fused_Heading", gyro.getFusedHeading());
    		double testing = right_drive_encoder.getRaw();
//    		System.out.println( testing );
    		network_table.putNumber( "New_Right_Encoder", testing );
    		// Check button inputs and change state 
    		if ( drive_controller.get_right_trigger() ) { // Shoot with right trigger,
    			state = robot_state.shooting;
    		} else if ( drive_controller.get_left_trigger() ) { // Ball intake with left trigger.
    			state = robot_state.intaking;
    		} else if ( drive_controller.get_b_button() ) { // Test shooter at full speed with B button,
    			//state = robot_state.testing_shooter;
    		} else if ( drive_controller.get_left_bumper() ) {
    			state = robot_state.gear_tracking;
    		} else if ( drive_controller.get_right_bumper() ) {
    			state = robot_state.sicem;
    		} else { // Robot Idle State
    			state = robot_state.idle;
    		}
    		
    		// handle gear servo
    		if ( drive_controller.get_x_button() && x_button_toggle ) {
    			x_button_toggle = false;
    			switch ( gear ) {
    				case in:
    					System.out.println( "Moving In!" );
    					gear_servo.set( 0.4 ); // In number
    					break;
    				case out:
    					System.out.println( "Moving Out!" );
    					gear_servo.set( 0.6 ); // Out number
    					break;
    				default:
    					break;
    			}
    			if ( gear == gear_state.in ) {
    				gear = gear_state.out;
    			} else {
    				gear = gear_state.in;
    			}
    		}
    		if ( !drive_controller.get_x_button() ) {
    			x_button_toggle = true;
    		}
    		// Handle States
    		switch ( state ) {
    			case shooting: 
    				master_shooter.enableControl(); // Allow talon internal PID to apply control to the talon
    				master_shooter.changeControlMode(TalonControlMode.Speed);
    				master_shooter.set( shooter_rpm_setpoint );
    				
    				// Network Table debugging
    				network_table.putNumber( "Shooter_RPM",  master_shooter.getSpeed() );
    				network_table.putNumber( "Shooter Error", master_shooter.getSpeed() - master_shooter.getSetpoint() );
    				network_table.putNumber( "Get value", master_shooter.get() );
    				network_table.putNumber( "Motor Output", master_shooter.getOutputVoltage() / master_shooter.getBusVoltage() );
    				
    				
        			ball_intake.set( -0.35 );
        			vibrator.set( 0.7 );
        			oscillate_servo();
        			// Only feed balls to shooter if RPM is within a tolerance.
        			if ( Math.abs( master_shooter.getSpeed() - master_shooter.getSetpoint() ) <= shooter_rpm_tolerance ) {
        				shooter_intake.set( shooter_intake_speed );
        			} else {
        				shooter_intake.set( 0.0 );
        			}
    				break;
    			case sicem:
    				ball_intake.set( -1.0 );
        			vibrator.set( 1.0 );
        			oscillate_servo();
        			
        			master_shooter.changeControlMode(TalonControlMode.Speed);
        			master_shooter.set( -3000 );
        			master_shooter.enableControl();
        			shooter_intake.set( -1.0 );
        			
//        			if ( Math.abs( master_shooter.getSpeed() ) >= 2100 ) {
//        				shooter_intake.set( -1.0 );
//        			}
    				break;
    			case intaking:
    				//ball_intake.set( -1.0 );
    				ball_intake.set( -1.0 );
    				break;
    			case testing_shooter:
    				master_shooter.changeControlMode( TalonControlMode.Speed );
    				master_shooter.set( -2200 );
    				master_shooter.enableControl();
    				System.out.println( master_shooter.getError() );
    				//shooter_intake.set( -0.3 );
    				break;
    			case idle:
    				master_shooter.disableControl();
        			ball_intake.set( 0.0 );
        			shooter_intake.set( 0.0 );
        			vibrator.set( 0.0 );
    				break;
    			case gear_tracking:
    				spin_torque = gear_vision.get();
    				network_table.putNumber( "vision_out", spin_torque );
    				break;
    			default:
    				System.out.println( "Defaulting in robot state!" );
    				break;
    		
    		} // End of state switch
    		
    		//drive.arcadeDrive( drive_controller.get_left_y_axis(), -drive_controller.get_right_x_axis() );
    		drive.arcadeDrive( forward_torque, spin_torque );
    		//drive.arcadeDrive( forward_torque, -1 * drive_controller.get_deadband_right_x_axis() );
    		
    		//System.out.println( master_shooter.getSpeed() );
    	}
}	
    //End of operatorControl

    public void test() {
    	
    	
    	while ( isTest() && isEnabled() ) {
//    		network_table.putNumber( "Right_Encoder", right_drive_encoder.getRaw() );
//    		network_table.putNumber( "Left_Encoder", left_drive_encoder.getRaw() );
//    		drive.arcadeDrive( drive_controller.get_left_y_axis(), -drive_controller.get_right_x_axis() );
    		
    		if ( drive_controller.get_b_button() ){
    			master_shooter.changeControlMode( TalonControlMode.PercentVbus );
        		master_shooter.set( -1.0 );
        		master_shooter.enableControl();
        	} else {
        		master_shooter.set( 0.0 );
        	}
    		
    		if ( drive_controller.get_y_button() ){
    			shooter_intake.set( -0.6);
    		} else {
    			shooter_intake.set( 0.0 );
    		}
    		
    		drive.arcadeDrive( drive_controller.get_left_y_axis(), -drive_controller.get_right_x_axis() );
    	}
    	
    	
    	
    	
//    	StringBuilder sb = new StringBuilder();
//    	int _loops = 0;
//    	while ( isTest() && isEnabled() ) {
//    		double leftYstick = -drive_controller.get_left_y_axis();
//    		double motorOutput = master_shooter.getOutputVoltage() / master_shooter.getBusVoltage();
//    		
//    			sb.append("\tout:");
//    			sb.append(motorOutput);
//    			sb.append("\tspd:");
//    			sb.append(master_shooter.getSpeed());
//    			if(drive_controller.get_a_button())
//    			{
//    				double targetSpeed = leftYstick * 1500.0;
//    				master_shooter.changeControlMode(TalonControlMode.Speed);
//    				master_shooter.set(targetSpeed); /* 1500 RPM in either direction */
//
//    	        	/* append more signals to print when in speed mode. */
//    	            sb.append("\terr:");
//    	            sb.append(master_shooter.getClosedLoopError());
//    	            sb.append("\ttrg:");
//    	            sb.append(targetSpeed);
//    	        } else {
//    	        	/* Percent voltage mode */
//    	        	master_shooter.changeControlMode(TalonControlMode.PercentVbus);
//    	        	master_shooter.set(leftYstick);
//    	        }
//
//    	        if(++_loops >= 10) {
//    	        	_loops = 0;
//    	        	System.out.println(sb.toString());
//    	        }
//    	        sb.setLength(0);
//    			
//    		
//    		
//    		//		SmartDashboard.putNumber("Left Encoder", left_drive_encoder.get());
//    //		SmartDashboard.putNumber("Right Encoder", right_drive_encoder.get());
//  		//drive.arcadeDrive( drive_controller.get_deadband_left_y_axis(), drive_controller.get_deadband_right_x_axis());
//    	}
    }
    //End of test
}
