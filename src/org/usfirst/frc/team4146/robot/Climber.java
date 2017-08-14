package org.usfirst.frc.team4146.robot;

public class Climber {
	
	public Climber() {
		
	}
	
	public void update() {
		
		if (RobotMap.lifterController.getButtonA()) {
			RobotMap.climberA.set(-0.2);
			RobotMap.climberB.set(-0.2);
		} else if (RobotMap.lifterController.getButtonB()) {
			RobotMap.climberA.set(0.1);
			RobotMap.climberB.set(0.1);
		} else if (RobotMap.lifterController.getButtonX()) {
			RobotMap.climberA.set(0.35);
			RobotMap.climberB.set(0.35);
		} else if (RobotMap.lifterController.getButtonY()) {
			RobotMap.climberA.set(0.5);
			RobotMap.climberB.set(0.5);
		} else {
			RobotMap.climberA.set(0.0);
			RobotMap.climberB.set(0.0);
		}
		
	}
	
}
