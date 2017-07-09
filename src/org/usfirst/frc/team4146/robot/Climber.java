package org.usfirst.frc.team4146.robot;

public class Climber {
	
	public Climber() {
		
	}
	
	public void update() {
		
		if (RobotMap.lifterController.getButtonA() ) {
			RobotMap.climber.set( 1.0 );
		}
		else if(RobotMap.lifterController.getButtonB() ) {
			RobotMap.climber.set( -1.0 );
		} 
		else if ( RobotMap.lifterController.getButtonX() ) {
			RobotMap.climber.set( 0.4  );
		} 
		else if( RobotMap.lifterController.getButtonY() ) {
			RobotMap.climber.set( -0.4  );
		}
		else {
			RobotMap.climber.set( 0.0 );
		}
		
	}
	
}
