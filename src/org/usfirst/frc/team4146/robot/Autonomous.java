package org.usfirst.frc.team4146.robot;

import org.usfirst.frc.team4146.robot.PID.*;

import com.ctre.CANTalon.TalonControlMode;

public class Autonomous {
	
	double theoreticalAngle = 0.0;
	
	double dt;
	
	Timer timer;
	
	Autonomous() {
		timer = new Timer();
	}
	
	public void turn(double angle, double timeOut){
		double timeElapsed = 0.0;
		timer.reset();
		theoreticalAngle += angle;
		RobotMap.Heading.headingPID.set_setpoint(theoreticalAngle);
		RobotMap.Heading.headingPID.set_integral_sum(0);
		RobotMap.Heading.headingPID.fill_error(100);
		RobotMap.Heading.setTurnMode();
		
		int i = 0;
		
		while(RobotMap.ROBOT.isEnabled() && RobotMap.Heading.isNotInError(timeOut, timeElapsed)){
			RobotMap.GearAssembly.update();
			timer.update();
			dt = timer.getDT();
			RobotMap.Heading.update(dt);
			double turn = PID.clamp(RobotMap.Heading.get(), RobotMap.MAX_TURN_SPEED);
			//double turn =  RobotMap.Heading.get();
			timeElapsed += dt;
			RobotMap.drive.arcadeDrive(0.0, -turn );
			Dashboard.send("Heading Spin Error", RobotMap.Heading.headingPID.get_error());
			
			
			if(i > 30){
				System.out.println(RobotMap.gyro.getAngle());
				i = 0;
			}
			i++;
		}
		System.out.println("Finished Turning in: " + timeElapsed);
	}
	
	public void move(double distance, double timeOut) throws InterruptedException{
//		RobotMap.gyro.reset();
		double timeElapsed = 0.0;
		timer.reset();
		RobotMap.MoveDistance.resetEncoders();
		RobotMap.MoveDistance.moveDistancePID.set_setpoint(distance);
		RobotMap.MoveDistance.moveDistancePID.set_integral_sum(0);
		RobotMap.MoveDistance.moveDistancePID.fill_error(100);
		RobotMap.MoveDistance.moveDistancePID.set_error_range(200);
		//RobotMap.MoveDistance.setLockMode();
		RobotMap.Heading.headingPID.set_setpoint(RobotMap.gyro.getAngle());
		RobotMap.Heading.headingPID.set_integral_sum(0);
		RobotMap.Heading.headingPID.fill_error(100);
		RobotMap.Heading.setLockMode();
		
		int i = 0;
		
		while(RobotMap.ROBOT.isEnabled() && RobotMap.MoveDistance.isNotInError(timeOut, timeElapsed)){
			RobotMap.GearAssembly.update();
			timer.update();
			dt = timer.getDT();
			RobotMap.MoveDistance.update(dt);
			RobotMap.Heading.update(dt);
			double move = PID.clamp(RobotMap.MoveDistance.get(), RobotMap.MAX_MOVE_SPEED);
			double spin = PID.clamp(RobotMap.Heading.get(), RobotMap.MAX_TURN_SPEED);
			timeElapsed += dt;
			RobotMap.drive.arcadeDrive(move, -spin);
			Dashboard.send("Move Distance Error", RobotMap.MoveDistance.moveDistancePID.get_error());
			Dashboard.send("Move Distance PID Out", RobotMap.MoveDistance.moveDistancePID.get());
			Dashboard.send("Left Drive Encoder", RobotMap.leftDriveEncoder.getRaw());
			Dashboard.send("Right Drive Encoder", RobotMap.rightDriveEncoder.getRaw());
			
			if(i > 30){
				//System.out.println((RobotMap.leftDriveEncoder.get() + RobotMap.rightDriveEncoder.get())/2.0);
				//Dashboard.send("Move Distance Error", RobotMap.MoveDistance.moveDistancePID.get_error());
				i = 0;
			}
			i++;
			Timer.waitMilli();
		}
		RobotMap.drive.arcadeDrive(0.0, 0.0);
		//Thread.sleep(10000);
		System.out.println("Move error: " + RobotMap.MoveDistance.moveDistancePID.get_error());
		System.out.println("Done moving in: " + timeElapsed);
	}
	
