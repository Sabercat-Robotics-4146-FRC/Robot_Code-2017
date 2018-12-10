package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
//import main.PixyPacket;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid; 
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class intake {
	
	public static boolean intakeOn =false ;
	public static boolean intakePressed = false;
	public static void updateintake(){
	
		//Controller driver = new Controller(0);
		if(RobotMap.driveController.getButtonX()){
            if(!intakePressed){
                intakeOn = !intakeOn;
                intakePressed = true;
            }
        }else{
            intakePressed = false;	
        }
}
}