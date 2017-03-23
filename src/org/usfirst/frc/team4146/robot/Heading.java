package org.usfirst.frc.team4146.robot;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team4146.robot.PID.*;

public class Heading {
	
	public AHRS gyro;
	public PID heading_pid;

	
	private double tempSetPoint;
	private double setPoint;
	
	
	Heading( AHRS g) {
		gyro = g;		
		
		heading_pid = new PID( new signal() {
			public double getValue() {
				return get_ang_diff( gyro.getFusedHeading(), setPoint);
			}
		}, false, "Heading", 30); //Doesn't start the integral until within 30 degrees, might be a really bad idea
		
		heading_pid.set_setpoint(0.0); // this pid always tries to go to zero
		//heading_pid.set_error_range( 16 );
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
		reset_pid();
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
		reset_pid();
	}
	
	private double get_ang_diff(double position, double setpoint) {
		
	    tempSetPoint = setpoint - position;
	    
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
	public void reset_pid() {
		heading_pid.set_integral_sum(0.0);
		heading_pid.take_prev_error_value();
	}
	public double get_fused_heading() {
		return gyro.getFusedHeading();
	}
}