	public void placeGear() throws InterruptedException{
		RobotMap.gearWheelLeft.set(RobotMap.GEAR_RELEASE_SPEED);
		RobotMap.gearWheelRight.set(RobotMap.GEAR_RELEASE_SPEED);
		RobotMap.gearTilt.set(RobotMap.TILT_DOWN_POWER);
		
		Timer.waitTime(1000);
		
		this.moveNoGear(2.0, 2);
		
		RobotMap.gearWheelLeft.set(0.0);
		RobotMap.gearWheelRight.set(0.0);
		RobotMap.gearTilt.set(0.0);
		
		//Timer.waitTime(1000);
		
		RobotMap.GearAssembly.update();
	}
	
	public void shoot() {
		RobotMap.masterShooter.enableControl(); // Allow talon internal PID to apply control to the talon
		RobotMap.masterShooter.changeControlMode(TalonControlMode.Speed);
		RobotMap.masterShooter.set( RobotMap.AUTO_SHOOT_SPEED );
		
		RobotMap.ballIntake.set(-0.3);
		RobotMap.vibrator.set(RobotMap.VIBRATOR_SPEED);
		RobotMap.ShooterAssembly.oscillateServo();
		// Only feed balls to shooter if RPM is within a tolerance.
		if (Math.abs(RobotMap.masterShooter.getSpeed() - RobotMap.masterShooter.getSetpoint())
				<= RobotMap.SHOOTER_RPM_TOLERANCE) {
			RobotMap.shooterIntake.set(RobotMap.SHOOTER_INTAKE_SPEED);
		} else {
			RobotMap.shooterIntake.set(0.0);
		}
		
		
	}
	
	public void moveNoGear(double distance, double timeOut) throws InterruptedException{
//		RobotMap.gyro.reset();
		double timeElapsed = 0.0;
		timer.reset();
		RobotMap.MoveDistance.resetEncoders();
		RobotMap.MoveDistance.moveDistancePID.set_setpoint(distance);
		RobotMap.MoveDistance.moveDistancePID.set_integral_sum(0);
		RobotMap.MoveDistance.moveDistancePID.fill_error(100);
		RobotMap.MoveDistance.moveDistancePID.set_error_range(200);
		//RobotMap.MoveDistance.setLockMode();
		RobotMap.Heading.headingPID.set_setpoint(RobotMap.gyro.getAngle());
		RobotMap.Heading.headingPID.set_integral_sum(0);
		RobotMap.Heading.headingPID.fill_error(100);
		RobotMap.Heading.setLockMode();
		
		int i = 0;
		
		while(RobotMap.ROBOT.isEnabled() && RobotMap.MoveDistance.isNotInError(timeOut, timeElapsed)){
			//RobotMap.GearAssembly.update();
			timer.update();
			dt = timer.getDT();
			RobotMap.MoveDistance.update(dt);
			RobotMap.Heading.update(dt);
			double move = PID.clamp(RobotMap.MoveDistance.get(), RobotMap.MAX_MOVE_SPEED);
			double spin = PID.clamp(RobotMap.Heading.get(), RobotMap.MAX_TURN_SPEED);
			timeElapsed += dt;
			RobotMap.drive.arcadeDrive(move, -spin);
			Dashboard.send("Move Distance Error", RobotMap.MoveDistance.moveDistancePID.get_error());
			Dashboard.send("Move Distance PID Out", RobotMap.MoveDistance.moveDistancePID.get());
			Dashboard.send("Left Drive Encoder", RobotMap.leftDriveEncoder.getRaw());
			Dashboard.send("Right Drive Encoder", RobotMap.rightDriveEncoder.getRaw());
			
			if(i > 30){
				//System.out.println((RobotMap.leftDriveEncoder.get() + RobotMap.rightDriveEncoder.get())/2.0);
				//Dashboard.send("Move Distance Error", RobotMap.MoveDistance.moveDistancePID.get_error());
				i = 0;
			}
			i++;
			Timer.waitMilli();
		}
		RobotMap.drive.arcadeDrive(0.0, 0.0);
		//Thread.sleep(10000);
		System.out.println("Move error: " + RobotMap.MoveDistance.moveDistancePID.get_error());
		System.out.println("Done moving in: " + timeElapsed);
	}
}
