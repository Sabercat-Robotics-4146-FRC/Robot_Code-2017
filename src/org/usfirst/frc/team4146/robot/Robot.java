package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	SendableChooser<String> chooser = new SendableChooser<>();

	public Robot() {
		RobotMap.ROBOT = this;
	}

	@Override
	public void robotInit() {
		RobotMap.init();
	}

	@Override
	public void autonomous() {
		String autoSelected = chooser.getSelected();
		// String autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);

		switch (autoSelected) {
		case customAuto:
			RobotMap.drive.setSafetyEnabled(false);
			RobotMap.drive.drive(-0.5, 1.0); // spin at half speed
			//Timer.delay(2.0); // for 2 seconds
			RobotMap.drive.drive(0.0, 0.0); // stop robot
			break;
		case defaultAuto:
		default:
			RobotMap.drive.setSafetyEnabled(false);
			RobotMap.drive.drive(-0.5, 0.0); // drive forwards half speed
			//Timer.delay(2.0); // for 2 seconds
			RobotMap.drive.drive(0.0, 0.0); // stop robot
			break;
		}
	}

	/**
	 * Runs the motors with arcade steering.
	 */
	@Override
	public void operatorControl() {
		double spin;
		double move;
		Timer timer = new Timer();
		double dt = 0.0;
		
		HeadingPID headingPID = new HeadingPID();
		
		RobotMap.drive.setSafetyEnabled(false);
		while (isOperatorControl() && isEnabled()) {
			dt = timer.getDT();
			
			
			//RobotMap.drive.arcadeDrive(RobotMap.driveController.getDeadbandRightXAxis(), -RobotMap.driveController.getDeadbandLeftYAxis());
			
			move = -RobotMap.driveController.getDeadbandLeftYAxis();
			spin = RobotMap.driveController.getDeadbandRightXAxis();
			if (RobotMap.driveController.getButtonBack()) {
				headingPID.update(dt);
				spin = headingPID.get();
				Dashboard.send("Experimental Spin", headingPID.get());
			}
			RobotMap.drive.arcadeDrive(spin, move);
			
			Dashboard.send("Spin", spin);
			Dashboard.send("Heading Spin Error", headingPID.get_error());
			Dashboard.send("Fused Heading", RobotMap.gyro.getFusedHeading());
			Dashboard.send("Gyro Angle", RobotMap.gyro.getAngle());
			// End of Drive Code
timer.update();
		}
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
	}
}
