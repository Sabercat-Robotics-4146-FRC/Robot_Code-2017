package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;

public class Heading {
	private PID headingPID;
	
	public Heading(){
		headingPID = new PID(new signal(){
			public double getValue(){
				return RobotMap.gyro.getAngle();
			}
		});
		headingPID.set_pid(RobotMap.HEADING_KP, RobotMap.HEADING_KI, RobotMap.HEADING_KD);
	}
	
	int i = 0;
	public void update(double dt) {
		headingPID.update(dt);
		if (i > 50) { 
			//System.out.println("Gryo Angle: " + RobotMap.gyro.getAngle());
			//System.out.println("dt: " + dt);
			//System.out.println("Gryo Angle: " + RobotMap.gyro.getAngle());
			//System.out.println(headingPID.get());
			i = 0;
		}
		i++;
	}
	public double get(){
		return headingPID.get();
	}
}
