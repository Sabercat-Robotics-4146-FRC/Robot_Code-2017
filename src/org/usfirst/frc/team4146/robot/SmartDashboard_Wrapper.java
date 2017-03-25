package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SmartDashboard_Wrapper {
	private static NetworkTable networktable;
	public SmartDashboard_Wrapper( NetworkTable n ) {
		networktable = n;
	}
	
	public static void printToSmartDashboard( String fieldName, double value ) {
		networktable.putNumber(fieldName, value);
	}
	
	public static void printToSmartDashboard( String fieldName, int value ) {
		networktable.putNumber(fieldName, value);
	}
	
	public static void printToSmartDashboard( String fieldName, boolean value ) {
		networktable.putBoolean(fieldName, value);
	}
	
}
