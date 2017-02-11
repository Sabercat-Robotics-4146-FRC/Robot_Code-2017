package org.usfirst.frc.team4146.robot;

import edu.wpi.first.wpilibj.SPI;

public class Pixy_SPI {
	/*
	private final int PIXY_INITIAL_ARRAYSIZE = 30;
	private final int PIXY_MAXIMUM_ARRAYSIZE = 130;
	private final int PIXY_START_WORD        = 0xaa55;
	private final int PIXY_START_WORD_CC     = 0xaa56;
	private final int PIXY_START_WORDX       = 0x55aa;
	private final int PIXY_MAX_SIGNATURE     = 7;
	private final int PIXY_DEFAULT_ARGVAL    = 0xffff;

	// Pixy x-y position values
	private final long PIXY_MIN_X             = 0L;
	private final long PIXY_MAX_X             = 319L;
	private final long PIXY_MIN_Y             = 0L;
	private final long PIXY_MAX_Y             = 199L;

	// RC-servo values
	private final long PIXY_RCS_MIN_POS       = 0L;
	private final long PIXY_RCS_MAX_POS       = 1000L;
	private final long PIXY_RCS_CENTER_POS 	  = ((PIXY_RCS_MAX_POS-PIXY_RCS_MIN_POS)/2);
	*/
	//SPI pixySpi = new SPI( SPI.Port.kOnboardCS0 );
	
	/* Stolen From Here 
	 * https://github.com/kauailabs/navxmxp/blob/master/roborio/java/navx_frc/src/com/kauailabs/navx/frc/RegisterIO_SPI.java
	 * Modified by Me
	 * Fuck Licensing Laws
	 */
/*
    SPI port;
    int bitrate;
    boolean trace = true;
    
    static final int   DEFAULT_SPI_BITRATE_HZ         = 500000;

	
	public Pixy_SPI( SPI spi_port ) {
        port = spi_port;
        bitrate = DEFAULT_SPI_BITRATE_HZ;
    }

    public Pixy_SPI( SPI spi_port, int bitrate ) {
        port = spi_port;
        this.bitrate = bitrate;
    }
    
    public boolean init() {
        port.setClockRate(bitrate);
        port.setMSBFirst();
        port.setSampleDataOnFalling();
        port.setClockActiveLow();
        port.setChipSelectActiveLow();
        if (trace) System.out.println("pixy:  Initialized SPI communication at bitrate " + bitrate);
        return true;
    }

	//import com.kauailabs.navx.AHRSProtocol;

	//import edu.wpi.first.wpilibj.SPI;
	//import edu.wpi.first.wpilibj.Timer;

	//class RegisterIO_SPI implements IRegisterIO{
	    
	    
	    	   //@Override
    	
	    //@Override
    	/*public boolean requestData(byte address, byte value ) {
    		byte[] cmd = new byte[2];
    		cmd[0] = (byte)
    	}*/
	/*
    	public byte[] buffer = new byte[16];
    
    	public boolean readSPI(byte[] buffer) {
    		 byte[] received_data = new byte[buffer.length+1];
    		 if ( port.read(true, received_data, received_data.length) != received_data.length ) {
		            if (trace) System.out.println("navX-MXP SPI Read:  Read error");
		            return false; // READ ERROR
		        }
    		
    		return false;
    	}
    	 
    	
    
	    public boolean write(byte address, byte value ) {
	        byte[] cmd = new byte[3];
	        cmd[0] = (byte) (address  | (byte)0x80);
	        cmd[1] = value;
	        cmd[2] = AHRSProtocol.getCRC(cmd, 2);
	        boolean write_ok;
	        synchronized(this) {
	        	write_ok = (port.write(cmd, cmd.length) == cmd.length);
	        }
	        if ( !write_ok ) {
	            if (trace) System.out.println("navX-MXP SPI Read:  Write error");
	            return false; // WRITE ERROR
	        }
	        return true;
	    }

	    //@Override
	    public boolean read(byte first_address, byte[] buffer) {
	*/
	    	
	    	
	    	
	  /*  	
	    	byte[] cmd = new byte[3];
	        cmd[0] = first_address;
	        cmd[1] = (byte)buffer.length;
	        cmd[2] = AHRSProtocol.getCRC(cmd, 2);
	        synchronized(this) {
		        if ( port.write(cmd, cmd.length) != cmd.length ) {
		        	return false; // WRITE ERROR
		        }*/
		        // delay 200 us /* TODO:  What is min. granularity of delay()? */
		        /*Timer.delay(0.001);
		        byte[] received_data = new byte[buffer.length+1];
		        if ( port.read(true, received_data, received_data.length) != received_data.length ) {
		            if (trace) System.out.println("navX-MXP SPI Read:  Read error");
		            return false; // READ ERROR
		        }
		        byte crc = AHRSProtocol.getCRC(received_data, received_data.length - 1);
		        if ( crc != received_data[received_data.length-1] ) {
		            if (trace) System.out.println("navX-MXP SPI Read:  CRC error");        	
		            return false; // CRC ERROR
		        }
		        System.arraycopy(received_data, 0, buffer, 0, received_data.length - 1);
	        }
	        return true;
	    }

	    //@Override
	    public boolean shutdown() {
	        return true;
	    }

	//}*/
}
