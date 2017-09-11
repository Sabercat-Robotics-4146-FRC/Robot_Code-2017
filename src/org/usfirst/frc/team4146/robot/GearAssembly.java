package org.usfirst.frc.team4146.robot;

public class GearAssembly {
	
	enum TiltState {
		TILTED_UP,
		TILTED_DOWN
	}
	
	enum WheelState {
		CATCH_GEAR,
		RELEASE_GEAR,
		HOLD_GEAR,
		IDLE
	}
	
	TiltState tiltState = TiltState.TILTED_UP;
	WheelState wheelState = WheelState.IDLE;
	boolean gearTiltToggle = true; 
	
	
	public GearAssembly() {
		
	}
	
	int i = 0;
	public void update() {
		if (i >= 17){ // This prints out the value of the Potentiometer every 17 iterations for testing.
			System.out.println("Pot Value: " + RobotMap.pot.get());
			i = 0;
		}
		i++;
		// Checks controller inputs for gear tilting operations and toggles tilt state accordingly.
		if (RobotMap.driveController.getRightBumper() && gearTiltToggle) {
			gearTiltToggle = false;
			
			if (tiltState == TiltState.TILTED_UP) {
				tiltState = TiltState.TILTED_DOWN;
			} else {
				tiltState = TiltState.TILTED_UP;
			}
		}
		
		if (!RobotMap.driveController.getRightBumper()) {
			gearTiltToggle = true;
		}
		
		// Checks controller inputs for gear wheel operations and changes states accordingly.
		if (RobotMap.limitSwitch.get() == true) { // Hold Gear
			wheelState = WheelState.HOLD_GEAR;
		} else if (RobotMap.driveController.getRightTrigger()) { // Catch Gear
			wheelState = WheelState.CATCH_GEAR;
		} else if (RobotMap.driveController.getLeftTrigger()) { // Release Gear
			wheelState = WheelState.RELEASE_GEAR;
		} else { // Idle
			wheelState = WheelState.IDLE;
		}
		
		// Changes gear tilt motor values in accordance with gear tilt state machine.
		switch (tiltState) {
			case TILTED_UP:
				if (RobotMap.pot.get() > RobotMap.TILT_UPPER_STOP) {
					RobotMap.gearTilt.set(RobotMap.TILT_STALL_TORQUE);
				} else {
					RobotMap.gearTilt.set(RobotMap.TILLT_UP_POWER);
				}
				break;
			case TILTED_DOWN:
				if (RobotMap.pot.get() > RobotMap.TILT_LOWER_STOP) {
					RobotMap.gearTilt.set(RobotMap.TILT_DOWN_POWER);
				} else {
					RobotMap.gearTilt.set(0.0);
				}
				break;
			default:
				System.out.println("Defaulting in Gear Tilt State!!!!!");
		}
		
		//Changes gear wheel motor values in accordance with gear 
		switch (wheelState) {
			case CATCH_GEAR:
				
				break;
			case RELEASE_GEAR:
				
				break;
			case HOLD_GEAR:
				
				break;
			case IDLE:
				
				break;
			
		}
		
	}
}
