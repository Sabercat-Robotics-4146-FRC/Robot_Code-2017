package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;

public class Heading {
	public PID headingPID;
	private double setpoint;
	public Heading(){
		setpoint = 0;
		headingPID = new PID(new signal(){
			public double getValue(){
				return find_smallest_angle(RobotMap.gyro.getAngle(), setpoint);
			}
		});
		headingPID.set_setpoint(0);
		headingPID.set_pid(RobotMap.HEADING_TURN_KP, RobotMap.HEADING_TURN_KI, RobotMap.HEADING_TURN_KD);
	}
	public void setSetpoint(double s) {
		setpoint = s;
	}
	int i = 0;
	
	public void update(double dt) {
		headingPID.update(dt);
		if (i > 50) { 
			//System.out.println("Gryo Angle: " + RobotMap.gyro.getAngle());
			//System.out.println("dt: " + dt);
			//System.out.println("Fused Heading: " + RobotMap.gyro.getFusedHeading());
			//System.out.println(headingPID.get());
			i = 0;
		}
		i++;
	}
	
	public double get(){
		return headingPID.get();
	}
	// a = where you are
	// b = where you want to go
	public static double find_smallest_angle( double a, double b ) {
	    return -1 * ( ( a - b ) - 360.0 * Math.round( ( a - b ) / 360.0 ) );
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
