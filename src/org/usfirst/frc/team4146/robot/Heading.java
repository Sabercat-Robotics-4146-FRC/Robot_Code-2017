package org.usfirst.frc.team4146.robot;
import com.kauailabs.navx.frc.AHRS;
import org.usfirst.frc.team4146.robot.PID.*;

public class Heading {
	
	private AHRS gyro;
	private PID heading_pid;


	private double tempSetPoint;
	private double setPoint;
	private long thisTime = 0;
	private long lastTime = System.nanoTime();
	private double dt = 0.0;
	
	Heading( AHRS g ) {
		gyro = g;		
		
		heading_pid = new PID( new signal() {
			public double getValue() {
				return get_ang_diff( gyro.getFusedHeading(), setPoint);
			}
		});
		
	}
	
	public double heading() {
		thisTime = System.nanoTime();					//Get Current System Time
		dt = ( thisTime - lastTime ) * 1e-9;			//Determine change in time from last loop to this one
		lastTime = thisTime;	
		
		heading_pid.update(dt);
		
		return heading_pid.get();
	}
	
	public void set_vars(double p, double i, double d, double s) {
		heading_pid.set_pid(p,i,d);
		heading_pid.set_setpoint(s);
	}
	
	public void set_heading() {
		setPoint = gyro.getFusedHeading();
	}
	
	public void rel_angle_turn(double change) {
		setPoint = ( setPoint + change );
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
	
}
