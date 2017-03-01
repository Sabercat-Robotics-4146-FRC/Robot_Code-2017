package org.usfirst.frc.team4146.robot;

import org.usfirst.frc.team4146.robot.Robot.servo_state;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;

public class Shooting {
	
	enum linear_servo_state {
		extending,
		retracting
	}
	servo_state linear_servo_state = servo_state.extending;
	
	private double linear_servo_time_accumulator = 0.0;
	private boolean linear_servo_oscillate_flag = false;
	
	CANTalon master_shooter;
	CANTalon slave_shooter;
	
	Talon shooter_intake;
	Talon vibrator;
	
	Servo linear_servo;
	
	public Shooting( CANTalon master_shooter, CANTalon slave_shooter, Talon shooter_intake, Talon vibrator,  Servo linear_servo ) {
		this.master_shooter = master_shooter;
		this.slave_shooter = slave_shooter;
		this.shooter_intake = shooter_intake;
		this.vibrator = vibrator;
		this.linear_servo = linear_servo;
	}
	
	/* Linear Servo Control */
	
	public void servoOscillationOff() {
		linear_servo_oscillate_flag = false;
	}
	
	public void servoOscillationOn() {
		linear_servo_oscillate_flag = true;
	}
	
	public void oscillateServo( double dt ) {
		if( linear_servo_oscillate_flag ) {
			linear_servo_time_accumulator += dt;
			if( linear_servo_time_accumulator > 5.5 ) {
				switch ( linear_servo_state ) {
					case extending:
						this.linear_servo.set( 0.2 );
						linear_servo_time_accumulator = 0.0;
						linear_servo_state = servo_state.retracting;
						break;
					case retracting:
						this.linear_servo.set( 0.7 );
						linear_servo_time_accumulator = 0.0;
						linear_servo_state = servo_state.extending;
						break;
					default:
						System.out.println( "Defaulted in linear_servo_state!" );
						break;
				}
			}
		}
	}
}
