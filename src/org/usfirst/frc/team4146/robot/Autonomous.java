package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;
import edu.wpi.first.wpilibj.Encoder;

/**
* Base Autonomous class.
* @author GowanR
* @version 2/1/2017
*/
class Autonomous {
  Robot robot;
  Encoder right_drive_encoder;
	Encoder left_drive_encoder;
  PID heading_pid;
  PID encoder_pid;
  static double encoder_conversion_factor = 666.666; // Hell number to be determined.
  
  public Autonomous ( Robot robot ) {
    this.robot = robot;
    // Instantiate drive encoders
		right_drive_encoder = new Encoder( 2, 3, false ); // false so to not reverse direction
		left_drive_encoder  = new Encoder( 0, 1, true );
    // Make all pids used in autonomous.
    encoder_pid = new PID( new signal() {
      public double getValue() {
        return convert_encoder_to_feet( (double) right_drive_encoder.getRaw() );
      }
    });
    // TODO: tweak encoder pid values.
    encoder_pid.set_pid( 0.5, 0, 0 );
    // Make Encoder PID
    
    // Make gyro PID
    heading_pid = new PID( new signal() {
      public double getValue() {
        // TODO: Make this angle absolute in 180 to -180 system to prevent wrapping behavior.
        return convert_wrapped_angle( robot.gyro.getAngle() );
      }
    });
    // TODO: tweak heading pid constants
    heading_pid.set_pid( 0.5, 0, 0 );
  }
  private double convert_wrapped_angle( double a ) {
    a = Math.abs( a );
    while ( a >= 360 ) {
      a -= 360;
    }
    double b = Math.abs(a) % 360;
    if ( a > 180 ) {
      a = -1 * ( 180 - ( a - 180 ) );
    }
    return a;
  }
  private double convert_encoder_to_feet( double e ) {
    return e * encoder_conversion_factor;
  }
  public void drive_forward( double feet ) {
    encoder_pid.set_setpoint( feet );
    heading_pid.set_setpoint( convert_wrapped_angle( robot.gyro.getAngle() ) );
    double dt = 1e-3;
    long start_time;
    right_drive_encoder.reset();
    while ( Math.abs( encoder_pid.steady_state_error() ) > 0.5 ) { // until the steady state error is lower than half a foot,
      start_time = System.nanoTime();
      // corect angle
      heading_pid.update( dt );
      encoder_pid.update( dt );
      robot.arcadeDrive( encoder_pid.get(), heading_pid.get()  );
      dt = ( start_time - System.nanoTime() ) * 1e-9; // convert nanosec to sec
    }
    // a while loop that breaks out with PID steady state error threshold, used pid update. 
  }
  public void turn_to ( double degrees ) {
    double dt = 1e-3;
    long start_time;
    while ( Math.abs( heading_pid.steady_state_error() ) > 5.0 ) { // until the steady state error is lower than 5 degrees,
      start_time = System.nanoTime();
      // corect angle
      heading_pid.update( dt );
      robot.arcadeDrive( 0, heading_pid.get() );
      dt = ( start_time - System.nanoTime() ) * 1e-9; // convert nanosec to sec
    }
    // a while loop that breaks out with PID steady state error threshold, used pid update. 
  }
}
