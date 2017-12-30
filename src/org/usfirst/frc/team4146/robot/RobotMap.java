package org.usfirst.frc.team4146.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class RobotMap {
	
	public static Robot ROBOT;
	
	// Heading Constants
	public static final double HEADING_TURN_KP = 0.06; // HEADING_KU * 0.6
	public static final double HEADING_TURN_KI = 0.25; // HEADING_TU/2
	public static final double HEADING_TURN_KD = 0.0;  // HEADING_TU/8
	public static final double HEADING_TURN_INTEGRAL_ACTIVATION_RANGE = 0.0;
	
	public static final double HEADING_LOCK_KP = 0.25; // HEADING_KU * 0.6
	public static final double HEADING_LOCK_KI = 0.022; // HEADING_TU/2
	public static final double HEADING_LOCK_KD = 0.0; // HEADING_TU/8
	
	//PID Constants
	public static final double ACCEPTABLE_ANGLE_ERROR = 0.5;
	public static final double ACCEPTABLE_DISTANCE_ERROR = 0.083;
	
	////// Declarations //////
	public static Controller driveController;
	public static Controller lifterController;
	
	// Motor Controllers Declaration
	public static Talon frontLeft;
	public static Talon rearLeft;
	public static Talon frontRight;
	public static Talon rearRight;
	
	//public static CANTalon canTalon;
	
	// Servos Declaration
	//public static Servo servo;
	
	// Navax Gyro Declaration
	public static AHRS gyro;
	
	// Encoders Declaration
	public static Encoder leftDriveEncoder;
	public static Encoder rightDriveEncoder;
	
	// Network Table Declaration
	public static NetworkTable networkTable;
	
	// Robot Drive Declaration
	public static RobotDrive drive;
	
	// Vision Declaration
	//public static Vision vision;
	
	// Heading Declaration
	//public static Heading heading;
	
	// Sendable Chooser Declaration
	public static SendableChooser chooser; //Sendable chooser allows us to choose the autonomous from smartdashboard
	
	public static void init() { // This is to be called in robitInit and instantiates stuff.
		
		// Controllers Initialization
    	driveController = new Controller(0);
    	lifterController = new Controller(1);
    	
    	// Motor Controllers Initialization
    	frontLeft = new Talon(0);
    	rearLeft = new Talon(1);
		frontRight = new Talon(2);
		rearRight = new Talon(3);
		
		rearRight.setInverted(true);
		
		frontLeft.setSafetyEnabled(false);
		rearLeft.setSafetyEnabled(false);
		frontRight.setSafetyEnabled(false);
		rearRight.setSafetyEnabled(false);
    	
    	// Servos Initialization
    	//servo = new Servo(1);
    	
    	// Navx Gyro Initialization
    	gyro = new AHRS(SPI.Port.kMXP);
    	
    	// Encoders Initialization
    	leftDriveEncoder = new Encoder(6, 7, true, Encoder.EncodingType.k4X);
    	rightDriveEncoder = new Encoder(8, 9, false, Encoder.EncodingType.k4X);
    	
    	leftDriveEncoder.reset();
    	rightDriveEncoder.reset();

    	// NetworkTable Initialization
    	networkTable = NetworkTable.getTable("SmartDashboard");
    	Dashboard.setNetworkTable(networkTable);

    	// RobotDrive Initialization
    	drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
    	
    	// Vision Initialization
    	//vision = new Vision();
    	
    	// Heading Initialization
    	//heading = new Heading();
    	
    	// Sendable Chooser Initialization
    	chooser = new SendableChooser();
    	chooser.addDefault("Do Nothing", "Do Nothing");
	}
}
