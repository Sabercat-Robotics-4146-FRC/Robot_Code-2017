package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.DriverStation;

public class IterativeTimer {
	private long startTime = 0;
	private long thisTime = 0;
	private long lastTime = 0;
	private double dt = 0;
	
	public IterativeTimer() {
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
	
	
	public double convert_nano_to_sec(long nano) {		//Don't use this on a full time, only dt, as a long has many more digits than double.
		try{
			return (nano * (1e-9));
		} catch( RuntimeException ex ) {
			DriverStation.reportWarning("Likely an overflow in conversion: " + ex.getMessage(), true);
		}
		return 0;
		
	}
	
	
	public double timeSinceStart() {
		return convert_nano_to_sec( System.nanoTime() - startTime );
	}
	public void reset_start() {
		startTime = System.nanoTime();
	}
}
