package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.DriverStation;

public class Iterative_Timer {
	private long startTime = 0;
	private long thisTime = 0;
	private long lastTime = 0;
	private double dt = 0;
	Iterative_Timer() {
		startTime = System.nanoTime();
		thisTime = startTime;
		lastTime = startTime;
	}
	
	public void reset() {
		startTime = System.nanoTime();
		thisTime = startTime;
		lastTime = startTime;
	}
	
	public void update() {
		thisTime = System.nanoTime();
		dt = convert_nano_to_sec(thisTime - lastTime);
		lastTime = thisTime;
	}
	public double get_dt() {
		return dt;
	}
	
	public double convert_nano_to_sec(long nano) {		
			return (nano * (1e-9));
	}
	
	public double timeSinceStart() {
		return convert_nano_to_sec( System.nanoTime() - startTime );
	}
	public void reset_start() {
		startTime = System.nanoTime();
	}
	
	public static void waitMilli( int waitTime ) {
		try {
			Thread.sleep( waitTime );
		} catch (InterruptedException e) {
			// MOVE THIS TO ITERATIVE_TIMER
			//OK
			System.out.println("Thread.sleep was Interrupted in Iterative_Timer!");
			e.printStackTrace();
		}
	}
}
