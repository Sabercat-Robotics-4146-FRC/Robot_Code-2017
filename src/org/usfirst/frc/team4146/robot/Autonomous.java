package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.RobotDrive;

public class Autonomous {
	Heading heading;
	Move_Distance distance;
	RobotDrive drive;
	
	Iterative_Timer timer = new Iterative_Timer();
	
	private final double acceptable_distance_error = 0.5;
	private final double acceptable_angle_error = 10.0;
	private final double timeOut = 5.0; //Timeout time for each command. If time elapsed since the start of the command passes this value, it should stop.
	
	Autonomous(Heading h, Move_Distance md, RobotDrive rD) {
		heading = h;
		distance = md;
		drive = rD;
//		heading.set_pid( 0.05, 0, 0);
	}
	
	public void move_forward( double dis ) {
		double dt;
		distance.reset();
		distance.set_distance(dis);
		timer.reset();
		
		do {
			timer.update();
			dt = timer.get_dt();
			
			// Update subsystem PIDs
			distance.update( dt );
//			heading.update( dt );
			
			drive.arcadeDrive( distance.get(), heading.get() );
		} while((Math.abs(distance.get_steady_state_error()) > acceptable_distance_error) && (timer.timeSinceStart() < timeOut));
		
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
