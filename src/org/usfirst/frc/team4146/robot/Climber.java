package org.usfirst.frc.team4146.robot;

public class Climber {
	
	public Climber() {
		
	}
	
	public void update() {
		if (RobotMap.driveController.getLeftBumper()) { // Catch
			RobotMap.climberA.set(0.35);
			RobotMap.climberB.set(0.35);
		} else if (RobotMap.driveController.getButtonB()) { // Climb
			RobotMap.climberA.set(0.9);
			RobotMap.climberB.set(0.9);
		} else if (RobotMap.driveController.getButtonStart()) {
			RobotMap.climberA.set(0.35);
			RobotMap.climberB.set(0.35);
		} else {
			RobotMap.climberA.set(0.0);
			RobotMap.climberB.set(0.0);
//			if (RobotMap.lifterController.getButtonA()) { // backdrive
//				RobotMap.climberA.set(-0.2);
//				RobotMap.climberB.set(-0.2);
//			} else if (RobotMap.lifterController.getButtonB()) { // stall
//				RobotMap.climberA.set(0.35);
//				RobotMap.climberB.set(0.35);
//			} else if (RobotMap.lifterController.getButtonX()) { // Catch
//				RobotMap.climberA.set(0.35);
//				RobotMap.climberB.set(0.35);
//			} else if (RobotMap.lifterController.getButtonY()) { // Climb
//				RobotMap.climberA.set(0.9);
//				RobotMap.climberB.set(0.9);
//			} else {
//				RobotMap.climberA.set(0.0);
//				RobotMap.climberB.set(0.0);
//			}
		}
		//X --> Catch
		//Y --> Climb
		//B --> Stall
		//A --> Backdrive
		
		
		
	}
	
}
