package org.usfirst.frc.team4146.robot;

import org.usfirst.frc.team4146.robot.PID.*;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PID_Tuner {
	
	private Controller drive_controller;
	private String name;
	private applyPID plant;
	private PID pid;
	
	
	
	private Preferences prefs = Preferences.getInstance();
	
	PID_Tuner( String name, PID pid, Controller drive_controller, applyPID plant ) {
		this.pid = pid;
		this.drive_controller = drive_controller;
		this.name = name;
		this.plant = plant;
		prefs.putDouble( name + "_p", 0.0 );
		prefs.putDouble( name + "_i", 0.0 );
		prefs.putDouble( name + "_d", 0.0 );
		prefs.putDouble( name +"_setpoint", 0.0);
	}
	public double update( double dt ) {		//added toReturn and changed void to double to return turn amount
		double toReturn = 0.0;
		pid.update( dt );
		if ( drive_controller.get_b_button() ) {
			double p = prefs.getDouble( name + "_p", 0.0 );
			double i = prefs.getDouble( name + "_i", 0.0 );
			double d = prefs.getDouble( name + "_d", 0.0 );
			double sp = prefs.getDouble( name +"_setpoint", 0.0);
			pid.set_pid( p, i, d );
			toReturn = sp;
			//pid.set_setpoint( sp ); //Commented out to tune heading
		}
		
		
		if ( drive_controller.get_a_button() ) {
			plant.apply( pid.get() );
		}
		
		return toReturn;
	}
}
