package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.Spark;
import com.ctre.CANTalon;
import com.ctre.CANTalon.*;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Autonomous {
	Heading heading;
	Move_Distance distance;
	RobotDrive drive;
	Vision autoVision;
	Iterative_Timer timer = new Iterative_Timer();
	
	//Constants
	private final double ACCEPTABLE_DISTANCE_ERROR = 0.083; // Used to be 0.083
	private final double ACCEPTABLE_ANGLE_ERROR = 1.0; // Used to be 1.0
	private final double DEFAULT_TIME_OUT = 5.0;
	private final double MAX_MOVE_SPEED = 0.8; //0.7
	private final double MAX_TURN_SPEED = 0.7;
	private final double MAX_HEADING_TURN_SPEED = 0.7;
	private final double HEADING_LOCK_DISTANCE_LOOSEN_THRESHOLD = 4/12;	//Use to be 1 foot
	private final double HEADING_LOCK_ANGLE_LOOSEN_THRESHOLD = 0.00625;
	private final int WHILE_WAIT_TIME = 1;
	//Variables
	private static double headingTurnP;
	private static double headingTurnI;
	private static double headingTurnD;
	private static double headingMoveP;
	private static double headingMoveI;
	private static double headingMoveD;
	private static double looseHeadingMoveP;
	private static double looseHeadingMoveI;
	private static double looseHeadingMoveD;
	
	
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
			Iterative_Timer.waitMilli(WHILE_WAIT_TIME);
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
    		SmartDashboard.putNumber( "Heading Steady State Error", heading.get_steady_state_error());

			timer.update();
			dt = timer.get_dt();
			clamp += (1.5 * dt); // really REALLY getto pid ramp
			heading.update( dt );
		//	drive.arcadeDrive(0.0, PID.clamp(PID.clamp(heading.get(), clamp), MAX_TURN_SPEED));
			drive.arcadeDrive(0.0, PID.clamp( heading.get(), MAX_TURN_SPEED ) );
			//SmartDashboard.putNumber("Heading PID out", heading.get());
			//heading.heading_pid.print_pid();
			Iterative_Timer.waitMilli(WHILE_WAIT_TIME);
			
		} while( ((heading.heading_pid.get_error() > ACCEPTABLE_ANGLE_ERROR) || (heading.get_steady_state_error() > ACCEPTABLE_ANGLE_ERROR)) && (timer.timeSinceStart() < timeOut) );
		drive.arcadeDrive( 0.0, 0.0 );
		System.out.println( "Done Turning! " + timer.timeSinceStart() + ": Dt = " + dt );
		/*while(timer.timeSinceStart() < (timeOut * 2 ))
		{
			SmartDashboard.putNumber( "Fused_Heading", heading.get_fused_heading());
    		SmartDashboard.putNumber( "Steady State Error", heading.get_steady_state_error());
    		heading.update( dt );
		}*/
	}
	
	public void turn( double angle ) {// uses default timeout value
		turn(angle, DEFAULT_TIME_OUT);
	}
	
	public void move_heading_lock( double dis, double timeOut) {
		set_heading_to_move();
		double dt;
		double clamp = 0.0;
		double spin;
		distance.reset();
		distance.set_distance(dis);
		timer.reset();
		distance.move_pid.fill_error( 1000 );
		heading.heading_pid.set_pid( headingMoveP, headingMoveI, headingMoveD);
		do {
			SmartDashboard_Wrapper.printToSmartDashboard( "Feet Moved", distance.right_drive_encoder.getRaw()/1300 );

    		//SmartDashboard.putNumber( "Fused_Heading", heading.get_fused_heading());
			
			timer.update();
			dt = timer.get_dt();
			clamp += (1 * dt); // really REALLY getto pid ramp
			// Update subsystem PIDs
			distance.update( dt );
			heading.update( dt );
			/*if(( Math.abs(heading.heading_pid.get_error()) > HEADING_LOCK_ANGLE_LOOSEN_THRESHOLD) && ( Math.abs(distance.move_pid.get_error()) > HEADING_LOCK_DISTANCE_LOOSEN_THRESHOLD)  ){
				heading.heading_pid.set_pid( headingMoveP, headingMoveI, headingMoveD);
			} else {
				heading.heading_pid.set_pid( looseHeadingMoveP, looseHeadingMoveI, looseHeadingMoveI );
			}*/
			spin = PID.clamp( heading.get(), MAX_HEADING_TURN_SPEED );
			if(( Math.abs(distance.move_pid.get_error()) < HEADING_LOCK_DISTANCE_LOOSEN_THRESHOLD)  ){
				spin = 0;
			}
			
			
			
			SmartDashboard_Wrapper.printToSmartDashboard( "Right_Encoder", distance.right_drive_encoder.getRaw() );
			SmartDashboard_Wrapper.printToSmartDashboard("Fused_Heading", heading.gyro.getFusedHeading());
			SmartDashboard_Wrapper.printToSmartDashboard("Forward Out", PID.clamp(PID.clamp(distance.get(), MAX_MOVE_SPEED), clamp));
			SmartDashboard.putNumber( "Heading Steady State Error", heading.get_steady_state_error());
			
	//		drive.arcadeDrive(PID.clamp(PID.clamp(distance.get(), clamp), MAX_MOVE_SPEED), heading.get());
			//SmartDashboard.putNumber("Move PID out, Unclamped", distance.get());
			drive.arcadeDrive(PID.clamp(PID.clamp(distance.get(), MAX_MOVE_SPEED), clamp), spin);
//			distance.networktable.putNumber("Distance P out", distance.move_pid.p_out());
//			distance.networktable.putNumber("Distance I out", distance.move_pid.i_out());
//			distance.networktable.putNumber("Distance D out", distance.move_pid.d_out() );
//			distance.networktable.putNumber("Heading P out", heading.heading_pid.p_out());
//			distance.networktable.putNumber("Heading I out", heading.heading_pid.i_out());
//			distance.networktable.putNumber("Heading D out", heading.heading_pid.d_out() );
			Iterative_Timer.waitMilli(WHILE_WAIT_TIME);
		} while((Math.abs(distance.get_steady_state_error()) > ACCEPTABLE_DISTANCE_ERROR) && (timer.timeSinceStart() < timeOut));
		drive.arcadeDrive( 0.0, 0.0 );
		System.out.println( "Done Moving Forward! " + timer.timeSinceStart() + " : dt is " + dt );
		//Timer.delay(1.0);
		timer.update();
		dt = timer.get_dt();
		heading.update(dt);
		System.out.println( "Final Error(FT): " + distance.move_pid.get_error());
		System.out.println( "Final Error(IN): " + distance.move_pid.get_error() * 12.0 );
	}
	
	public void shoot( CANTalon master_shooter, Talon ball_intake, Talon vibrator, Talon shooter_intake, double shooter_rpm_setpoint, double vibrator_speed, double shooter_rpm_tolerance, double shooter_intake_speed, double timeOut ){
		timer.reset();
		while( timer.timeSinceStart() < timeOut ){
			timer.update();
			master_shooter.enableControl(); // Allow talon internal PID to apply control to the talon
			master_shooter.changeControlMode(TalonControlMode.Speed);
			master_shooter.set( shooter_rpm_setpoint );
			
			// Network Table debugging
			SmartDashboard_Wrapper.printToSmartDashboard( "Shooter_RPM",  master_shooter.getSpeed() );
			SmartDashboard_Wrapper.printToSmartDashboard( "Shooter Error", master_shooter.getSpeed() - master_shooter.getSetpoint() );
			SmartDashboard_Wrapper.printToSmartDashboard( "Get value", master_shooter.get() );
			SmartDashboard_Wrapper.printToSmartDashboard( "Motor Output", master_shooter.getOutputVoltage() / master_shooter.getBusVoltage() );
			SmartDashboard_Wrapper.printToSmartDashboard( "Closed_Loop_Error", master_shooter.getClosedLoopError() );
			
			ball_intake.set( -0.3 );
			vibrator.set( vibrator_speed );
//			oscillate_servo();
			// Only feed balls to shooter if RPM is within a tolerance.
			if ( Math.abs( master_shooter.getSpeed() - master_shooter.getSetpoint() ) <= shooter_rpm_tolerance ) {
				shooter_intake.set( shooter_intake_speed );
			} else {
				shooter_intake.set( 0.0 );
			}
			
			
		}
		master_shooter.disableControl();
		ball_intake.set( 0 );
		vibrator.set( 0 );
		shooter_intake.set( 0 );
		System.out.println( "Shooting Done!" );
		
	}//End of shooter
	
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
			Iterative_Timer.waitMilli(WHILE_WAIT_TIME);
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
	public static void set_loose_heading_move_pid_values(double p, double i, double d) {
		looseHeadingMoveP = p;
		looseHeadingMoveI = i;
		looseHeadingMoveD = d;
	}
	private void set_heading_to_turn() {
		heading.set_pid(headingTurnP, headingTurnI, headingTurnD);
	}
	
	private void set_heading_to_move() {
		heading.set_pid(headingMoveP, headingMoveI, headingMoveD);
	}
	
	
}
