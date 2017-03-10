package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	Heading heading;
	Move_Distance distance;
	RobotDrive drive;
	Vision autoVision;
	Iterative_Timer timer = new Iterative_Timer();
	
	
	private final double ACCEPTABLE_DISTANCE_ERROR = 0.5;
	private final double ACCEPTABLE_ANGLE_ERROR = 10.0;
	private final double DEFAULT_TIME_OUT = 5.0;
	private final double MAX_MOVE_SPEED = 0.8;
	private final double MAX_TURN_SPEED = 0.6;
	
	Autonomous(Heading h, Move_Distance md, RobotDrive rd, Vision v) {
		heading = h;
		distance = md;
		drive = rd;
		autoVision = v;
	}
	
	public void move_forward( double dis, double timeOut ) {// uses given timeout value
		double dt;
		double clamp = 0.0;
		distance.reset();
		distance.set_distance(dis);
		timer.reset();
		distance.move_pid.fill_error( 1000 );
		do {
			timer.update();
			dt = timer.get_dt();
			clamp += (1 * dt); // really REALLY getto pid ramp
			// Update subsystem PIDs
			distance.update( dt );
			
			drive.arcadeDrive(distance.get(), 0.0 );
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
		
		heading.heading_pid.fill_error( 1000 );
		System.out.println("running 1");

		do {
		//System.out.println("running 2");
    		//SmartDashboard.putNumber( "Fused_Heading", heading.get_fused_heading());

			timer.update();
			dt = timer.get_dt();
			clamp += (1.5 * dt); // really REALLY getto pid ramp
			heading.update( dt );
		//	drive.arcadeDrive(0.0, PID.clamp(PID.clamp(heading.get(), clamp), MAX_TURN_SPEED));
			drive.arcadeDrive(0.0, heading.get());
			//SmartDashboard.putNumber("Heading PID out", heading.get());
			heading.heading_pid.print_pid();
		} while(/*(heading.get_steady_state_error() > ACCEPTABLE_ANGLE_ERROR ) && */( timer.timeSinceStart() < timeOut ) );
	}
	
	public void turn( double angle ) {// uses default timeout value
		turn(angle, DEFAULT_TIME_OUT);
	}
	
	public void move_heading_lock( double dis, double timeOut) {
		double dt;
		double clamp = 0.0;
		distance.reset();
		distance.set_distance(dis);
		timer.reset();
		distance.move_pid.fill_error( 100 );
		do {
    		SmartDashboard.putNumber( "Fused_Heading", heading.get_fused_heading());

			timer.update();
			dt = timer.get_dt();
			clamp += (1 * dt); // really REALLY getto pid ramp
			// Update subsystem PIDs
			distance.update( dt );
			heading.update( dt );
			
			drive.arcadeDrive(PID.clamp(PID.clamp(distance.get(), clamp), MAX_MOVE_SPEED), heading.get());
			SmartDashboard.putNumber("Move PID out, Unclamped", distance.get());
			
//			distance.networktable.putNumber("Distance P out", distance.move_pid.p_out());
//			distance.networktable.putNumber("Distance I out", distance.move_pid.i_out());
//			distance.networktable.putNumber("Distance D out", distance.move_pid.d_out() );
//			distance.networktable.putNumber("Heading P out", heading.heading_pid.p_out());
//			distance.networktable.putNumber("Heading I out", heading.heading_pid.i_out());
//			distance.networktable.putNumber("Heading D out", heading.heading_pid.d_out() );
		} while((distance.get_steady_state_error() > ACCEPTABLE_DISTANCE_ERROR) && (timer.timeSinceStart() < timeOut));

	}
	
	public void turnToGear(double timeOut) {
		double dt;
		timer.reset();
		//Fill Error?
		do {
			timer.update();
			dt = timer.get_dt();
			autoVision.update(dt);
			drive.arcadeDrive( 0.0, autoVision.get()); //autovision get might have to be negative
			
		}while((autoVision.get_steady_state_error() > ACCEPTABLE_ANGLE_ERROR) && (timer.timeSinceStart() < timeOut));
		
	}
	
	public void moveToGear( double timeOut ) {
		double dis = autoVision.get_distance(); //subtract .5 or something?
		move_heading_lock(dis, timeOut); //Dis Might have to be negative	
		}
}
