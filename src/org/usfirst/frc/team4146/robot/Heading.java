package org.usfirst.frc.team4146.robot;
import com.kauailabs.navx.frc.AHRS;
import org.usfirst.frc.team4146.robot.PID.*;

public class Heading {
	
	private AHRS gyro;
	private PID heading_pid;


	private double tempSetPoint;
	private double setPoint;
	
	
	Heading( AHRS g ) {
		gyro = g;		
		
		heading_pid = new PID( new signal() {
			public double getValue() {
				return get_ang_diff( gyro.getFusedHeading(), setPoint);
			}
		}, false);
		heading_pid.set_setpoint(0.0); // this pid always tries to go to zero
	}
	
	public void update(double dt) {		//Pass dt to function, which should be from Iterative_Timer
			
		heading_pid.update(dt);
	}
	
	public double get() {		//Get pid output	
		
		return heading_pid.get();
	}
	
	public void set_pid(double p, double i, double d) {
		heading_pid.set_pid(p,i,d);
		
	}
	
	public void set_heading() {
		setPoint = gyro.getFusedHeading();
		reset_integral_sum();
	}
	
	public void rel_angle_turn(double change) {
		setPoint = ( setPoint + change );
		if(setPoint > 360)
		{
			setPoint -= 360;
		}
		else if(setPoint < 0)
		{
			setPoint += 360;
		}
		reset_integral_sum();
	}
	
	private double get_ang_diff(double position, double setpoint) {
		
	    tempSetPoint = position - setpoint;
	    
	    if( tempSetPoint > 180 ) { 
	      tempSetPoint -= 360;
	    } else if(tempSetPoint < -180) {
	      tempSetPoint += 360;
	    }
	    return tempSetPoint;
    }
	
	public double get_steady_state_error() {
		return heading_pid.steady_state_error();
	}
	public void reset_integral_sum() {
		heading_pid.set_integral_sum(0.0);
	}
}
