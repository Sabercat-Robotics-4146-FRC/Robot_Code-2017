package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;

/**
* Base Autonomous class.
* @author GowanR
* @version 2/1/2017
*/
class Autonomous {
  Robot robot;
  public Autonomous ( Robot robot ) {
    this.robot = robot;
    // Make all pids used in autonomous.
    // Make Encoder PID
    
    // Make gyro PID
    PID heading_pid = new PID( new signal () {
      double getValue() {
        // TODO: Make this angle absolute in 180 to -180 system to prevent wrapping behavior.
        return robot.gyro.getAngle();
      }
    });
    // TODO: tweak heading pid constants
    heading_pid.set_pid( 0.5, 0, 0 );
  }
  public void drive_forward( double feet ) {
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
