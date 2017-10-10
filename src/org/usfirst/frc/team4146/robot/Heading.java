package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;

public class Heading {
	public PID headingPID;
	
	public Heading(){
		headingPID = new PID(new signal(){
			public double getValue(){
				return RobotMap.gyro.getAngle();
			}
		});
		headingPID.set_pid(RobotMap.HEADING_TURN_KP, RobotMap.HEADING_TURN_KI, RobotMap.HEADING_TURN_KD);
	}
	
	int i = 0;
	
	public void update(double dt) {
		headingPID.update(dt);
		if (i > 50) { 
			//System.out.println("Gryo Angle: " + RobotMap.gyro.getAngle());
			//System.out.println("dt: " + dt);
			//System.out.println("Gryo Angle: " + RobotMap.gyro.getAngle());
			//System.out.println(headingPID.get());
			i = 0;
		}
		i++;
	}
	
	public double get(){
		return headingPID.get();
	}
	
	public void setTurnMode(){
		headingPID.set_pid(RobotMap.HEADING_TURN_KP, RobotMap.HEADING_TURN_KI, RobotMap.HEADING_TURN_KD);
		headingPID.setIntegralActivationRange(RobotMap.HEADING_TURN_INTEGRAL_ACTIVATION_RANGE);
	}
	
	public void setLockMode(){
		headingPID.set_pid(RobotMap.HEADING_LOCK_KP, RobotMap.HEADING_LOCK_KI, RobotMap.HEADING_LOCK_KD);
	}
	
	// 
	public boolean isNotInError(double timeOut, double timeElapsed){
		return ((headingPID.get_error() > RobotMap.ACCEPTABLE_ANGLE_ERROR) || (headingPID.steady_state_error() > RobotMap.ACCEPTABLE_ANGLE_ERROR)) && (timeElapsed < timeOut);
	}
}
