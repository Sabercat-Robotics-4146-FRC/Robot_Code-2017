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

public class RobotMap {
	
	// Constants
	final double THIS_IS_A_CONSTANT = 0.0;
	
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
	public static Talon climberA;
	public static Talon climberB;
	
	public static CANTalon masterShooter;
	public static CANTalon slaveShooter;
	
	// Servos Declaration
	public static Servo linearServo;
	
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
	
	// Heading Declaration
	public static Heading Heading;
	
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
    	ballIntake = new Talon(4);
    	shooterIntake = new Talon(5);
    	vibrator = new Talon(6);
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
    	
    	// Navx Gyro Initialization
    	gyro = new AHRS(SPI.Port.kMXP);
    	
    	// Encoders Initialization
    	rightDriveEncoder = new Encoder(8, 9, true, Encoder.EncodingType.k4X);
    	leftDriveEncoder = new Encoder(6, 7, false, Encoder.EncodingType.k4X);
    	
    	rightDriveEncoder.reset();
    	leftDriveEncoder.reset();

    	// NetworkTable Initialization
    	networkTable = NetworkTable.getTable("SmartDashboard");

    	// RobotDrive Initialization
    	drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
    	drive.setSafetyEnabled(false);
    	
    	// Vision Initialization
    	Vision = new Vision();
    	
    	// Lifter Initialization
    	Climber = new Climber();
    	
    	// Gear Assembly Initialization 
    	GearAssembly = new GearAssembly();
    	
    	// Heading Initialization
    	Heading = new Heading();
    	
    	// Sendable Chooser Initialization
    	chooser = new SendableChooser();
    	chooser.addDefault("Do Nothing", "Do Nothing");
	}
}
