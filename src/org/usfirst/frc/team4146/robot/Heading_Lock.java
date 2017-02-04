package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.RobotDrive;
import com.kauailabs.navx.frc.AHRS;
//import edu.wpi.first.wpilibj.Timer;

/**
 * The Heading_Lock class automatically adjusts any drift while holding the a button, 
 * from starting bearing that is initially set by pressing b
 * 
 * @author Christopher Campanella
 *
 */
public class Heading_Lock{
	
	private static final double turn_rate = 0.25;
	
	private double speed = 0.0;
	private Controller drive_controller;
	private RobotDrive drive;
	private double angle = 0.0;
	private double gyro_start;
	private AHRS gyro;
	private boolean gyro_reset_flag = true;
	
	Heading_Lock(Controller dc, RobotDrive d, AHRS giro) {
		gyro = giro;
		drive_controller = dc;
		drive = d;
	}
	
	public void heading_lock() {
		speed = -drive_controller.get_left_y_axis();
		if(drive_controller.get_b_button()) {
			gyro_start = gyro.getFusedHeading();
		}
		if(drive_controller.get_a_button()) {
			if(gyro_reset_flag){
				//gyro_start = gyro.getFusedHeading();
				gyro_reset_flag = false;
			}	
			if(gyro.getFusedHeading() > gyro_start + 1) {
				angle = turn_rate;
			}
			else if(gyro.getFusedHeading() < gyro_start - 1) {
				angle = -turn_rate;
			}
			else {
				angle = 0;
			}
		}
		else {
			gyro_reset_flag = true;
			angle = -drive_controller.get_right_x_axis();
		}
		drive.arcadeDrive(speed, angle);
	}
}
