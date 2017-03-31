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

public class Robot_Map {
	/*-------Initial Initilization-------*/
	
	/*-----Global Constants-----*/
	
	final double GEAR_IN = 0.35;
	final double GEAR_OUT = 0.64;
	
	/*Shooter RPM parameters*/
	static double shooter_rpm_tolerance = 10.0; //was 50
	static double shooter_rpm_setpoint  = -2000.0;// In competition set it to: -2700.0
	static double shooter_intake_speed  = -0.8;
	static double vibrator_speed = 0.8;
	
	/*Autonomous Parameters*/
	final double ACCEPTABLE_DISTANCE_ERROR = 0.083; // Used to be 0.083
	final double ACCEPTABLE_ANGLE_ERROR = 1.0; // Used to be 1.0
	final double DEFAULT_TIME_OUT = 5.0;
	final double MAX_MOVE_SPEED = 0.7; //0.7
	final double MAX_TURN_SPEED = 0.7; // 0.7
	final double MAX_HEADING_TURN_SPEED = 0.7; //0.7
	final double HEADING_LOCK_DISTANCE_LOOSEN_THRESHOLD = 4/12;	//Use to be 1 foot
	final double HEADING_LOCK_ANGLE_LOOSEN_THRESHOLD = 0.00625;
	final int WHILE_WAIT_TIME = 1;
	
	/*Lifter Paramaters*/
	final double right_lifter_finger_servo_close = 0.0;	//Fill These with correct values - 0.9
	final double right_lifter_finger_servo_open = 1.0;		//Fill These with correct values
	
	final double left_lifter_finger_servo_close = 1.0;	//Fill These with correct values
	final double left_lifter_finger_servo_open = 0.0;	//Fill These with correct values - 0.99
	
	boolean fingerServoFlag = false;
/*
	-----Enums-----
	
	Linear Servo State Machine
	public enum servo_state { 
		extending,
		retracting
	}
	
	Gear Servo State Machine
	enum gear_state {
		out,
		in
	}
	
	Robot State Machine
	enum robot_state {
		shooting,
		testing_shooter,
		intaking,
		gear_tracking,
		sicem, // Growl mode
		idle
	}
	*/
	/*-----Joystick Controllers-----*/ 
	
	Controller drive_controller;
	Controller lifter_controller;
	
	
	/*-----Motor Controller / Servo initialization-----*/
	
	/*Talon SR Motor Controller initialization*/
	Talon front_left;
	Talon rear_left;
	
	Talon front_right;
	Talon rear_right;
	
	Talon ball_intake;
	Talon shooter_intake;
	
	Talon vibrator;
	
	Talon lifter_motor; //Note: I changed this from `lifter` to fix naming conflicts (perhaps just for now)
		
	/*CANTalon SRX Motor Controller initialization*/
	CANTalon master_shooter;
	CANTalon slave_shooter;
	
	/*Servo initialization*/
	Servo linear_servo;
	Servo gear_servo;
	
	Servo left_lifter_finger_servo;
	Servo right_lifter_finger_servo;
									//Rip Locking Servo
	
	/*-----Sensor and NetworkTable initialization-----*/
	
	/*Navx Gyro initialization*/
	AHRS gyro;
	
	/*Encoder initialization*/
	Encoder right_drive_encoder;
	Encoder left_drive_encoder;
	
	/*NetworkTable initialization*/
	NetworkTable network_table;
	
	
	/*-----Subclass initialization-----*/
	
	/*RobotDrive initialization*/
	RobotDrive drive;
	
	/*Ramp_Drive initialization*/
	Ramp_Drive smooth_drive;
	
	/*Vision initialization*/
	Vision gear_vision;
	
	/*Lifter initialization*/
	Lifter lifter;
	
	/*Heading initialization*/
	Heading heading;
	
	/*Move_Distance initialization*/
	Move_Distance distance;
	
	/*Iterative_Timer initialization*/
	Iterative_Timer timer;
	
	/*Sendable Chooser initialization*/
	SendableChooser chooser; //Sendable chooser allows us to choose the autonomous from smartdashboard!
	
	/*Autonomous initialization*/
	Autonomous auto;
	
