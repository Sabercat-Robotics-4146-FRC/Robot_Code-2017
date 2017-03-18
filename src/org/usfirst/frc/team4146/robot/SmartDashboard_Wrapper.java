package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SmartDashboard_Wrapper {
	private static NetworkTable networktable;
	public SmartDashboard_Wrapper(NetworkTable n) {
		networktable = n;
	}
	public static class SD_Wrapper {
		
		public static void putDouble(String fieldName, double value) {
		networktable.putNumber(fieldName, value);
		}
		public static void putInt(String fieldName, int value) {
			networktable.putNumber(fieldName, value);
		}
	}
}
