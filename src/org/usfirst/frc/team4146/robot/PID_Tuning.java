package org.usfirst.frc.team4146.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PID_Tuning {
	
	private Heading rHeading;
	private Controller driveCont;
	private AHRS gy;
	
	private Preferences prefs = Preferences.getInstance();
	private double p_pref = 0.0;
	private double i_pref = 0.0;
	private double d_pref = 0.0;
	
	private boolean aLast = true;
	private boolean bLast = true;
	private boolean yLast = true;
	
	private double turn_angle = 30.0;
	private double turnism = 0.0;
	
	private Iterative_Timer pidTuningControlDt;
	
	PID_Tuning(Heading rH, Controller dC, AHRS g) {
		rHeading = rH;
		driveCont = dC;
		gy = g;
	}
	
	public void checkButtons()
	{
		
		pidTuningControlDt.update();
		if(driveCont.get_a_button() && aLast) {
			rHeading.set_heading();
			aLast = false;
		}
		else if( !driveCont.get_a_button() ) {
			aLast = true;
		}
	
		if(driveCont.get_b_button() && bLast) {
//			rH.rel_angle_turn(-driveCont.get_deadband_right_x_axis() * 180);
			rHeading.rel_angle_turn(turn_angle);

			bLast = false;
		}
		else if( !driveCont.get_b_button() ) {
			bLast = true;
		}
	

		if(driveCont.get_y_button() && yLast) {
			p_pref = prefs.getDouble("PValue", 0.0);
			i_pref = prefs.getDouble("IValue", 0.0);
			d_pref = prefs.getDouble("DValue", 0.0);
			turn_angle = prefs.getDouble("TurnAngle", 0.0);
			rHeading.set_vars(p_pref, i_pref, d_pref, 0.0);
			SmartDashboard.putNumber("P", p_pref);
			SmartDashboard.putNumber("I", i_pref);
			SmartDashboard.putNumber("D", d_pref);
			SmartDashboard.putNumber("turnAngle", turn_angle);
			rHeading.set_heading();
			yLast = false;
		}
		else if( !driveCont.get_y_button() ) {
			yLast = true;
		}
		turnism = rHeading.heading(pidTuningControlDt.getDt());

		SmartDashboard.putNumber("gyroFusedHeading", gy.getFusedHeading());
		SmartDashboard.putNumber("HeadingOutput", turnism);
	}
	
	public double getTurnism() {
		return turnism;
	}
}
