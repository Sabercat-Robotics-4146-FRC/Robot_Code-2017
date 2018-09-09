package org.usfirst.frc.team4146.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends SampleRobot {
	
	public Robot() {
		
	}
	
	@Override
	public void robotInit() {
		
	}

	@Override
	public void autonomous() {
		
	}
	
	@Override
	public void operatorControl() {
		Controller driver = new Controller(0);
		WPI_TalonSRX frontLeft = new WPI_TalonSRX(4);
		WPI_TalonSRX rearLeft = new WPI_TalonSRX(3);
		WPI_TalonSRX frontRight = new WPI_TalonSRX(1);
		WPI_TalonSRX rearRight = new WPI_TalonSRX(2);
	
		
		frontLeft.setSafetyEnabled(false);
		rearLeft.setSafetyEnabled(false);
		frontRight.setSafetyEnabled(false);
		rearRight.setSafetyEnabled(false);
		
		rearRight.setInverted(true);
		frontRight.setInverted(true);
		
		
		//RobotDrive drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
		//code for button Y to move forward
		while (isOperatorControl() && isEnabled()) {
			if (driver.getButtonY()){ // rear right is backward still need to fix!!!
			rearRight.set(0.5);
			frontRight.set(0.5);
			rearLeft.set(0.5);
			frontLeft.set(0.5);
			} else {
				rearRight.set(0.0);
				frontLeft.set(0.0);
        		rearLeft.set(0.0);
        		frontRight.set(0.0);
			}
				
		
		
		//code for button b to move right
		
		
			if (driver.getButtonB()){ // rear right is backward still need to fix!!!
			//rearRight.set(0.5);
			//frontRight.set(0.5);
			rearLeft.set(0.5);
			frontLeft.set(0.5);
			} else {
				//rearRight.set(0.0);
				frontLeft.set(0.0);
        		rearLeft.set(0.0);
        		//frontRight.set(0.0);
			}
				
		
		
		
		// code for button a to move back
		
		
			if (driver.getButtonA()){ // rear right is backward still need to fix!!!
				rearRight.set(-0.3);
				frontRight.set(-0.3);
				rearLeft.set(-0.3);
				frontLeft.set(-0.3);
				
			} else {
				rearRight.set(0.0);
				frontLeft.set(0.0);
        		rearLeft.set(0.0);
				frontRight.set(0.0);
			}
				
		
		
		
		// code for button x to move left
		
		
		
			if (driver.getButtonX()){ // rear right is backward still need to fix!!!
			rearRight.set(0.5);
			frontRight.set(0.5);
			//rearLeft.set(0.5);
			//frontLeft.set(0.5);
			} else {
				rearRight.set(0.0);
				//frontLeft.set(0.0);
        		//rearLeft.set(0.0);
				frontRight.set(0.0);
			}
		}
		
		try {
			wait(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	@Override
	public void test() {
		
	}

	
}
