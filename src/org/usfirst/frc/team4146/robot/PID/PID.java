package org.usfirst.frc.team4146.robot.PID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * General purpose PID loop.
 * @author Gowan Rowland
 * @version 9/8/2016
 */
public class PID {
	private double Kp;
	private double Ki;
	private double Kd;
	private double prevError;
	private double setpoint;
	//private double integral;
	private double error;
	private double derivative;
	private signal functions;
	private double output;

	/* Prevent Integral windup with a SizedStack */
	private SizedStack integral_stack;
	/* Make derivative filter with SizedStack */
	private SizedStack derivative_stack;
	/* Make error tolerance stack, used for steady state error breakouts */
	private SizedStack error_stack;
	
	private boolean sp_ramp_enabled;
	PID sp_ramp_pid;

	public PID ( signal functions ){
		sp_ramp_enabled = false;
		this.functions = functions;
		//integral = 0;
		setpoint = 0;
		integral_stack = new SizedStack( 10 );
		derivative_stack = new SizedStack( 3 );
		error_stack = new SizedStack( 5 );
	}
	public void set_integral_range( int n ) {
		integral_stack.resize( n );
	}
	public void set_derivative_range( int n ) {
		derivative_stack.resize( n );
	}
	public void set_setpoint( double s ){
		if ( sp_ramp_enabled ) {
			sp_ramp_pid.set_setpoint( s );
		}
		setpoint = s;
	}
	public void set_pid( double p, double i, double d ) {
		this.Kp = p;
		this.Ki = i;
		this.Kd = d;
	}
	public void set_sp_ramp( PID sp_pid ) {
		sp_ramp_enabled = true;
		sp_ramp_pid = sp_pid;
	}
	public double steady_state_error() {
		return error_stack.mean();
	}
	public void update( double dt ){
		if ( sp_ramp_enabled ) {
			sp_ramp_pid.update( dt );
			setpoint = sp_ramp_pid.get();
		}
		error = setpoint - functions.getValue();
		error_stack.push( error );
		integral_stack.push( ( Ki * error * dt ) );

		derivative = ( error - prevError ) / dt;// inconsistent with the github version, swapped vars.
		derivative_stack.push( derivative );
		prevError = error;
		output = ( Kp * error ) + ( integral_stack.sum() ) + ( Kd * derivative_stack.mean() ); // Note: Should we use mean derivative filter?
		SmartDashboard.putNumber("P out", Kp * error);
		SmartDashboard.putNumber("I out", integral_stack.sum());
		SmartDashboard.putNumber("D out", Kd * derivative_stack.mean() );

	}
	public double get() {
		return output;
	}
}
