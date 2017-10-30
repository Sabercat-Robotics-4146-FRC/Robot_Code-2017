package org.usfirst.frc.team4146.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotMap {
	
	// Constants
	
	public static Robot ROBOT;
	/*Shooter parameters*/
	public static final double SHOOTER_RPM_TOLERANCE = 10.0; //was 50 //10.0
	public static final double SHOOTER_RPM_SETPOINT  = -2700.0;// In competition set it to: -2700.0
	public static final double SHOOTER_INTAKE_SPEED  = -0.8;
	public static final double VIBRATOR_SPEED = 0.8;
	
	//Gear Constants
	public static final double TILT_UPPER_STOP = 0.71; //was .7 then .74
	public static final double TILT_LOWER_STOP = 0.45; // was .4 .45
	public static final double TILLT_UP_POWER = 0.46;
	public static final double TILT_DOWN_POWER = -0.1;
	public static final double TILT_STALL_TORQUE = 0.1; //.1
	public static final double GEAR_CATCH_SPEED = 0.6;
	public static final double GEAR_RELEASE_SPEED = -0.5;
	public static final double GEAR_HOLD_SPEED = 0.2;
	
	// Heading Constants
	// Ku = 0.15
	// Tu = 1
//	public static final double HEADING_KU = 0.15; // 0.15
//	public static final double HEADING_TU = .8; // 0.8
	public static final double HEADING_TURN_KP = 0.06; // HEADING_KU * 0.6
	public static final double HEADING_TURN_KI = 0.25; // HEADING_TU/2
	public static final double HEADING_TURN_KD = 0.0;  // HEADING_TU/8
	public static final double HEADING_TURN_INTEGRAL_ACTIVATION_RANGE = 0.0;
	
	public static final double HEADING_LOCK_KP = 0.25; // HEADING_KU * 0.6
	public static final double HEADING_LOCK_KI = 0.022; // HEADING_TU/2
	public static final double HEADING_LOCK_KD = 0.0;  // HEADING_TU/8
	
	// MoveDistance Constants
	public static final double MoveDistance_KP = 0.7;
	public static final double MoveDistance_KI = 2.0;
	public static final double MoveDistance_KD = 0.0;
	
	//PID Constants
	public static final double ACCEPTABLE_ANGLE_ERROR = 0.5;
	public static final double ACCEPTABLE_DISTANCE_ERROR = 0.083;
	
	//Autonomous Constants
	public static final double MAX_TURN_SPEED = 0.7;
	public static final double MAX_MOVE_SPEED = 0.8;
	public static final double AUTO_SHOOT_SPEED = -2700.0;
	
	////// Declarations //////
	public static Controller driveController;
	public static Controller lifterController;
	
	// Motor Controllers Declaration
	public static Talon frontLeft;
	public static Talon rearLeft;
	public static Talon frontRight;
	public static Talon rearRight;
	public static Talon ballIntake;
	public static Talon shooterIntake;
	public static Talon vibrator;
	public static Talon gearTilt;
	public static Talon gearWheelLeft;
	public static Talon gearWheelRight;
	public static Talon climberA;
	public static Talon climberB;
	
	public static CANTalon masterShooter;
	public static CANTalon slaveShooter;
	
	// Servos Declaration
	public static Servo linearServo;
	
	//hferjkekjfejrkfn Declaration ask Jacob
	public static AnalogPotentiometer pot;
	
	//Limit Switch Declaration
	static DigitalInput limitSwitch;
	
	// Navax Gyro Declaration
	public static AHRS gyro;
	
	// Encoders Declaration
	public static Encoder rightDriveEncoder;
	public static Encoder leftDriveEncoder;
	
	// Network Table Declaration
	public static NetworkTable networkTable;
	
	// Robot Drive Declaration
	public static RobotDrive drive;
	
	// Vision Declaration
	public static Vision Vision;
	
	// Climber Declaration
	public static Climber Climber;
	
	// Gear Assembly Declaration
	public static GearAssembly GearAssembly;
	
	// Shooter Assembly Declaration
	public static ShooterAssembly ShooterAssembly;
	
	// Heading Declaration
	public static Heading Heading;
	
	// Move Distance Declaration
	public static MoveDistance MoveDistance;
	
	// Sendable Chooser Declaration
	public static SendableChooser chooser; //Sendable chooser allows us to choose the autonomous from smartdashboard
	
	// Autonomous Declaration
	public static Autonomous auto;
	
	public static void init() { // This is to be called in robitInit and instantiates stuff.
		
		// Controllers Initialization
    	driveController = new Controller(0);
    	lifterController = new Controller(1);
    	
    	// Motor Controllers Initialization
    	frontLeft = new Talon(0);
    	rearLeft = new Talon(1);
    	frontRight = new Talon(2);
    	rearRight = new Talon(3);
    	ballIntake = new Talon(4);
    	shooterIntake = new Talon(5);
    	vibrator = new Talon(6);
    	gearTilt = new Talon(11);
    	gearWheelLeft = new Talon(12);
    	gearWheelRight = new Talon(13);
    	gearWheelRight.setInverted(true); 
    	// Inverted so we can give them the same value and turn in on each other.
    	climberA = new Talon(14);
    	climberB = new Talon(15);
    	climberA.setInverted(true);
    	climberB.setInverted(true);
    	
    	frontLeft.setSafetyEnabled(false);
    	rearLeft.setSafetyEnabled(false);
    	frontRight.setSafetyEnabled(false);
    	rearRight.setSafetyEnabled(false);
    	
    	masterShooter = new CANTalon(0);
    	slaveShooter = new CANTalon(1);
    	
    	masterShooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	masterShooter.reverseSensor(false);
    		
    	masterShooter.configNominalOutputVoltage(+0.0f, -0.0f);
    	masterShooter.configPeakOutputVoltage(+12.0f, -12.0f);	//One of these might suppose to be 0
    	
    	masterShooter.setProfile(0);
    	masterShooter.changeControlMode(TalonControlMode.Speed);
    		
    	//Setting slave_talon 
    	slaveShooter.changeControlMode(CANTalon.TalonControlMode.Follower);
    	slaveShooter.set(masterShooter.getDeviceID());
    	
    	// Servos Initialization
    	linearServo = new Servo(10);
    	
    	// ask Jacob Initialization 
    	pot = new AnalogPotentiometer(3);
    	
    	//Limit Switch Initialization
    	limitSwitch = new DigitalInput(0);
    	
    	// Navx Gyro Initialization
    	gyro = new AHRS(SPI.Port.kMXP);
    	
    	// Encoders Initialization
    	rightDriveEncoder = new Encoder(8, 9, true, Encoder.EncodingType.k4X);
    	leftDriveEncoder = new Encoder(6, 7, false, Encoder.EncodingType.k4X);
    	
    	rightDriveEncoder.reset();
    	leftDriveEncoder.reset();

    	// NetworkTable Initialization
    	networkTable = NetworkTable.getTable("SmartDashboard");
    	Dashboard.setNetworkTable(networkTable);

    	// RobotDrive Initialization
    	drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
    	drive.setSafetyEnabled(false);
    	
    	// Vision Initialization
    	Vision = new Vision();
    	
    	// Lifter Initialization
    	Climber = new Climber();
    	
    	// Gear Assembly Initialization 
    	GearAssembly = new GearAssembly();
    	
    	// Shooter Assembly Initialization
    	ShooterAssembly = new ShooterAssembly();
    	
    	// Heading Initialization
    	Heading = new Heading();
    	
    	// Move Distance Initialization
    	MoveDistance = new MoveDistance();
    	
    	// Autonomous Initiilization
    	auto = new Autonomous();
    	
    	// Sendable Chooser Initialization
    	chooser = new SendableChooser();
    	
    	chooser.addDefault("Do Nothing", "Do Nothing");
    	chooser.addObject("Cross Baseline", "Cross Baseline");
    	chooser.addObject("Gear from Center", "Gear from Center");
    	chooser.addObject("Blue Gear Boiler Side", "Blue Gear Boiler Side");
    	chooser.addObject("Blue Gear NOT Boiler Side", "Blue Gear NOT Boiler Side");
    	chooser.addObject("Red Gear Boiler Side", "Red Gear Boiler Side");
    	chooser.addObject("Red Gear NOT Boiler Side", "Red Gear NOT Boiler Side");
    	
    	chooser.addObject("Testing 1", "Testing 1");
    	chooser.addObject("Testing 2", "Testing 2");
    	chooser.addObject("Testing 3", "Testing 3");
    	
    	SmartDashboard.putData("Auto mode", chooser);
	}
	
	public static double inchesToFeet(double i){
		return i / 12;
	}
	
	public static double feetToInches(double i){
		return i * 12;
	}
}
