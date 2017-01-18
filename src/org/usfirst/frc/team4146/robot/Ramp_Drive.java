package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;

public class Ramp_Drive {
	
	public double speed = 0.0;
	public double accelerate_rate = 0.55;
	public double decelerate_rate = 1.0;
	public double dt = 1e-3;
	public long system_time = System.nanoTime();
	private double ctrl_deadband = 0.1;
	private double mech_deadband = 0.4;
	
	private Controller drive_controller;
	private RobotDrive drive;
	
	Ramp_Drive( Controller dc, RobotDrive d ){
		drive_controller = dc;
		drive = d;
		
	}
	
	public void ramp_drive(){
		dt = ( System.nanoTime() - system_time ) * 1e-9;
		system_time = System.nanoTime();
		double left_y = -drive_controller.get_left_y_axis();
		
		check_speed(left_y);
		drive.arcadeDrive( speed, -1 * drive_controller.get_right_x_axis() );
		System.out.println( speed );
		//Timer.delay(0.005);	
	}
	public void check_speed(double left_y) {
		left_y = controller_deadband(left_y);
		mechanical_deadband(left_y);    //FINISH THIS SHITLORD
		quick_stop(left_y);
		
		if ( (left_y > speed) && (left_y > 0)){ //Traveling forward but speed is not fast enough
			speed +=  accelerate_rate * dt;
		}
		else if((left_y < speed) && (left_y < 0)){ //Traveling backwards but speed is not fast enough
			speed -= accelerate_rate * dt;
		}
		else if((left_y < speed) && (left_y >= 0)){//Traveling forwards but speed is too fast
			speed -= decelerate_rate * dt;
		}
		else if( (left_y > speed) && (left_y <= 0)){//Traveling backwards but speed is too fast
			speed +=  decelerate_rate * dt;
		}
		
	}
	private void quick_stop(double left_y){	//if speed is opposite sign of left y then instant stop
		if(((left_y > 0) && (speed < 0)) || ((left_y < 0) && (speed > 0))){
			speed = 0.0;
		}
	}
	private double controller_deadband(double left_y){
		if((left_y < ctrl_deadband) && (left_y > -ctrl_deadband))
		{
			return 0.0;
		}
		else
		{
			return left_y;
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
	
}
