package org.usfirst.frc.team4146.robot.PID;

/**
 * General purpose PID loop.
 * @author Gowan Rowland
 * @version 6/24/2017
 */
public class PID {
	private double Kp;
	private double Ki;
	private double Kd;
	private double prevError;
	private double setpoint;
	private double integral;
	private double error;
	private double derivative;
	private signal func;
	private double output;

	public PID (signal functions){
		func = functions;
		setpoint = 0;
		integral = 0;
	}
	public void set_setpoint(double s){
		setpoint = s;
	}
	public void set_pid(double p, double i, double d) {
		this.Kp = p;
		this.Ki = i;
		this.Kd = d;
	}
	public void update(double dt){
		error = setpoint - func.getValue();
		integral += (Ki * error * dt);
		derivative = (error - prevError) / dt;
		prevError = error;
		output = (Kp * error) + integral + (Kd * derivative); // Note: Should we use mean derivative filter?
	}

	public double get() {
		return output;
	}
}