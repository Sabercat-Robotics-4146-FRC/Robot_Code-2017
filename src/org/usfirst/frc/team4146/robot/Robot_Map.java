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

	/*Linear Servo State Machine*/
	static enum servo_state {
		extending,
		retracting
	}
	
	/*Gear Servo State Machine*/
	enum gear_state {
		out,
		in
	}
	
	/*Robot State Machine*/
	enum robot_state {
		shooting,
		testing_shooter,
		intaking,
		gear_tracking,
		sicem, // Growl mode
		idle
	}
	
	
	/* Global Constants */
	
	final double GEAR_IN = 0.35;
	final double GEAR_OUT = 0.64;
	
	/*Shooter RPM parameters*/
	static double shooter_rpm_tolerance = 10.0; //was 50
	static double shooter_rpm_setpoint  = -2000.0;// In competition set it to: -2700.0
	static double shooter_intake_speed  = -0.8;
	static double vibrator_speed = 0.8;
	
	/* Joystick Controllers */ 
	
	Controller drive_controller;
	Controller lifter_controller;
	
	
	/* Motor Controller initialization */
	
	/*Talon SR Motor Controller init*/
	Talon front_left;
	Talon rear_left;
	
	Talon front_right;
	Talon rear_right;
	
	Talon ball_intake;
	Talon shooter_intake;
	Talon vibrator;
		
	/*CANTalon SRX Motor Controller init*/
	CANTalon master_shooter;
	CANTalon slave_shooter;
	
	/*Servo Motor Controller init*/
	Servo linear_servo;
	Servo gear_servo;
	
	
	/* Sensor and NetworkTable initialization */
	
	/*Navx Gyro init*/
	AHRS gyro;
	
	/*Encoder init*/
	Encoder right_drive_encoder;
	Encoder left_drive_encoder;
	
	/*NetworkTable init*/
	NetworkTable network_table;
	
	
	/* Subclass initialization */
	
	/*RobotDrive init*/
	RobotDrive drive;
	
	/*Ramp_Drive init */
	Ramp_Drive smooth_drive;
	
	/*Vision init*/
	Vision gear_vision;
	
	/*Lifter init*/
	Lifter lifter;
	
	/*Heading init*/
	Heading heading;
	
	//Move_Distance init
	Move_Distance distance;
	
	/*Sendable Chooser init */
	SendableChooser chooser; //Sendable chooser allows us to choose the autonomous from smartdashboard
	
	public Robot_Map(){
		
		
		
	}

}
