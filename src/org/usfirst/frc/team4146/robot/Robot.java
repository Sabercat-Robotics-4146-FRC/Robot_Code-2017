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
		
		CameraServer.getInstance().startAutomaticCapture();
	}

	/**
	 * Runs during the autonomous time period.
	 */
	@Override
	public void autonomous() {
		RobotMap.gyro.reset();
//		Preferences prefs = Preferences.getInstance(); // Currently unused.
    	
    	
    	// Castes the current selection of chooser into autoSelected.
    	String autoSelected = (String) RobotMap.chooser.getSelected(); 
    	System.out.println(autoSelected);
    	try {
    	
		switch(autoSelected) { // runs the selected autonomous determined by autoSelected.
		
			case "Do Nothing": // This does nothing!
				default: 
					
				break;
				
			case "Cross Baseline": // Updated!!!
				RobotMap.auto.move(-8.0, 15); //-8
				break;
				
			case "Gear from Center": // Updated!!!
				RobotMap.auto.move(-6.08 + RobotMap.inchesToFeet(-20), 5); // -6.08, 5
				RobotMap.auto.placeGear();
				break;
				
			case "Blue Gear Boiler Side": // Updated ...
				RobotMap.auto.move(-7.0, 8); // -7.29
				RobotMap.auto.turn(60, 3);
				RobotMap.auto.move(-3.16 + RobotMap.inchesToFeet(-20), 3); // -2.48
				RobotMap.auto.placeGear();
				break;
				
			case "Blue Gear NOT Boiler Side": // Updated
				RobotMap.auto.move(RobotMap.inchesToFeet(-94), 8); // -7.29, 8
				RobotMap.auto.turn(-60, 3);
				RobotMap.auto.move(RobotMap.inchesToFeet(-68), 3); // -2.48, 3
				RobotMap.auto.placeGear();
				break;
	
			case "Red Gear Boiler Side": // Updated!!!
				RobotMap.auto.move(-6.29, 8); // -6.396
				RobotMap.auto.turn(-60, 3);
				RobotMap.auto.move(-1.833 + RobotMap.inchesToFeet(-20), 3); //-2.48
				RobotMap.auto.placeGear();
				break;
	
			case "Red Gear NOT Boiler Side": // Updated!!!
				RobotMap.auto.move(-6.5 + RobotMap.inchesToFeet(-17), 8); // -6.396
				RobotMap.auto.turn(60, 3);
				RobotMap.auto.move(-4.2 + RobotMap.inchesToFeet(-17), 3); // -2.48, 3
				RobotMap.auto.placeGear();
				break;
	
			case "Testing 1":
				RobotMap.auto.placeGear();
				break;
	
			case "Testing 2":
				
				break;
	
			case "Testing 3":
				
				break;
		}
		
    	} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//		try {
//			RobotMap.auto.move(-6, 10);
//			RobotMap.auto.placeGear();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * Runs during the teleop period.
	 */
	@Override
	public void operatorControl() {
		Timer timer = new Timer();
		double dt = 0.0;
		double spin;
		double move;
		
		HeadingPID heading2 = new HeadingPID();
		while (isOperatorControl() && isEnabled()) {
			dt = timer.getDT();
			heading2.update(dt);
			
			// Start of Subsystem Updates
			RobotMap.Climber.update();
			//RobotMap.ShooterAssembly.update();
			
			RobotMap.ShooterAssembly.update();
			// Testing shooting end
			
			RobotMap.GearAssembly.update();
			// End of Subsystem Updates
			
			// Start of Drive Code (in testing phase)
			move = RobotMap.driveController.getDeadbandLeftYAxis();
			spin = -RobotMap.driveController.getDeadbandRightXAxis();
			if (RobotMap.driveController.getButtonBack()) {
				spin = heading2.get();
				Dashboard.send("Experimental Spin", heading2.get());
			}
			RobotMap.drive.arcadeDrive(move, spin);
			Dashboard.send("Spin", spin);
			Dashboard.send("Heading Spin Error", RobotMap.Heading.headingPID.get_error());
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
