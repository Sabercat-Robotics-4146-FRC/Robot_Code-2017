package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team4146.robot.PID.*;

public class MoveDistance {
	private final double ENCODER_TICKS_PER_REVOLUTION = 360.0;
	private final double WHEEL_DIAMETER = 7.625;
	private final double ENCODER_CONVERSION = 12 * (ENCODER_TICKS_PER_REVOLUTION / (WHEEL_DIAMETER * Math.PI));// YAY.

	
	Encoder right_drive_encoder;
	Encoder left_drive_encoder;
	
	private PID move_pid;
	
	MoveDistance( Encoder r, Encoder l ) {
		right_drive_encoder = r;
		left_drive_encoder = l;
		
		reset();
		
		move_pid = new PID( new signal() {
			public double getValue() {
				return convert_to_feet( encoder_distance() );
			}
		}, false);// Non limited integral Stack
	}
	
	public void update(double dt) {		//Pass dt to function, which should be from Iterative_Timer	
		move_pid.update(dt);
	}
	
	public double get() {
		return move_pid.get();
	}
	
	public void reset() {
		left_drive_encoder.reset();
		right_drive_encoder.reset();
	}
	
	public void set_pid( double p, double i, double d ) {
		move_pid.set_pid( p, i ,d );
	}
	
	public void set_setpoint( double s ) {
		set_distance( s );
	}
	
	public void set_distance(double d) {
		move_pid.set_setpoint( d );
	}
	
	private double convert_to_feet(double f) {
		return (f * ENCODER_CONVERSION);
	}
	
	private double encoder_distance() {
		return ( ( right_drive_encoder.getRaw() + left_drive_encoder.getRaw() ) / 2 );
	}
	
	public double get_steady_state_error() {
		return move_pid.steady_state_error();
	}
}
