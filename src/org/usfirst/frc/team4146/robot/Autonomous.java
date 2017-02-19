package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.PID;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	Heading heading;
	MoveDistance distance;
	RobotDrive drive;
	
	IterativeTimer timer = new IterativeTimer();
	
	private final double acceptable_distance_error = 0.5;
	private final double acceptable_angle_error = 10.0;
	private final double timeOut = 5.0; //Timeout time for each command. If time elapsed since the start of the command passes this value, it should stop.
	
	Autonomous(Heading h, MoveDistance md, RobotDrive rD) {
		heading = h;
		distance = md;
		drive = rD;
//		heading.set_pid( 0.05, 0, 0);
	}
	
	public void move_forward( double dis ) {
		distance.reset();
		distance.set_distance(dis);
		timer.reset();
		double dt = timer.get_dt();
		//heading.update( dt );
		distance.update( dt );
		System.out.println("POINT 1");
		while((Math.abs(distance.get_steady_state_error()) > acceptable_distance_error) && (timer.timeSinceStart() < timeOut)) {
			System.out.println("Point 2");
			System.out.printf("TimePassed: %f \n", timer.timeSinceStart());
			
			timer.update();
			dt = timer.get_dt();
			
			// Update subsystem PIDs
//			heading.update( dt );
			distance.update( dt );
			SmartDashboard.putNumber("Distance Pid", PID.clamp(distance.get(), 0.6));
			drive.arcadeDrive( PID.clamp(distance.get(), 0.6), 0.0);
		}
	}
	
//	public void turn_to_angle(double angle, double acceptable_error) {
//		timer.reset();
//		heading.turn( angle );
//		
//		while( /*( Math.abs( heading.get_steady_state_error() ) > acceptable_angle_error ) &&*/ ( timer.timeSinceStart() < timeOut ) )
//		{
//			timer.update();
//			double dt = timer.get_dt();
//			heading.update( dt );
//			System.out.println( heading.get() );
//			drive.arcadeDrive(0.0, heading.get() );
//		}
//	}
}
