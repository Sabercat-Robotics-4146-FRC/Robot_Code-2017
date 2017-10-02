package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.CameraServer;

public class Robot extends SampleRobot {
	
	public Robot() {
		
	}
	
	/**
	 * Runs once when the robot is powered on and called when you are basically guaranteed that
	 * all WPILIBJ stuff will work.
	 */
	@Override
	public void robotInit() {
		RobotMap.init(); // Instantiates and Declares things to be used from RobotMap.
	}

	/**
	 * Runs during the autonomous time period.
	 */
	@Override
	public void autonomous() {
//		Preferences prefs = Preferences.getInstance(); // Currently unused.
    	
    	Autonomous auto = new Autonomous();
    	// Castes the current selection of chooser into autoSelected.
    	String autoSelected = (String) RobotMap.chooser.getSelected(); 
		switch(autoSelected) { // runs the selected autonomous determined by autoSelected.
			case "Do Nothing": // This does nothing!
				default: 
				break;
		}
	}

	/**
	 * Runs during the teleop period.
	 */
	@Override
	public void operatorControl() {
		CameraServer.getInstance().startAutomaticCapture();
		Timer timer = new Timer();
		double dt = 0.0;
		double spin;
		double move;
		// Loops as long as it is the teleop time period and the robot is enabled.
		while (isOperatorControl() && isEnabled()) {
			dt = timer.getDT();
			//Subsystem Updates
			RobotMap.Climber.update();
			//RobotMap.ShooterAssembly.update();
			RobotMap.GearAssembly.update();
			RobotMap.Heading.update(dt);
			RobotMap.MoveDistance.update(dt);
			timer.update();
			move = RobotMap.driveController.getDeadbandLeftYAxis();
			spin = -RobotMap.driveController.getDeadbandRightXAxis();
			
			//Drive Code
			if (RobotMap.driveController.getButtonStart()) {
				spin = -RobotMap.Heading.get();
			}
			if(RobotMap.driveController.getButtonBack()) {
				move = RobotMap.MoveDistance.get();
			}
			RobotMap.drive.arcadeDrive(move, spin);
		}
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
		
	}
}
