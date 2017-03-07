package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team4146.robot.PID.*;

public class Move_Distance {
	private final double ENCODER_TICKS_PER_REVOLUTION = 360.0; //79% sure / rocky trust
	private final double WHEEL_DIAMETER = 7.625; // Inches
	private final double ENCODER_CONVERSION = (  ENCODER_TICKS_PER_REVOLUTION / ( WHEEL_DIAMETER * Math.PI ) ) * 12;// YAY.
	//Tested ticks per foot is 1300
	private final double ENCODER_RAW_CONVERSION = 1300; //ticks per foot
	
	Encoder right_drive_encoder;
	Encoder left_drive_encoder;
	
	public PID move_pid;
	
	Move_Distance( Encoder r, Encoder l ) {
		right_drive_encoder = r;
		left_drive_encoder = l;
		
		reset();
		
		move_pid = new PID( new signal() {
			public double getValue() {
				return convert_to_feet( encoder_distance() );
			}
		});
		move_pid.set_pid( 0.2, 0, 0 );
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
	}
	
	public void set_pid( double p, double i, double d ) {
		move_pid.set_pid( p, i ,d );
	}
	
	public void set_setpoint( double s ) {
		move_pid.set_setpoint( s );
	}
	
	public void set_distance(double d) {
		move_pid.set_setpoint( d );
	}
	// Converts encoder ticks to feet
	private double convert_to_feet( double e ) {
		return ( e / ENCODER_CONVERSION );
	}
	// Returns average encoder ticks
	private double encoder_distance() {
//		return ( ( right_drive_encoder.getRaw() + left_drive_encoder.getRaw() ) / 2 ); // Practice bot has broken left encoder
		return right_drive_encoder.getRaw();
	}
	
	public double get_steady_state_error() {
		return move_pid.steady_state_error();
	}
}
