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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.CANSpeedController;

import edu.wpi.first.wpilibj.Spark;

import com.ctre.CANTalon;
import com.ctre.CANTalon.*;

import org.usfirst.frc.team4146.robot.PID.*;

public class Robot extends SampleRobot {
	
	//Things to change when not working on practice bot
		//Change to use both encoders
		//Change password back in build.properties
		//Unreverse lifter motor
		//Undeinvert Right Encoder -- So invert it
		//Make sure autonomous has no test code in it.
	/* Robot State Machine Lists */
	
	// Linear Servo State Machine
	enum servo_state {
		extending,
		retracting
	}
	
	// Gear Servo State Machine
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
	
	
	/* Global Constants */
	
	private final double GEAR_IN = 0.35;
	private final double GEAR_OUT = 0.64;
	
	// Shooter RPM parameters
	static double shooter_rpm_tolerance = 10; //was 50
	static double shooter_rpm_setpoint  = -2700.0;
	static double shooter_intake_speed  = -0.8;
	static double vibrator_speed = 0.8;
	
	/* Joystick Controllers */ 
	
	Controller drive_controller;
	Controller lifter_controller;
	
	
	/* Motor Controller initialization */
	
	// Talon SR Motor Controller init
	Talon front_left;
	Talon rear_left;
	
	Talon front_right;
	Talon rear_right;
	
	Talon ball_intake;
	Talon shooter_intake;
	Talon vibrator;
		
	//CANTalon SRX Motor Controller init
	CANTalon master_shooter;
	CANTalon slave_shooter;
	
	//Servo Motor Controller init
	Servo linear_servo;
	Servo gear_servo;
	
	
	/* Sensor and NetworkTable initialization */
	
	//Navx Gyro init
	AHRS gyro;
	
	//Encoder init
	Encoder right_drive_encoder;
	Encoder left_drive_encoder;
	
	//NetworkTable init
	NetworkTable network_table;
	
	
	/* Subclass initialization */
	
	//RobotDrive init
	RobotDrive drive;
	
	//Ramp_Drive init 
	Ramp_Drive smooth_drive;
	
	//Vision init
	Vision gear_vision;
	
	//Lifter init
	Lifter lifter;
	
	//Heading init
	Heading heading;
	
	//Move_Distance init
	Move_Distance distance;
	
	//Sendable Chooser init
	SendableChooser chooser; //Sendable chooser allows us to choose the autonomous from smartdashboard
	
    public Robot() {
    
    	/* Joystick Controllers */ 
    	
    	//Controller Initialization 
    	drive_controller = new Controller( 0 );
    	lifter_controller = new Controller( 1 );
    	

    	/* Motor Controller initialization */
    	
    	// Talon SR Motor Controller init
    	front_left  	= new Talon( 0 );
    	rear_left   	= new Talon( 1 );
    	
    	front_right 	= new Talon( 2 );
    	rear_right 		= new Talon( 3 );
    	
    	ball_intake	 	= new Talon( 4 );
    	shooter_intake 	= new Talon( 5 );
    	
    	vibrator		= new Talon( 6 );
    
    	//CANTalon SRX Motor Controller init
    	master_shooter 	= new CANTalon( 0 );
    	slave_shooter 	= new CANTalon( 1 );
    		
    	master_shooter.setFeedbackDevice( FeedbackDevice.CtreMagEncoder_Relative );
    	master_shooter.reverseSensor( false );
    		
    	master_shooter.configNominalOutputVoltage( +0.0f, -0.0f );
    	master_shooter.configPeakOutputVoltage( +12.0f, -12.0f );	//One of these might suppose to be 0
    	
    	master_shooter.setProfile( 0 );
    	master_shooter.changeControlMode( TalonControlMode.Speed );
//    	master_shooter.setF( 0.0 ); 	// was 0.1097
//      master_shooter.setP( 0.0 ); 	// was 0.22
//      master_shooter.setI( 0.0002 ); 	// was 0
//      master_shooter.setD( 0.01 );
    		
    	//Setting slave_talon 
    	slave_shooter.changeControlMode( CANTalon.TalonControlMode.Follower );
    	slave_shooter.set( master_shooter.getDeviceID() );
    	
    	//Servo Motor Controller init
    	linear_servo = new Servo( 10 );
    	gear_servo = new Servo( 8 );
    	

    	/* Sensor and NetworkTable initialization */
    	
    	//Navx Gyro init
    	gyro = new AHRS( SPI.Port.kMXP );
    	
    	//Encoder init
    	right_drive_encoder = new Encoder( 8, 9, false, Encoder.EncodingType.k4X );
    	left_drive_encoder = new Encoder( 6, 7, false, Encoder.EncodingType.k4X );
    
    	//NetworkTable init
    	network_table = NetworkTable.getTable( "SmartDashboard" );
    	
    	
    	/* Subclass initialization */
    	
    	// Instantiate robot's drive with Talons
    	//RobotDrive init
    	drive = new RobotDrive( front_left, rear_left, front_right, rear_right );
    	
    	
    	//Ramp_Drive init 
    	smooth_drive = new Ramp_Drive( drive_controller, drive );
    	
    	//Vision init
    	gear_vision = new Vision( "gear", network_table );
    	
    	//Lifter init
    	lifter = new Lifter( lifter_controller );
    		
    	//Heading init
    	heading = new Heading( gyro);
    	
    	//Move_Distance init
    	distance = new Move_Distance( right_drive_encoder, right_drive_encoder);
    	
    	chooser = new SendableChooser();
    	chooser.addDefault("Do Nothing", "Do Nothing");						//Autonomous that does nothing. It is the default
    	chooser.addObject("Gear from Center", "Gear from Center");			//Delivering gear to center from center	
    	chooser.addObject("Cross Baseline", "Cross Baseline");				//Only drives forward about 6 feet
    	chooser.addObject("Side Gear on Left", "Side Gear on Left");		
    	chooser.addObject("Side Gear on Right", "Side Gear on Right");
    	chooser.addObject("Testing 1", "Testing 1");
    	chooser.addObject("Testing 2", "Testing 2");
    	chooser.addObject("Testing 3", "Testing 3");

    	SmartDashboard.putData("Auto mode", chooser);
    	
    	SmartDashboard_Wrapper dashboard = new SmartDashboard_Wrapper(network_table);
    }
    
