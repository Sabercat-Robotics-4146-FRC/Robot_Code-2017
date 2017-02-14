package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team4146.robot.PID.*;

public class Move_Distance {
	private final double ENCODER_TICKS_PER_REVOLUTION = 360.0;
	private final double WHEEL_DIAMETER = 7.625;
	private final double ENCODER_CONVERSION = ENCODER_TICKS_PER_REVOLUTION / (WHEEL_DIAMETER * Math.PI);// YAY.
	
	private double setPoint;
	private double rightStart;
	private double leftStart;
	private double startPoint;
	
	Encoder right_drive_encoder;
	Encoder left_drive_encoder;
	
	private PID move_pid;
	
	Move_Distance( Encoder r, Encoder l ) {
		right_drive_encoder = r;
		left_drive_encoder = l;
		
		set_start();
		
		move_pid = new PID( new signal() {
			public double getValue() {
				return convert_to_feet(encoder_error());
			}
		});
	}
	
	public double move_distance(double dt) {		//Pass dt to function, which should be from Iterative_Timer	
		
		move_pid.update(dt);
		return move_pid.get();
	}
	
	public void set_vars(double p, double i, double d, double s) {
		move_pid.set_pid(p,i,d);
		move_pid.set_setpoint(s);
	}
	
	public void set_start() {
		rightStart = right_drive_encoder.getRaw();
		leftStart = left_drive_encoder.getRaw();
		startPoint = ( leftStart + rightStart ) / 2;
	}
	
	public void set_distance(double d) {
		setPoint = startPoint + d * ENCODER_CONVERSION;
	}
	
	private double convert_to_feet(double f) {
		return (f * ENCODER_CONVERSION);
	}
	
	private double encoder_error() {
		return encoder_distance() - setPoint;
	}
	
	private double encoder_distance() {
		return ( (right_drive_encoder.getRaw() - rightStart) + (left_drive_encoder.getRaw() - leftStart) / 2 );
	}
	
	public double getSteadyStateError() {
		return move_pid.steady_state_error();
	}
}
