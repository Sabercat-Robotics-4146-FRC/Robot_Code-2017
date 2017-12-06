package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;

public class HeadingPID extends PID {
	public HeadingPID() {
		super(new signal() {
			public double getValue(){
				return RobotMap.gyro.getAngle();
			}
		});
		super.set_pid(0.004, 0.0, 0.0);
	}
	@Override
	public double getSystemError(){
		return find_smallest_angle(RobotMap.gyro.getFusedHeading(), setpoint);
	}
	// a = where you are
	// b = where you want to go
	public static double find_smallest_angle( double a, double b ) {
		return -1 * ( ( a - b ) - 360.0 * Math.round( ( a - b ) / 360.0 ) );
	}
}
