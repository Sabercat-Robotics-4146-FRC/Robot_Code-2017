package org.usfirst.frc.team4146.robot.PID;

//import org.usfirst.frc.team4146.robot.SD_Wrapper;

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
	protected double setpoint;
	//private double integral;
	private double error;
	private double derivative;
	private signal functions;
	private double output;
	
	/* Make derivative filter with SizedStack */
	/* Make error tolerance stack, used for steady state error breakouts */
	private SizedStack error_stack;
	private int error_stack_size = 100; //Default value
	private double error_stack_sample_rate = 0.001; //Default value, will push 100 times per second.
	private double error_stack_dt;
	
	private double integralSum = 0.0;
	private double integralActivationRange = 360.0;
	
	private boolean sp_ramp_enabled;
	PID sp_ramp_pid;
	

	public PID( signal functions ){
		sp_ramp_enabled = false;
		this.functions = functions;
		//integral = 0;
		setpoint = 0;
		error_stack = new SizedStack( error_stack_size );
	}
	
	public void setIntegralActivationRange(double i) {
		integralActivationRange = i;
	}
	
	public void set_error_range(int n ) {
		error_stack.resize( n );
		error_stack_size = n;
	}
	public void set_error_sample_rate(int n ) {
		error_stack_sample_rate = n;
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
		for ( int i = 0; i <= error_stack_size; i++ ) {
			error_stack.push( e );
		}
	}
	public double steady_state_error() {
		return error_stack.absolute_mean();
	}
	public double getSystemError(){
		return setpoint - functions.getValue();
	}
	int i = 0;
	double derivative_dt = 0;
	
	public void update( double dt ){
		error = getSystemError();
		error_stack_dt += dt;
		
		if( error_stack_dt > (error_stack_sample_rate)) {
			error_stack.push( error );
			error_stack_dt = 0.0;
			//System.out.println( error );
		}
		
		derivative_dt += dt;
		if(error != prevError) {
			derivative = ( error - prevError ) / derivative_dt;
			prevError = error;
			derivative_dt = 0;
		}
		
		if(Math.abs(error) < integralActivationRange)
		{
			integralSum += Ki * error * dt;	
		}
		
		output = ( Kp * error ) + ( integralSum )/* + ( Kd * derivative  )*/;
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
	public void take_prev_error_value() {
		
		prevError = setpoint - functions.getValue();
	}
	
	public double get_error() {
		return error;
	}
}