	public Robot_Map(){
		/*-------Secondary Initilization-------*/

    	/*-----Joystick Controllers-----*/ 

    	drive_controller = new Controller( 0 );
    	lifter_controller = new Controller( 1 );
    	

    	/*-----Motor Controller / Servo initialization-----*/
    	
    	/*Talon SR Motor Controller initialization*/
    	front_left  	= new Talon( 0 );
    	rear_left   	= new Talon( 1 );
    	
    	front_right 	= new Talon( 2 );
    	rear_right 		= new Talon( 3 );
    	
    	ball_intake	 	= new Talon( 4 );
    	shooter_intake 	= new Talon( 5 );
    	
    	vibrator		= new Talon( 6 );
    	
    	lifter_motor	= new Talon( 7 );
		lifter_motor.setInverted(true);//<-------------------- Why?
    
    	/*CANTalon SRX Motor Controller initialization*/
    	master_shooter 	= new CANTalon( 0 );
    	slave_shooter 	= new CANTalon( 1 );
    		
    	master_shooter.setFeedbackDevice( FeedbackDevice.CtreMagEncoder_Relative );
    	master_shooter.reverseSensor( false );
    		
    	master_shooter.configNominalOutputVoltage( +0.0f, -0.0f );
    	master_shooter.configPeakOutputVoltage( +12.0f, -12.0f );	//One of these might suppose to be 0 //Don't think so but check.
    	
    	master_shooter.setProfile( 0 );
    	master_shooter.changeControlMode( TalonControlMode.Speed );
//    	master_shooter.setF( 0.0 ); 	// was 0.1097
//      master_shooter.setP( 0.0 ); 	// was 0.22
//      master_shooter.setI( 0.0002 ); 	// was 0
//      master_shooter.setD( 0.01 );
    		
    	/*Configuring slave_shooter*/ 
    	slave_shooter.changeControlMode( CANTalon.TalonControlMode.Follower );
    	slave_shooter.set( master_shooter.getDeviceID() );
    	
    	/*Servo  initialization*/
    	linear_servo = new Servo( 10 );
    	gear_servo = new Servo( 8 );
    	
    	left_lifter_finger_servo = new Servo( 12 );//was 12.
    	right_lifter_finger_servo = new Servo( 11 );
    	

    	/*-----Sensor and NetworkTable initialization-----*/
    	
    	/*Navx Gyro initialization*/
    	gyro = new AHRS( SPI.Port.kMXP );
    	
    	/*Encoder initialization*/
    	right_drive_encoder = new Encoder( 8, 9, false, Encoder.EncodingType.k4X );
    	left_drive_encoder = new Encoder( 6, 7, false, Encoder.EncodingType.k4X );
    
    	/*NetworkTable initialization*/
    	network_table = NetworkTable.getTable( "SmartDashboard" );
    	
    	
    	/*-----Subclass initialization-----*/
    	
    	/*RobotDrive initialization*/
    	drive = new RobotDrive( front_left, rear_left, front_right, rear_right ); // Instantiate robot's drive with Talons
    	
    	/*Ramp_Drive initialization*/ 
    	smooth_drive = new Ramp_Drive( drive_controller, drive );
    	
    	/*Vision initialization*/
    	gear_vision = new Vision( "gear", network_table );
    	
    	/*Lifter initialization*/
    	lifter = new Lifter( lifter_controller );
    		
    	/*Heading initialization*/
    	heading = new Heading( gyro );
    	
    	/*Move_Distance initialization*/
    	distance = new Move_Distance( right_drive_encoder, right_drive_encoder );
    	
    	/*Iterative_Timer initialization*/
    	timer = new Iterative_Timer();
    	
    	/*Sendable Chooser initialization*/
    	chooser = new SendableChooser();
    	chooser.addDefault( "Do Nothing", "Do Nothing" );						//Autonomous that does nothing. It is the default
    	chooser.addObject( "Cross Baseline", "Cross Baseline" );				//Only drives forward about 6 feet
    	chooser.addObject( "Gear from Center", "Gear from Center" );			//Delivering gear to center from center	
    	chooser.addObject( "Blue Gear Boiler Side", "Blue Gear Boiler Side" );
    	chooser.addObject( "Blue Gear NOT Boiler Side", "Blue Gear NOT Boiler Side" );
    	chooser.addObject( "Red Gear Boiler Side", "Red Gear Boiler Side" );
    	chooser.addObject( "Red Gear NOT Boiler Side", "Red Gear NOT Boiler Side" );
    	chooser.addObject( "Testing 1", "Testing 1" );
    	chooser.addObject( "Testing 2", "Testing 2" );
    	chooser.addObject( "Testing 3", "Testing 3" );

    	SmartDashboard.putData( "Auto mode", chooser );
    	
    	SmartDashboard_Wrapper dashboard = new SmartDashboard_Wrapper( network_table );
    	
    	/*Autonomous initialization*/
    	auto = new Autonomous( heading, distance, drive, gear_vision);
	/*-------Tertiary Initilization-------*/
    	
    	/*Set SafetyEnabled to false for talons*/   //Without this the RoboRio complains a lot, which caused a crash in a qualification match.
    	front_left.setSafetyEnabled( false );
		rear_left.setSafetyEnabled( false );
		front_right.setSafetyEnabled( false );
		rear_right.setSafetyEnabled( false );
		drive.setSafetyEnabled( false );
		
		/*Reset Encoders*/
		right_drive_encoder.reset();
		left_drive_encoder.reset();

		/*PID Values*/  //We should add variables at the top for these.
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
		
		/*Get Mechanical Things Ready*/ 
		gear_servo.set( GEAR_IN );
		
		left_lifter_finger_servo.set( left_lifter_finger_servo_open );
    	right_lifter_finger_servo.set( right_lifter_finger_servo_open );
		
	}

}
