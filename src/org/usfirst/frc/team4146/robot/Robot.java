package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.CameraServer;

public class Robot extends SampleRobot {
	
	public Robot() {
		RobotMap.ROBOT = this;
	}
	
	/**
	 * Runs once when the robot is powered on and called when you are basically guaranteed that
	 * all WPILIBJ stuff will work.
	 */
	@Override
	public void robotInit() {
		RobotMap.init(); // Instantiates and Declares things to be used from RobotMap.
		RobotMap.gyro.reset();
	}

	/**
	 * Runs during the autonomous time period.
	 */
	@Override
	public void autonomous() {
//		Preferences prefs = Preferences.getInstance(); // Currently unused.
    	
    	
    	// Castes the current selection of chooser into autoSelected.
//    	String autoSelected = (String) RobotMap.chooser.getSelected(); 
//		switch(autoSelected) { // runs the selected autonomous determined by autoSelected.
//			case "Do Nothing": // This does nothing!
//				default: 
//				break;
//		}
		//RobotMap.auto.turn(60, 5);
		try {
			//RobotMap.auto.move(4, 10);
			RobotMap.auto.placeGear();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		boolean gyroToggle = true;
		// Loops as long as it is the teleop time period and the robot is enabled.
		RobotMap.Heading.setTurnMode();
		RobotMap.Heading.headingPID.set_setpoint(15);
		while (isOperatorControl() && isEnabled()) {
			dt = timer.getDT();
			// Start of Subsystem Updates
			RobotMap.Climber.update();
			//RobotMap.ShooterAssembly.update();
			
			// Testing shooting start
			if( RobotMap.driveController.getButtonA() ){
				RobotMap.slaveShooter.set(0.0);
				RobotMap.masterShooter.set(0.3);
				//System.out.println("A button");
			} else if( RobotMap.driveController.getButtonB() ){
				RobotMap.masterShooter.set(0.0);
				RobotMap.slaveShooter.set(0.3);
				//System.out.println("B button");
			} else {
				RobotMap.masterShooter.set(0.0);
				RobotMap.slaveShooter.set(0.0);
			}
			// Testing shooting end
			
			RobotMap.GearAssembly.update();
			RobotMap.Heading.update(dt);
			RobotMap.MoveDistance.update(dt);
			// End of Subsystem Updates
			
			Dashboard.send("Gyro Angle", RobotMap.gyro.getAngle());
			
			if (RobotMap.driveController.getButtonX() && gyroToggle) {
				RobotMap.gyro.reset();
				gyroToggle = false;
			}
			if (!RobotMap.driveController.getButtonX()) {
				gyroToggle = true;
			}
			
			// Start of Drive Code (in testing phase)
			move = RobotMap.driveController.getDeadbandLeftYAxis();
			spin = -RobotMap.driveController.getDeadbandRightXAxis();
			
			if (RobotMap.driveController.getButtonStart()) {
				spin = -RobotMap.Heading.get();
				// System.out.println("spin");
			}
			if(RobotMap.driveController.getButtonBack()) {
				move = RobotMap.MoveDistance.get();
			}
			RobotMap.drive.arcadeDrive(move, spin);
			Dashboard.send("Spin", spin);
			Dashboard.send("Heading Spin Error", RobotMap.Heading.headingPID.get_error());
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
