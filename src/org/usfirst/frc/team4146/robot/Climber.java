package org.usfirst.frc.team4146.robot;

public class Climber {
	
	public Climber() {
		
	}
	
	public void update() {
		//X --> Catch
		//Y --> Climb
		//B --> Stall
		//A --> Backdrive
		
		if (RobotMap.lifterController.getButtonA()) { // Stall
			RobotMap.climberA.set(-0.2);
			RobotMap.climberB.set(-0.2);
		} else if (RobotMap.lifterController.getButtonB()) { // Backdrive
			RobotMap.climberA.set(0.1);
			RobotMap.climberB.set(0.1);
		} else if (RobotMap.lifterController.getButtonX()) { // Catch
			RobotMap.climberA.set(0.35);
			RobotMap.climberB.set(0.35);
		} else if (RobotMap.lifterController.getButtonY()) { // Climb
			RobotMap.climberA.set(0.5);
			RobotMap.climberB.set(0.5);
		} else {
			RobotMap.climberA.set(0.0);
			RobotMap.climberB.set(0.0);
		}
		
	}
	
}
