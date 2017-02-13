package org.usfirst.frc.team4146.robot;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team4146.robot.PID.*;

public class Autonomous {
	Heading roboHeading;
	Move_Distance roboMove;
	RobotDrive roboDrive;
	
	Iterative_Timer autoDT = new Iterative_Timer();
	
	private final double acceptable_distance_error = 0.5;
	private final double acceptable_angle_error = 10.0;
	private final double timeOut = 5.0; //Timeout time for each command. If time elapsed since the start of the command passes this value, it should stop.
	
	Autonomous(Heading h, Move_Distance md, RobotDrive rD) {
		roboHeading = h;
		roboMove = md;
		roboDrive = rD;
	}
	
	public void autoRun() {
		heading_Distance(5.0);
		turn_Angle(30.0, 5.0);
		
	}
	
	private void heading_Distance(double dis) {
		roboHeading.set_heading();
		roboMove.set_start();
		roboMove.set_distance(dis);
		autoDT.reset();
		
		while((Math.abs(roboMove.getSteadyStateError()) > acceptable_distance_error) && (autoDT.timeSinceStart() < timeOut)) {
			autoDT.update();
			roboDrive.arcadeDrive(roboMove.move_distance(autoDT.getDt()), roboHeading.heading(autoDT.getDt()));
		}
	}
	
	private void turn_Angle(double ang, double acceptable_error) {
		roboHeading.rel_angle_turn(ang);
		autoDT.reset();
		while((Math.abs(roboHeading.getSteadyStateError()) > acceptable_angle_error) && (autoDT.timeSinceStart() < timeOut))
		{
			autoDT.update();
			roboDrive.arcadeDrive(0.0, roboHeading.heading(autoDT.getDt()));
		}
	}
}
