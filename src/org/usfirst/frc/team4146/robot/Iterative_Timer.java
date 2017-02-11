package org.usfirst.frc.team4146.robot;

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
		dt = convertNanoToSec(thisTime - lastTime);
		lastTime = thisTime;
	}
	public double getDt() {
		return dt;
	}
	
	
	public double convertNanoToSec(long nano) {		//Don't use this on a full time, only dt, as a long has many more digits than double.
		return (nano * (1e-9));
	}
	
	
	public double timeSinceStart() {
		return convertNanoToSec(System.nanoTime() - startTime);
	}
	public void resetStart() {
		startTime = System.nanoTime();
	}
}