    public void robotInit() {
    	//Set SafetyEnabled to false. Without this the RoboRio complains a lot, which caused a crash in a qualification match.
    	front_left.setSafetyEnabled( false );
		rear_left.setSafetyEnabled( false );
		front_right.setSafetyEnabled( false );
		rear_right.setSafetyEnabled( false );
		drive.setSafetyEnabled(false);
		
		right_drive_encoder.reset();
		left_drive_encoder.reset();

		//heading.set_pid( 0.07, 0.1, 0.0 ); //0.07, 0.1, 0.0 
		Autonomous.set_heading_turn_pid_values( 0.06, 0.25, 0.0 );
		
		
		
		Autonomous.set_heading_move_pid_values( 0.35, 0.022, 0.0 );
		Autonomous.set_loose_heading_move_pid_values( 0.0, 0.02, 0.0 );
		distance.set_pid( 0.7, 2.0, 0.0);
		//0.4, 0.0, 0.0  
		//0.4, 0.1, 0.0 Integral Range of 3, this one crawls a little bit after stopping	
		//0.4, 0.6, 0.0 Integral Range is 2, Overshoots 
		// Fix error stack! 
		// Add the ability to start integral when within a value
		
		gear_servo.set( GEAR_IN );
		
    }
    
    /* 
     * !!!auto.turn() DOES NOT reset angle before turning
     * you must use heading.set_heading()!!!
     * 
     * Commands you can use:
     * 
     * 
     * auto.move_forward(double distanceInFeet, double timeOutInSeconds);//Does not reset heading or use heading at all
     * auto.move_heading_lock(double distanceInFeet, double timeOutInSeconds);
     * auto.turn(double angleInDegrees, double timeOutInSeconds);		   //Turns to relative angle from current setpoint
     * heading.set_heading();// sets current heading as zero
     * Timer.delay(double seconds);
     */
	
