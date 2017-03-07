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
	private int error_stack_size = 10;
	
	private boolean integralStackFlag;
	private double integralSum = 0.0;
	
	private boolean sp_ramp_enabled;
	PID sp_ramp_pid;

	public PID ( signal functions ){
		this.integralStackFlag = true;
		sp_ramp_enabled = false;
		this.functions = functions;
		//integral = 0;
		setpoint = 0;
		integral_stack = new SizedStack( 10 );
		derivative_stack = new SizedStack( 3 );
		error_stack = new SizedStack( error_stack_size );
	}
	
	public PID ( signal functions, boolean integralStackFlag ){
		this.integralStackFlag = integralStackFlag;
		sp_ramp_enabled = false;
		this.functions = functions;
		//integral = 0;
		setpoint = 0;
		integral_stack = new SizedStack( 10 );
		derivative_stack = new SizedStack( 3 );
		error_stack = new SizedStack( error_stack_size );
	}
	
	public void set_integral_range( int n ) {
		integral_stack.resize( n );
	}
	public void set_derivative_range( int n ) {
		derivative_stack.resize( n );
	}
	public void set_error_range(int n ) {
		error_stack.resize( n );
		error_stack_size = n;
	}
	public void set_setpoint( double s ){
		if ( sp_ramp_enabled ) {
			sp_ramp_pid.set_setpoint( s );
		}
		setpoint = s;
		integralSum = 0;
	}
	public void set_integral_sum( double i ) {
		integralSum = i;
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
	public void fill_error( double e ) {
		for ( int i = 0; i < error_stack_size; i++ ) {
			error_stack.push( e );
		}
		prevError = e;
	}
	public double steady_state_error() {
		return error_stack.absolute_mean();
	}
	public void update( double dt ){
		if ( sp_ramp_enabled ) {
			sp_ramp_pid.update( dt );
			setpoint = sp_ramp_pid.get();
		}
		error = setpoint - functions.getValue();
		error_stack.push( error );
		
		derivative = ( error - prevError ) / dt;// inconsistent with the github version, swapped vars.
		derivative_stack.push( derivative );
		prevError = error;
		
		if(integralStackFlag) {
			
			integral_stack.push( ( Ki * error * dt ) );
			output = ( Kp * error ) + ( integral_stack.sum() ) + ( Kd * derivative_stack.mean() ); // Note: Should we use mean derivative filter?
			SmartDashboard.putNumber("P out", Kp * error);
			SmartDashboard.putNumber("I out", integral_stack.sum());
			SmartDashboard.putNumber("D out", Kd * derivative_stack.mean() );
			
		}
		else {
			integralSum += Ki * error * dt;
			output = ( Kp * error ) + ( integralSum ) + ( Kd * /*derivative_stack.mean()*/ derivative ); // Note: Should we use mean derivative filter?
			
			SmartDashboard.putNumber("P out", Kp * error);
			SmartDashboard.putNumber("I out", integralSum);
			SmartDashboard.putNumber("D out", Kd * derivative_stack.mean() );
			
		}

		
	}
	public double get() {
		return output;
	}
	
	public static double clamp(double valueToClamp, double clampValue) {
		if(valueToClamp > clampValue)
		{
			return clampValue;
		} else if(valueToClamp < -(clampValue)) {
			return -(clampValue);
		}
		return valueToClamp;
	}
}
