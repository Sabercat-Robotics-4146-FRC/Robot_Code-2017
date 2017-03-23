package org.usfirst.frc.team4146.robot;
import org.usfirst.frc.team4146.robot.PID.*;
import edu.wpi.first.wpilibj.networktables.*;

public class Vision {
	private String network_name;
	private PID center_pid;
	private NetworkTable table;
	
	private static double width = 5;
	private static double focal_length = 184;
	
	public Vision( String network_name, NetworkTable table ) {
		this.network_name = network_name;
		this.table = table;
		
		this.center_pid = new PID( new signal() {
			public double getValue() {
				double x = table.getNumber( "gear_x", 0.0);
    			if ( x == 0.0 ) {
    				return 0;
    			}
    			return x - 160.0;
			}
		}, true, "Vision");
		this.center_pid.set_pid( 0.008, 0, 0.4 );
	}
	public double get_distance() {
		return ( width * focal_length ) / table.getNumber( network_name + "_w", 0.0 );
	}
	// Updates PID
	public void update( double dt ) {
		center_pid.update( dt );
	}
	// Gets PID controller value output
	public double get() {
//		if ( center_pid.get() > 0.7 ) {
//			return 0;
//		}
		return center_pid.get();
	}
	
	public double get_steady_state_error() {
		return center_pid.steady_state_error();
	}
	
}
