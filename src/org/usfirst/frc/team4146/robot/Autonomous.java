package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	Heading heading;
	Move_Distance distance;
	RobotDrive drive;
	
	Iterative_Timer timer = new Iterative_Timer();
	
	private final double ACCEPTABLE_DISTANCE_ERROR = 0.5;
	private final double ACCEPTABLE_ANGLE_ERROR = 20.0;
	private final double DEFAULT_TIME_OUT = 5.0;
	private final double MAX_MOVE_SPEED = 0.8;
	private final double MAX_TURN_SPEED = 0.6;
	
	Autonomous(Heading h, Move_Distance md, RobotDrive rd) {
		heading = h;
		distance = md;
		drive = rd;
	}
	
	public void move_forward( double dis, double timeOut ) {// uses given timeout value
		double dt;
		double clamp = 0.0;
		distance.reset();
		distance.set_distance(dis);
//		heading.set_heading(); // <-- Do we want this? will we ever want to go and turn at the same time? or just go forward?
		timer.reset();
		distance.move_pid.fill_error( dis );
		do {
			timer.update();
			dt = timer.get_dt();
			clamp += (1 * dt); // really REALLY getto pid ramp
			// Update subsystem PIDs
			distance.update( dt );
			heading.update( dt );
			
			drive.arcadeDrive(PID.clamp(PID.clamp(distance.get(), clamp), MAX_MOVE_SPEED), heading.get());
			SmartDashboard.putNumber("Move PID out, Unclamped", distance.get());
			
		} while((distance.get_steady_state_error() > ACCEPTABLE_DISTANCE_ERROR) && (timer.timeSinceStart() < timeOut));
		
	}
	
	public void move_forward( double dis ) {// uses default timeout value
		move_forward( dis, DEFAULT_TIME_OUT);
	}
	
	public void turn(double angle, double timeOut) {// uses given timeout value
		double dt;
		double clamp = 0.0;
		timer.reset();
		heading.rel_angle_turn( angle );
		
		heading.heading_pid.fill_error( 100 );
		System.out.println("running 1");

		do {
//			System.out.println("running 2");
		
			timer.update();
			dt = timer.get_dt();
			clamp += (1.5 * dt); // really REALLY getto pid ramp
			heading.update( dt );
			drive.arcadeDrive(0.0, PID.clamp(PID.clamp(heading.get(), clamp), MAX_TURN_SPEED));
			SmartDashboard.putNumber("Heading PID out", heading.get());

		} while((heading.get_steady_state_error() > ACCEPTABLE_ANGLE_ERROR ) && ( timer.timeSinceStart() < timeOut ) );
	}
	public void turn( double angle ) {// uses default timeout value
		turn(angle, DEFAULT_TIME_OUT);
	}
}
