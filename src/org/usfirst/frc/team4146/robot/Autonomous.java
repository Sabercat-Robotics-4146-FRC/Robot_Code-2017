package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	Heading heading;
	Move_Distance distance;
	RobotDrive drive;
	Vision autoVision;
	Iterative_Timer timer = new Iterative_Timer();
	
	//Constants
	private final double ACCEPTABLE_DISTANCE_ERROR = 0.25;
	private final double ACCEPTABLE_ANGLE_ERROR = 1.0;
	private final double DEFAULT_TIME_OUT = 5.0;
	private final double MAX_MOVE_SPEED = 0.7;
	private final double MAX_TURN_SPEED = 0.7;
	
	//Variables
	private static double headingTurnP;
	private static double headingTurnI;
	private static double headingTurnD;
	private static double headingMoveP;
	private static double headingMoveI;
	private static double headingMoveD;
	
	Autonomous(Heading h, Move_Distance md, RobotDrive rd, Vision v) {
		heading = h;
		distance = md;
		drive = rd;
		autoVision = v;
	}
	
	public void move_forward( double dis, double timeOut ) {// uses given timeout value
		set_heading_to_move();
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
		//	distance.move_pid.print_pid();
//			SmartDashboard.putNumber("Gyro", heading.gyro.getFusedHeading());
			
		} while((distance.get_steady_state_error() > ACCEPTABLE_DISTANCE_ERROR) && (timer.timeSinceStart() < timeOut));
		
	}
	
	public void move_forward( double dis ) {// uses default timeout value
		move_forward( dis, DEFAULT_TIME_OUT);
	}
	
	public void turn(double angle, double timeOut) {// uses given timeout value
		double dt;
		double clamp = 0.0;
		set_heading_to_turn();
		timer.reset();
		heading.rel_angle_turn( angle );
		
		heading.heading_pid.fill_error( 1000 );
		System.out.println("running 1");

		do {
		//System.out.println("running 2");
    		SmartDashboard.putNumber( "Fused_Heading", heading.get_fused_heading());
    		SmartDashboard.putNumber( "Steady State Error", heading.get_steady_state_error());

			timer.update();
			dt = timer.get_dt();
			clamp += (1.5 * dt); // really REALLY getto pid ramp
			heading.update( dt );
		//	drive.arcadeDrive(0.0, PID.clamp(PID.clamp(heading.get(), clamp), MAX_TURN_SPEED));
			drive.arcadeDrive(0.0, PID.clamp( heading.get(), MAX_TURN_SPEED ) );
			//SmartDashboard.putNumber("Heading PID out", heading.get());
			//heading.heading_pid.print_pid();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// MOVE THIS TO ITERATIVE_TIMER
				System.out.println("Thread.sleep was Interrupted!");
				e.printStackTrace();
			}
		} while( ((heading.heading_pid.get_error() > ACCEPTABLE_ANGLE_ERROR) || (heading.get_steady_state_error() > ACCEPTABLE_ANGLE_ERROR)) && (timer.timeSinceStart() < timeOut) );
		drive.arcadeDrive( 0.0, 0.0 );
		System.out.println( "Done Turning! " + timer.timeSinceStart() + ": Dt = " + dt );
		while(timer.timeSinceStart() < (timeOut * 2 ))
		{
			SmartDashboard.putNumber( "Fused_Heading", heading.get_fused_heading());
    		SmartDashboard.putNumber( "Steady State Error", heading.get_steady_state_error());
    		heading.update( dt );
		}
	}
	
	public void turn( double angle ) {// uses default timeout value
		turn(angle, DEFAULT_TIME_OUT);
	}
	
	public void move_heading_lock( double dis, double timeOut) {
		set_heading_to_move();
		double dt;
		double clamp = 0.0;
		distance.reset();
		distance.set_distance(dis);
		timer.reset();
		distance.move_pid.fill_error( 1000 );
		do {
    		//SmartDashboard.putNumber( "Fused_Heading", heading.get_fused_heading());

			timer.update();
			dt = timer.get_dt();
			clamp += (1 * dt); // really REALLY getto pid ramp
			// Update subsystem PIDs
			distance.update( dt );
			heading.update( dt );
			SmartDashboard_Wrapper.printToSmartDashboard( "Right_Encoder", distance.right_drive_encoder.getRaw() );
			SmartDashboard_Wrapper.printToSmartDashboard("Fused_Heading", heading.gyro.getFusedHeading());

	//		drive.arcadeDrive(PID.clamp(PID.clamp(distance.get(), clamp), MAX_MOVE_SPEED), heading.get());
			//SmartDashboard.putNumber("Move PID out, Unclamped", distance.get());
			drive.arcadeDrive(PID.clamp(PID.clamp(distance.get(), MAX_MOVE_SPEED), clamp), heading.get());
//			distance.networktable.putNumber("Distance P out", distance.move_pid.p_out());
//			distance.networktable.putNumber("Distance I out", distance.move_pid.i_out());
//			distance.networktable.putNumber("Distance D out", distance.move_pid.d_out() );
//			distance.networktable.putNumber("Heading P out", heading.heading_pid.p_out());
//			distance.networktable.putNumber("Heading I out", heading.heading_pid.i_out());
//			distance.networktable.putNumber("Heading D out", heading.heading_pid.d_out() );
		} while((Math.abs(distance.get_steady_state_error()) > ACCEPTABLE_DISTANCE_ERROR) && (timer.timeSinceStart() < timeOut));
		drive.arcadeDrive( 0.0, 0.0 );
		System.out.println( "Done Moving Forward! " + timer.timeSinceStart() + " : dt is " + dt );
		
	}
	
	public void turnToGear(double timeOut) {
		set_heading_to_turn();
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
		set_heading_to_move();
		double dis = autoVision.get_distance(); //subtract .5 or something?
		move_heading_lock(dis, timeOut); //Dis Might have to be negative	
		
	}
	
	public static void set_heading_turn_pid_values(double p, double i, double d) {
		headingTurnP = p;
		headingTurnI = i;
		headingTurnD = d;
	}
	public static void set_heading_move_pid_values(double p, double i, double d) {
		headingMoveP = p;
		headingMoveI = i;
		headingMoveD = d;
	}
	
	private void set_heading_to_turn() {
		heading.set_pid(headingTurnP, headingTurnI, headingTurnD);
	}
	
	private void set_heading_to_move() {
		heading.set_pid(headingMoveP, headingMoveI, headingMoveD);
	}
	
	
}
