package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends SampleRobot {
	
	/**
	 * Default Robot Constructor.
	 */
	public Robot() {
		RobotMap.ROBOT = this;
	}
	
	/**
	 * Runs once when the robot is powered on and called when you are basically guaranteed that
	 * all WPILIBJ stuff will work.
	 */
	@Override
	public void robotInit() {
		RobotMap.init();
	}
	
	/**
	 * Runs during the autonomous time period.
	 */
	@Override
	public void autonomous() {
		String autoSelected = (String) RobotMap.chooser.getSelected();
		System.out.println("Running: " + autoSelected + " auto.");
		
		switch (autoSelected) {
			
		}
	}
	
	/**
	 * Runs the motors with arcade steering.
	 */
	@Override
	public void operatorControl() {
		Timer timer = new Timer();
		HeadingPID headingPID = new HeadingPID();
		
		double spin;
		double move;
		double dt = 0.0;
		
		RobotMap.drive.setSafetyEnabled(false);
		
		while (isOperatorControl() && isEnabled()) { // Operator Controller Loop
			dt = timer.getDT();
			
			move = RobotMap.driveController.getDeadbandLeftYAxis();
			spin = -RobotMap.driveController.getDeadbandRightXAxis();
			
			if(RobotMap.driveController.getButtonA()){
				System.out.println("Testtttttt");
				RobotMap.sparkL.set(-1.0);
				RobotMap.sparkR.set(1.0);
			} else if (RobotMap.driveController.getButtonY()){
				RobotMap.sparkL.set(1.0);
				RobotMap.sparkR.set(-1.0);
			} else {
				RobotMap.sparkL.set(0.0);
				RobotMap.sparkR.set(0.0);
			}
			
//			if (RobotMap.driveController.getButtonBack()) {
//				headingPID.update(dt);
//				spin = headingPID.get();
//				Dashboard.send("Experimental Spin", headingPID.get());
//			}
			
			RobotMap.drive.arcadeDrive(move, spin);
			// End of Drive Code
			//RobotMap.masterArm.set(move);
			
			// Printing Stuff to SmartDahsboard
			Dashboard.send("Spin", spin);
			Dashboard.send("Move", move);
			Dashboard.send("Encoder", RobotMap.encoder.getRaw());
			//Dashboard.send("Heading Spin Error", headingPID.get_error());
			//Dashboard.send("Fused Heading", RobotMap.gyro.getFusedHeading());
			//Dashboard.send("Gyro Angle", RobotMap.gyro.getAngle());
			
			timer.update();
		} // End of Operator Controller loop
	} // End of Operator Controller
	
	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
		
	}
}
