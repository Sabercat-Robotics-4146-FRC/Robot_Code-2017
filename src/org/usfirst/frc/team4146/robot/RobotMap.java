package org.usfirst.frc.team4146.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class RobotMap {
	
	public static Robot ROBOT;
	
	//constants 
	
	//declare all the things
	
	//controllers
	public static Controller driveController;
	public static Controller tshirtController;
	
	//talons
	public static Talon frontRight;
	public static Talon rearRight;
	public static Talon rearLeft;
	public static Talon frontLeft;
	public static Talon flyWheel;
	public static Talon turretRotator;
	public static Talon Intake;
	public static Talon intakeRotator;
	public static Talon tshirtL;
	public static Talon tshirtR;
	public static Relay spike;
	
	//solenoids
	public static Solenoid Solenoid0;
	public static Solenoid Solenoid1;
	
	//encoders and stuff
	public static DigitalInput limitSwitchDown;
	public static DigitalInput limitSwitchUp; 
	
	public static Compressor Compressor;
	//classes      

	//public static Object prDrive;

	public static DifferentialDrive prDrive;
	public static SpeedControllerGroup left;
	public static SpeedControllerGroup right;
	
	
	
	// make an init function to be called in robotinit
		// instatusate all the things 
	
	public static void init()
	{
		
		//camera
//		IPCamera camera = CameraServer.getInstance().startAutomaticCapture();
		//controller
		driveController = new Controller(0);
		tshirtController = new Controller(1);
    	//talons
		rearLeft = new Talon(0);
    	frontLeft = new Talon(1);
    	frontRight = new Talon(3);
    	rearRight= new Talon(4);
    	
    	
    	
    	flyWheel = new Talon(2);
    	intakeRotator = new Talon(5);
    	turretRotator = new Talon(6);
    	Intake = new Talon(7);    	
    	tshirtL = new Talon(8);
    	tshirtR = new Talon(9);
    	
    	
    	
    	
 		//safety
    	frontLeft.setSafetyEnabled(true);
		rearLeft.setSafetyEnabled(true);
		frontRight.setSafetyEnabled(false);
		rearRight.setSafetyEnabled(false);
		
		flyWheel.setSafetyEnabled(false);
		turretRotator.setSafetyEnabled(false);
	    Intake.setSafetyEnabled(false);
		intakeRotator.setSafetyEnabled(false);
		tshirtL.setSafetyEnabled(false);
		tshirtR.setSafetyEnabled(false);
		
		
		
		//inverted
		rearLeft.setInverted(true);
		frontLeft.setInverted(true);
		rearRight.setInverted(false);
		frontRight.setInverted(false);
		
		flyWheel.setInverted(true);
		turretRotator.setInverted(true);
		Intake.setInverted(false);
		intakeRotator.setInverted(false);	
		tshirtL.setInverted(false);
		tshirtR.setInverted(true);
		
		 left = new SpeedControllerGroup(frontLeft, rearLeft);
	     right = new SpeedControllerGroup(frontRight, rearRight);
	 	 prDrive = new DifferentialDrive(left, right);
		
		//RobotDrive m_Drive = new RobotDrive(frontRight, rearRight,frontLeft, rearLeft);
		
		
		//solenoids / compressor
		Solenoid0 = new Solenoid(0);                        // Solenoid port
		Solenoid1 = new Solenoid(1);
        limitSwitchDown = new DigitalInput(0);
    	limitSwitchUp = new DigitalInput(1);
		//RobotDrive tshirt_Drive = new RobotDrive(tshirtL, tshirtR);
    	Compressor = new Compressor(1);  //creating compressor

    	
	}

}
