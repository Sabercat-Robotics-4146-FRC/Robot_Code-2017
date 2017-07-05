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
	
	// Constants
	final double THIS_IS_A_CONSTANT = 0.0;
	
	////// Declarations //////
	public static Controller driveController;
	public static Controller lifterController;
	
	// Motor Controllers Declaration
	public static Talon talon;
	public static CANTalon canTalon;
	
	// Servos Declaration
	public static Servo servo;
	
	// Navax Gyro Declaration
	public static AHRS gyro;
	
	// Encoders Declaration
	public static Encoder encoder;
	
	// Network Table Declaration
	public static NetworkTable networkTable;
	
	// Robot Drive Declaration
	public static RobotDrive drive;
	
	// Vision Declaration
	public static Vision vision;
	
	// Heading Declaration
	public static Heading heading;
	
	// Sendable Chooser Declaration
	public static SendableChooser chooser; //Sendable chooser allows us to choose the autonomous from smartdashboard
	
	public static void init() { // This is to be called in robitInit and instantiates stuff.
		
		// Controllers Initialization
    	driveController = new Controller(0);
    	lifterController = new Controller(1);
    	
    	// Motor Controllers Initialization
    	talon = new Talon(0);
    	canTalon = new CANTalon(0);
    	
    	talon.setSafetyEnabled(false);
    	canTalon.setSafetyEnabled(false);
    	
    	// Servos Initialization
    	servo = new Servo(1);
    	
    	// Navx Gyro Initialization
    	gyro = new AHRS(SPI.Port.kMXP);
    	
    	// Encoders Initialization
    	encoder = new Encoder(8, 9, true, Encoder.EncodingType.k4X);
    	
    	encoder.reset();

    	// NetworkTable Initialization
    	networkTable = NetworkTable.getTable("SmartDashboard");

    	// RobotDrive Initialization
    	drive = new RobotDrive(); // fill with frontLeft, rearLeft, frontRight, rearRight
    	
    	// Vision Initialization
    	vision = new Vision();
    	
    	// Heading Initialization
    	heading = new Heading(gyro);
    	
    	// Sendable Chooser Initialization
    	chooser = new SendableChooser();
    	chooser.addDefault("Do Nothing", "Do Nothing");
	}
}
