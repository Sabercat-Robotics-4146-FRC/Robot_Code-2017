package org.usfirst.frc.team4146.robot;
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
		Talon frontLeft = new Talon(0);
		Talon rearLeft = new Talon(1);
		Talon frontRight = new Talon(2);
		Talon rearRight = new Talon(3);
		
		frontLeft.setSafetyEnabled(false);
		rearLeft.setSafetyEnabled(false);
		frontRight.setSafetyEnabled(false);
		rearRight.setSafetyEnabled(false);
		
		//RobotDrive drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
		
		while (isOperatorControl() && isEnabled()) {
			if (driver.getButtonA()){ // rear right is backward still need to fix!!!
		//		rearRight.set(0.5);
			frontRight.set(0.5);
//				rearLeft.set(0.5);
//				frontLeft.set(0.5);
			} else {
				//rearRight.set(0.0);
			//	rearRight.set(0.0);
				frontRight.set(0.0);
//				rearLeft.set(0.0);
//				frontLeft.set(0.0);
			}
				
		}
	}
	
	@Override
	public void test() {
		
	}
}