    public void autonomous() {
    	Preferences prefs = Preferences.getInstance();
    	
    	Autonomous auto = new Autonomous( heading, distance, drive, gear_vision);
    	distance.reset();
		heading.set_heading();
		network_table.putBoolean( "isAutoComplete", false );
		
		String autoSelected = (String) chooser.getSelected();
		switch(autoSelected)
		{
		case "Do Nothing":
			default:
			break;
			
		case "Gear from Center":
			auto.move_heading_lock( -6.8, 5.0 );
			Timer.delay(0.3);

	    	gear_servo.set( GEAR_OUT );
	    	Timer.delay(0.3);
	    	auto.move_heading_lock(2.0, 2.0);
	    	gear_servo.set( GEAR_IN );
	    	break;
	    	
		case "Cross Baseline":
			auto.move_heading_lock( -7, 15 );	//Is this suppose to be positive?
			break;
			
		case "Side Gear on Left":
			auto.move_heading_lock(-7.5, 8);	//-8.04
			//Timer.delay(0.3);
			auto.turn(60, 7);					//60
			//Timer.delay(0.3);
			auto.move_heading_lock(-1.75, 3);	//-1.75
			break;
			
		case "Side Gear on Right":			
			
			auto.move_heading_lock(-7.5, 8);	//-8.04
			//Timer.delay(0.3);
			auto.turn(-60, 7);					//60
			//Timer.delay(0.3);
			auto.move_heading_lock(-1.75, 5);	//-1.75
			
			break;
			
		case "Testing 1":
			auto.turn(30, 5);
			//auto.move_heading_lock( 10, 10 );
			break;
			
		case "Testing 2":
			auto.turn(60, 6);

			break;
			
		case "Testing 3":
			double moveDis = prefs.getDouble( "_setpoint", 0.0);
			double testP = prefs.getDouble( "testP", 0.0);
			double testI = prefs.getDouble( "testI", 0.0);
			double testD = prefs.getDouble( "testD", 0.0);
			Autonomous.set_heading_move_pid_values( testP, testI, testD );
			
			double looseP = prefs.getDouble( "looseP", 0.0);
			double looseI = prefs.getDouble( "looseI", 0.0);
			double looseD = prefs.getDouble( "looseD", 0.0);
			Autonomous.set_loose_heading_move_pid_values( looseP, looseI, looseD );
			//distance.set_pid( testP, testI, testD );
			System.out.println("move distance: " + moveDis);
			auto.move_heading_lock(moveDis, 15);

			break;
		}
		/* Begin auto */
    
    	//auto.turn( 90.0, 5.0 );
    	
    	
//    	Timer.delay(4.0);
    	//auto.move_heading_lock(12, 10.0);

    	/* End auto */
		network_table.putBoolean( "isAutoComplete", true );
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
    	gear_state gear = gear_state.out; //Should be first state to run since gear servo starts closed
    	
    	
    	// Resets the servo in the beginning of Operator Control
    	if ( linear_servo.get() >= 0.5 ) {
    		linear_servo.set( 0.2 );
    	} else {
    		linear_servo.set( 0.9 );
    	}
    	
//    	double forward_torque;
    	double spin_torque;
    	//gear_servo.set( 0.2 );
    	// out = 0.45
    	//  in = 0.2
    	while ( isOperatorControl() && isEnabled() ) {
    		
    		timer.update();
    		dt = timer.get_dt();
    		gear_vision.update( dt );
    		lifter.update( dt );
//    		forward_torque = smooth_drive.ramp_drive( dt );
    		spin_torque = -1 * drive_controller.get_deadband_right_x_axis();
    		
//    		network_table.putNumber( "Forward_Torque", forward_torque );
    		network_table.putNumber( "Spin_Torque", spin_torque );
    		
    		time_accumulator += dt;
    		network_table.putNumber( "Right_Encoder", right_drive_encoder.getRaw() );
    		network_table.putNumber( "Left_Encoder", left_drive_encoder.getRaw() );
    		network_table.putNumber( "Fused_Heading", gyro.getFusedHeading() );
//    		double testing = right_drive_encoder.getRaw();
//    		System.out.println( testing );
//    		network_table.putNumber( "New_Right_Encoder", testing );
    		// Check button inputs and change state 
    		if ( drive_controller.get_right_trigger() ) { // Shoot with right trigger,
    			state = robot_state.shooting;
    		} else if ( drive_controller.get_left_trigger() ) { // Ball intake with left trigger.
    			state = robot_state.intaking;
    		} else if ( drive_controller.get_b_button() ) { // Test shooter at full speed with B button,
    			state = robot_state.testing_shooter;
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
    					System.out.println( "Moving In! / Closing" );
    					gear_servo.set( GEAR_IN ); // In number
    					break;
    				case out:
    					System.out.println( "Moving Out! / Opening" );
    					gear_servo.set( GEAR_OUT ); // Out number
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
    				network_table.putNumber( "Closed_Loop_Error", master_shooter.getClosedLoopError() );
    				
        			ball_intake.set( -0.3 );
        			vibrator.set( vibrator_speed );
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
        			vibrator.set( 0.9 );
        			oscillate_servo();
        			
        			master_shooter.changeControlMode( TalonControlMode.Speed );
        			master_shooter.set( -3000 );
        			master_shooter.enableControl();
        			
        			if ( Math.abs( master_shooter.getSpeed() ) >= 2900 ) {
        				shooter_intake.set( -1.0 );
        			}
    				break;
    			case intaking:
    				ball_intake.set( -1.0 );
    				break;
    			case testing_shooter:
//    				master_shooter.changeControlMode( TalonControlMode.Speed );
//    				master_shooter.set( -2200 );
//    				master_shooter.enableControl();
//    				System.out.println( master_shooter.getError() );
    				//shooter_intake.set( -0.3 );
    				//shooter_intake.set( -0.6 );
    				vibrator.set( 0.6 );
    				shooter_intake.set(-1.0);
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
    		drive.arcadeDrive( drive_controller.get_deadband_left_y_axis(), spin_torque );
    		//drive.arcadeDrive( forward_torque, -1 * drive_controller.get_deadband_right_x_axis() );
    		//drive.arcadeDrive(  drive_controller.get_deadband_left_y_axis(), -1 * drive_controller.get_deadband_right_x_axis() );
    		
    		//System.out.println( master_shooter.getSpeed() );
    	}
}	
    //End of operatorControl

    public void test() {
    	Spark motor = new Spark( 9 );
    
    
    	Iterative_Timer timer = new Iterative_Timer();
    	timer.reset();
    	double dt;
    	heading.set_heading();
    	while ( isTest() && isEnabled() ) {
    		timer.update();
    		dt = timer.get_dt();
    		
    		if ( drive_controller.get_a_button() ) {
    			motor.set( 0.5 );
    		} else if ( drive_controller.get_b_button() ) {
    			motor.set( -0.5 );
    		} else {
    			motor.set( 0.0 );
    		}
    	}
    		
    }
    //End of test
}
