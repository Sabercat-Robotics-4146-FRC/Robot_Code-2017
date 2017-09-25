package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;

public class MoveDistance {
	private PID moveDistancePID;
	
	public MoveDistance() {
		moveDistancePID = new PID(new signal(){
			public double getValue(){
				return RobotMap.leftDriveEncoder.get();
			}
		});
		moveDistancePID.set_pid(RobotMap.MoveDistance_KP, RobotMap.MoveDistance_KI, RobotMap.MoveDistance_KD);
	}
	
	int i = 0;
	public void update(double dt) {
		moveDistancePID.update(dt);
		
		if (i >= 20) {
			//System.out.println("Left Encoder: " + RobotMap.leftDriveEncoder.get());
			//System.out.println("Right Encoder: " + RobotMap.rightDriveEncoder.get());
			i = 0;
		}
		i++;
	}
	
	public double get() {
		return moveDistancePID.get();
	}
}
