package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;

public class Drive_Train {
	
	public double speed = 0.0;
	public double accelerate_rate = 0.55;
	public double decelerate_rate = 1.0;
	public double dt = 1e-3;
	public long system_time = System.nanoTime();
	
	private Controller drive_controller;
	private RobotDrive drive;
	
	Drive_Train( Controller dc, RobotDrive d ){
		drive_controller = dc;
		drive = d;
		
	}
	
	public void ramp_drive(){
		dt = ( System.nanoTime() - system_time ) * 1e-9;
		system_time = System.nanoTime();
		double left_y = drive_controller.get_left_y_axis();
		if ( left_y > speed ){ 
			speed +=  accelerate_rate * dt;
		}else if( left_y < speed ){
			speed -=  decelerate_rate * dt;
		}
		drive.arcadeDrive( -1 * speed, -1 * drive_controller.get_right_x_axis() );
		System.out.println( -1 * speed );
		Timer.delay(0.005);	
	}
}
