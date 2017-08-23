package org.usfirst.frc.team4146.robot;

public class Climber {
	
	public Climber() {
		
	}
	
	public void update() {
		
		if (RobotMap.lifterController.getButtonA()) { // use to hold once at the top
			RobotMap.climberA.set(-0.2);
			RobotMap.climberB.set(-0.2);
		} else if (RobotMap.lifterController.getButtonB()) { // backdrive the climber to get the rope out
			RobotMap.climberA.set(0.1);
			RobotMap.climberB.set(0.1);
		} else if (RobotMap.lifterController.getButtonX()) { // Use to catch the rope
			RobotMap.climberA.set(0.35);
			RobotMap.climberB.set(0.35);
		} else if (RobotMap.lifterController.getButtonY()) { // Once caught use to climb the rest of the way to the top
			RobotMap.climberA.set(0.5);
			RobotMap.climberB.set(0.5);
		} else {
			RobotMap.climberA.set(0.0);
			RobotMap.climberB.set(0.0);
		}
		
	}
	
}
