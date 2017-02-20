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
import com.ctre.CANTalon;
import com.ctre.CANTalon.*;

import org.usfirst.frc.team4146.robot.PID.*;

public class Main_Operations {//Main_Operations does not include driving.
	
	Controller drive_controller;
	
	Talon ball_intake;
	Talon shooter_intake;
	Talon vibrator;
	
	CANTalon master_shooter;
	CANTalon slave_shooter;
	
	
	
//	boolean ball_intake_button_ready = true;
	
	public Main_Operations( Controller drive_controller ) {
		
		this.drive_controller = drive_controller;
		
		ball_intake 	= new Talon( 4 );
		shooter_intake 	= new Talon( 5 );
		vibrator		= new Talon( 6 );
		
		master_shooter 	= new CANTalon( 0 );
		slave_shooter  	= new CANTalon( 1 );
		
		
		
		master_shooter.setFeedbackDevice( FeedbackDevice.CtreMagEncoder_Relative );
		master_shooter.reverseSensor(false);
		
		master_shooter.configNominalOutputVoltage(+0.0f, -0.0f);
		master_shooter.configPeakOutputVoltage(+12.0f, 0.0f);
	}
	
	public void operations(){
		if ( drive_controller.get_a_button() ) {
			ball_intake.set( -0.6 );
		} else {
			ball_intake.set( 0.0 );
		}
		
		
//		if ( drive_controller.get_a_button() && ball_intake_button_ready ) {
//			ball_intake.set( -0.6 );
//			ball_intake_button_ready = false;
//		} else if ( !drive_controller.get_a_button() ) {
//			ball_intake_button_ready = true;
//			ball_intake.set( 0.0 );
//		} else {
//			ball_intake.set( 0.0 );
//		}
		
		
		
		if ( drive_controller.get_b_button() ) {
			master_shooter.set( -1.0 );
			slave_shooter.set( -1.0 );
		} else {
			master_shooter.set( 0.0 );
			slave_shooter.set( 0.0 );
		}
		
		if ( drive_controller.get_y_button() ) {
			shooter_intake.set( -0.8 );
		} else {
			shooter_intake.set( 0.0 );
		}
		
		if ( drive_controller.get_x_button() ) {
			vibrator.set( 0.7 );
		} else {
			vibrator.set( 0.0 );
		}
		
		if ( drive_controller.get_right_bumper() ) {
			ball_intake.set( -0.6 );
			shooter_intake.set( -0.8 );
			vibrator.set( 0.7 );
		} else {
			ball_intake.set( 0.0 );
			shooter_intake.set( 0.0 );
			vibrator.set( 0.0 );
		}
		
		System.out.println( -master_shooter.getSpeed() );
		//SmartDashboard.putNumber("Flywheel Speed", -master_shooter.getSpeed());
		
	}
	
	public void Temp_Talon_Set(){
		ball_intake.setSafetyEnabled(false);
	}
	
}
