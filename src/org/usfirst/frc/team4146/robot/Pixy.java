package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
public class Pixy {
	
	public void get_value(  ){
		/*for ( SPI.Port c : SPI.Port.values() ){
			System.out.println( c );
		}*/
		SPI spi_pixy = new SPI( Port.kOnboardCS0 );
		
		boolean initiate = false; 
		byte[] dataReceived = new byte[10];
		int size = 100;
		
		System.out.println( spi_pixy.read( initiate,  dataReceived, size) ); 
		
	}
}