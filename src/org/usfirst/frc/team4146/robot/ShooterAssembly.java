package org.usfirst.frc.team4146.robot;

import com.ctre.CANTalon.TalonControlMode;

public class ShooterAssembly {
	
	enum RobotState { // Robot State Machine
		SHOOTING,
		TESTING_SHOOTER,
		INTAKING,
		SICEM, // Growl mode
		IDLE
	}
	
	enum ServoState { // Linear Servo State Machine
		EXTENDING,
		RETRACTING
	}
	
	RobotState state = RobotState.IDLE;
    ServoState linearServoState = ServoState.EXTENDING;
    
    Timer ShooterTimer = new Timer();

	public ShooterAssembly() {
		
	}
	
	double timeAccumulator = 0.0;
    
    public void oscillateServo() {
    	// Linear servo 
    	if(timeAccumulator > 5.5) {
			switch (linearServoState) {
				case EXTENDING:
					RobotMap.linearServo.set(0.2);
					timeAccumulator = 0.0;
					linearServoState = ServoState.RETRACTING;
					break;
				case RETRACTING:
					RobotMap.linearServo.set(0.7);
					timeAccumulator = 0.0;
					linearServoState = ServoState.EXTENDING;
					break;
				default:
					System.out.println("Defaulted in linearServoState!");
					break;
			}
		}
    }
	
	public void update() {
		
		ShooterTimer.update(); // Update ShooterTimer.
		timeAccumulator += ShooterTimer.getDT();
		
		// Check button inputs and change state 
		if (RobotMap.driveController.getButtonA()) { // Shoot with right trigger,
			state = RobotState.SHOOTING;
		} 
//			else if (RobotMap.driveController.getLeftBumper()) { // Ball intake with left trigger.
//			state = RobotState.INTAKING;
//		} else if (RobotMap.driveController.getButtonB()) { // Test shooter at full speed with B button
//			state = RobotState.TESTING_SHOOTER;
//		}
		 else if (RobotMap.driveController.getButtonX()) {
			state = RobotState.SICEM;
		} else { // Robot Idle State
			state = RobotState.IDLE;
		}
		
		
		// Handle States
		switch (state) {
			case SHOOTING: 
				RobotMap.masterShooter.enableControl(); // Allow talon internal PID to apply control to the talon
				RobotMap.masterShooter.changeControlMode(TalonControlMode.Speed);
				RobotMap.masterShooter.set(RobotMap.SHOOTER_RPM_SETPOINT);
				
				// Network Table debugging
				RobotMap.networkTable.putNumber("Shooter_RPM", RobotMap.masterShooter.getSpeed());
				RobotMap.networkTable.putNumber("Shooter Error", RobotMap.masterShooter.getSpeed() - 
						RobotMap.masterShooter.getSetpoint());
				RobotMap.networkTable.putNumber("Get value", RobotMap.masterShooter.get());
				RobotMap.networkTable.putNumber("Motor Output", RobotMap.masterShooter.getOutputVoltage() /
						RobotMap.masterShooter.getBusVoltage());
				RobotMap.networkTable.putNumber("Closed_Loop_Error", RobotMap.masterShooter.getClosedLoopError());
				
				RobotMap.ballIntake.set(-0.3);
				RobotMap.vibrator.set(RobotMap.VIBRATOR_SPEED);
				oscillateServo();
    			// Only feed balls to shooter if RPM is within a tolerance.
    			if (Math.abs(RobotMap.masterShooter.getSpeed() - RobotMap.masterShooter.getSetpoint())
    					<= RobotMap.SHOOTER_RPM_TOLERANCE) {
    				RobotMap.shooterIntake.set(RobotMap.SHOOTER_INTAKE_SPEED);
    			} else {
    				RobotMap.shooterIntake.set(0.0);
    			}
				break;
			case SICEM:
				RobotMap.ballIntake.set(-1.0);
				RobotMap.vibrator.set(0.9);
    			oscillateServo();
    			
    			RobotMap.masterShooter.enableControl();
    			RobotMap.masterShooter.changeControlMode(TalonControlMode.Speed);
    			RobotMap.masterShooter.set(-3000);
    			
    			if (Math.abs(RobotMap.masterShooter.getSpeed()) >= 2900) {
    				RobotMap.shooterIntake.set(-1.0);
    			}
				break;
			case INTAKING:
				RobotMap.ballIntake.set(-1.0);
				break;
			case TESTING_SHOOTER:
//				RobotMap.masterShooter.changeControlMode(TalonControlMode.Speed);
//				RobotMap.masterShooter.set(-2200);
//				RobotMap.masterShooter.enableControl();
//				System.out.println(RobotMap.masterShooter.getError());
//				RobotMap.shooterIntake.set(-0.3);
//				RobotMap.shooterIntake.set(-0.6);
				RobotMap.vibrator.set(0.6);
				RobotMap.shooterIntake.set(-1.0);
				break;
			case IDLE:
				RobotMap.masterShooter.disableControl();
				RobotMap.ballIntake.set(0.0);
				RobotMap.shooterIntake.set(0.0);
				RobotMap.vibrator.set(0.0);
				break;
			default:
				System.out.println("Defaulting in robot state!");
				break;
		
		} // End of state switch
	}
}
