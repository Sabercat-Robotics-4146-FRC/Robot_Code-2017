package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team4146.robot.PID.*;

public class Move_Distance {
	private final double ENCODER_TICKS_PER_REVOLUTION = 360.0; //79% sure / rocky trust
	private final double WHEEL_DIAMETER = 7.625; // Inches
	private final double ENCODER_CONVERSION = (  ENCODER_TICKS_PER_REVOLUTION / ( WHEEL_DIAMETER * Math.PI ) ) * 12;// YAY.
	//Tested ticks per foot is 1300
	private final double ENCODER_RAW_CONVERSION = 1317; //ticks per foot
	
	Encoder right_drive_encoder;
	Encoder left_drive_encoder;
	
	
	public PID move_pid;
	
	Move_Distance( Encoder r, Encoder l) {
		right_drive_encoder = r;
		left_drive_encoder = l;
		
		move_pid = new PID( new signal() {
			public double getValue() {
				return convert_to_feet( encoder_distance() );
			}
		}, false, "Move Distance", 3);
		
		reset();
	}
	
	public void update(double dt) {		//Pass dt to function, which should be from Iterative_Timer	
		move_pid.update( dt );
	}
	
	// Returns distance in feet
	public double get() {
		return move_pid.get();
	}
	
	// Resets Encoders
	public void reset() {
		left_drive_encoder.reset();
		right_drive_encoder.reset();
		reset_pid();
	}
	
	public void set_pid( double p, double i, double d ) {
		move_pid.set_pid( p, i ,d );
	}
	
	public void set_setpoint( double s ) {
		move_pid.set_setpoint( s );
		reset_pid();
	}
	// ^v THESE TWO DO THE SAME THING??????????????????????
	public void set_distance(double d) {
		set_setpoint( d );
	}
	// Converts encoder ticks to feet
	private double convert_to_feet( double e ) {
		return ( e / ENCODER_RAW_CONVERSION );
	}
	// Returns average encoder ticks
	private double encoder_distance() {
		return ( ( right_drive_encoder.getRaw() + left_drive_encoder.getRaw() ) / 2 ); // Practice bot has broken left encoder
	}
	
	public double get_steady_state_error() {
		return move_pid.steady_state_error();
	}
	public void reset_pid() {
		move_pid.set_integral_sum(0.0);
		move_pid.take_prev_error_value();
	}
}