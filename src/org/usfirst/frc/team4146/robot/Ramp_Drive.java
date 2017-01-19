package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;

public class Ramp_Drive {
	
	public double speed = 0.0;
	public double accelerate_rate = 0.65;
	public double decelerate_rate = 1.0;
	public double dt = 1e-3;
	public long system_time = System.nanoTime();
	private double ctrl_deadband = 0.15;
	private double mech_deadband = 0.3;
	private double mSlope;
	private double bIntercept;
	private double targetSpeed;
	private Controller drive_controller;
	private RobotDrive drive;
	
	Ramp_Drive( Controller dc, RobotDrive d ){
		drive_controller = dc;
		drive = d;
		mSlope = ((1.0 - mech_deadband) / (1.0 - ctrl_deadband));
		bIntercept = ((-ctrl_deadband * mSlope) + mech_deadband);
		
	}
	
	public void ramp_drive(){
		dt = ( System.nanoTime() - system_time ) * 1e-9;
		system_time = System.nanoTime();
		double left_y = -drive_controller.get_left_y_axis();
		
		left_y = check_speed(left_y);
		drive.arcadeDrive( speed, -1 * drive_controller.get_right_x_axis() );
		System.out.printf("% 5.2f -- % 5.2f -- % 5.2f \n", left_y, targetSpeed, speed );
		Timer.delay(0.005);	
	}
	public double check_speed(double left_y) {
		left_y = controller_deadband(left_y);
		mechanical_deadband(left_y);
		targetSpeed = find_target_speed(left_y);
		//quick_stop(left_y);
		if(/*(targetSpeed == 0.0) &&*/ ((speed > -mech_deadband) && (speed < mech_deadband))){
			speed = 0.0;
		}
		else if ( ( targetSpeed > speed) && ( targetSpeed > 0)){ //Traveling forward but speed is not fast enough
			speed +=  accelerate_rate * dt;
		}
		else if(( targetSpeed < speed) && ( targetSpeed < 0)){ //Traveling backwards but speed is not fast enough
			speed -= accelerate_rate * dt;
		}
		else if(( targetSpeed < speed) && ( targetSpeed >= 0)){//Traveling forwards but speed is too fast
			speed -= decelerate_rate * dt;
		}
		else if( ( targetSpeed > speed) && ( targetSpeed <= 0)){//Traveling backwards but speed is too fast
			speed +=  decelerate_rate * dt;
		}
		
		return left_y;
	}
	private void quick_stop(double left_y){	//if speed is opposite sign of left y then instant stop //Probably Useless
		if(((left_y > 0) && (speed < 0)) || ((left_y < 0) && (speed > 0))){
			speed = 0.0;
		}
	}
	private double controller_deadband(double l_y){
		if((l_y < ctrl_deadband) && (l_y > -ctrl_deadband))
		{
			return (double)(0.0);
		}
		else
		{
			return l_y;
		}
	}
	private void mechanical_deadband(double left_y){
		if((left_y > 0) && (speed == 0.0)){
			speed = mech_deadband;
		}
		else if((left_y < 0) && (speed == 0.0)){
			speed = -mech_deadband;
		}
	}
	private double find_target_speed( double left_y ){
		if( left_y == 0.0 ){
			return 0.0;
		}else if(left_y > 0.0){
			return ( ( mSlope * left_y) + bIntercept );
		}else if(left_y < 0.0){
			return ( ( mSlope * left_y) - bIntercept );
		}
		return 0.0;
	}
	
}
