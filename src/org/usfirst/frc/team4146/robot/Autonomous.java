package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	Heading heading;
	Move_Distance distance;
	RobotDrive drive;
	
	Iterative_Timer timer = new Iterative_Timer();
	
	private final double acceptable_distance_error = 0.5;
	private final double acceptable_angle_error = 10.0;
	private final double defaultTimeOut = 5.0;// Default timeout value in seconds
	
	Autonomous(/*Heading h,*/ Move_Distance md, RobotDrive rd) {
//		heading = h;
		distance = md;
		drive = rd;
//		heading.set_pid( 0.05, 0, 0);
		distance.set_pid(0.2, 0.0, 0.01);
	}
	
	public void move_forward( double dis, double timeOut ) {// uses given timeout value
		double dt;
		distance.reset();
		distance.set_distance(dis);
//		heading.set_heading(); // <-- Do we want this? will we ever want to go and turn at the same time? or just go forward?
		timer.reset();
		
		distance.move_pid.fill_error( 10 );
		double clamp = 0.0;
		do {
			timer.update();
			dt = timer.get_dt();
			clamp += (1 * dt); 
			// Update subsystem PIDs
			distance.update( dt );
//			heading.update( dt );
			
			drive.arcadeDrive( PID.clamp(distance.get(), clamp), /*heading.get()*/ 0.0 );
			SmartDashboard.putNumber("Move PID out, Unclamped", distance.get());
			
		} while((distance.get_steady_state_error() > acceptable_distance_error) && (timer.timeSinceStart() < timeOut));
		
	}
	
	public void move_forward( double dis ) {// uses default timeout value
		move_forward( dis, defaultTimeOut);
	}
	
	public void turn_to_angle(double angle, double timeOut) {// uses given timeout value
		timer.reset();
		heading.rel_angle_turn( angle );
		
		while((heading.get_steady_state_error() > acceptable_angle_error ) && ( timer.timeSinceStart() < timeOut ) )
		{
			timer.update();
			double dt = timer.get_dt();
			heading.update( dt );
			System.out.println( heading.get() );
			drive.arcadeDrive(0.0, heading.get() );
		}
	}
	public void turn_to_angle( double angle ) {// uses default timeout value
		turn_to_angle(angle, defaultTimeOut);
	}
}
