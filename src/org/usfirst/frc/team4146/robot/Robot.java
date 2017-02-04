package org.usfirst.frc.team4146.robot;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
=======
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

>>>>>>> 7f445c7f4af873f44e9fc8a94c69a22b481e7153
import org.usfirst.frc.team4146.robot.PID.*;

public class Robot extends SampleRobot {
	
	Controller drive_controller;
	
	Talon front_left;
	Talon rear_left;
	Talon front_right;
	Talon rear_right;
	
	RobotDrive drive;
	AHRS gyro;
<<<<<<< HEAD
	//PID heading_pid;
	
	
	//Copy Pasta NAVX
	
	//PIDController turnController;
	double rotateToAngleRate;
	

	

	  static final double kP = 0.03;
	  static final double kI = 0.00;
	  static final double kD = 0.00;
	  static final double kF = 0.00;
	  static double pidOutput = 0.00;
	  //PIDController turnController = new PIDController(kP, kI, kD, kF, gyro, this);
	/* This tuning parameter indicates how close to "on target" the    */
	/* PID Controller will attempt to get.                             */

	static final double kToleranceDegrees = 2.0f;
	
	
    public Robot() {		//Add try catch for initializations?
    	try {
    		drive_controller = new Controller( 0 );
    	
    		front_left  = new Talon( 5 );
    		rear_left   = new Talon( 6 );
    		front_right = new Talon( 7 );
    		rear_right  = new Talon( 0 );
		
    		front_left.setSafetyEnabled(false);
    		rear_left.setSafetyEnabled(false);
    		front_right.setSafetyEnabled(false);
    		rear_right.setSafetyEnabled(false);
    		//COPYPASTA NAVX CODE
 /*   		  
    	      turnController.setInputRange(-180.0f,  180.0f);
    	      turnController.setOutputRange(-1.0, 1.0);
    	      turnController.setAbsoluteTolerance(kToleranceDegrees);
    	      turnController.setContinuous(true);
    		*/
    		gyro = new AHRS( SPI.Port.kMXP );
    		gyro.reset();
    	//	turnController = new PIDController(kP, kI, kD, kF, gyro, pidWrite(pidOutput){});
    		// Instantiate robot's drive with Talons
    		drive = new RobotDrive( front_left, rear_left, front_right, rear_right );
		/*
    		heading_pid = new PID( new signal() {
    			public double getValue() {
    				return gyro.getAngle();
    			}
    		});
    		heading_pid.set_pid( 1, 0, 0 );
    		heading_pid.set_setpoint( 0 );*/
    	} catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating: " + ex.getMessage(), true);
    	}
    }
    
    public void robotInit() {
        
=======
	
	Heading robotHeading;
	Move_Distance robotMove;
	
	Encoder right_drive_encoder;
	Encoder left_drive_encoder;
	
    public Robot() {
    	try {
    		drive_controller = new Controller( 0 );
    	
    		front_left  = new Talon( 5 );
    		rear_left   = new Talon( 6 );
    		front_right = new Talon( 7 );
    		rear_right  = new Talon( 0 );
    		
    		// Instantiate robot's drive with Talons
    		drive = new RobotDrive( front_left, rear_left, front_right, rear_right );
    		
    		gyro = new AHRS( SPI.Port.kMXP );
    		
        	robotHeading = new Heading( gyro );
        	
        	robotMove = new Move_Distance( right_drive_encoder, left_drive_encoder );

    	} catch (RuntimeException ex ) {
    		DriverStation.reportError("Error instantiating: " + ex.getMessage(), true);
    	}
    }
    
    public void robotInit() {
    	
    	front_left.setSafetyEnabled(false);
		rear_left.setSafetyEnabled(false);
		front_right.setSafetyEnabled(false);
		rear_right.setSafetyEnabled(false);
    	
		gyro.reset();
		
		robotHeading.set_vars(0.5, 0.0, 0.0, 0.0);// p, i, d, setPoint
		
		robotMove.set_vars(0.5, 0.0, 0.0, 0.0);// p, i, d, setPoint
>>>>>>> 7f445c7f4af873f44e9fc8a94c69a22b481e7153
    }
	
    public void autonomous() {
    	
    }
    
    public void operatorControl() {
    	
    	//Ramp_Drive dTrain = new Ramp_Drive( drive_controller, drive );
<<<<<<< HEAD
    	Heading_Lock dTrain = new Heading_Lock( drive_controller, drive, gyro );
    	Pixy pixy = new Pixy(  );
=======
    	Preferences prefs = Preferences.getInstance();
    	double p_pref = 0.0;
    	double i_pref = 0.0;
    	double d_pref = 0.0;
    	double turn_angle = 30.0;
>>>>>>> 7f445c7f4af873f44e9fc8a94c69a22b481e7153
    	
    	boolean aLast = true;
    	boolean bLast = true;
    	boolean yLast = true;
    	while ( isOperatorControl() && isEnabled() ) {
<<<<<<< HEAD
    		//dTrain.ramp_drive();
    		dTrain.heading_lock();
    		
    		if( drive_controller.get_y_button() ){
    			pixy.get_value();
    		}
    		
    		//NAVX COPYPASTA CODE
/*    		Display 6-axis Processed Angle Data                                      */
//    		SmartDashboard.putBoolean(  "IMU_Connected",        gyro.isConnected());
//            SmartDashboard.putBoolean(  "IMU_IsCalibrating",    gyro.isCalibrating());
//            SmartDashboard.putNumber(   "IMU_Yaw",              gyro.getYaw());
//            SmartDashboard.putNumber(   "IMU_Pitch",            gyro.getPitch());
//            SmartDashboard.putNumber(   "IMU_Roll",             gyro.getRoll());
//            
//            /* Display tilt-corrected, Magnetometer-based heading (requires             */
//            /* magnetometer calibration to be useful)                                   */
//            
//            SmartDashboard.putNumber(   "IMU_CompassHeading",   gyro.getCompassHeading());
//            
//            /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
    			SmartDashboard.putNumber(   "IMU_FusedHeading",     gyro.getFusedHeading());
//
//            /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
//            /* path for upgrading from the Kit-of-Parts gyro to the navx-MXP            */
//            
//            SmartDashboard.putNumber(   "IMU_TotalYaw",         gyro.getAngle());
//            SmartDashboard.putNumber(   "IMU_YawRateDPS",       gyro.getRate());
//
//            /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */
//            
//            SmartDashboard.putNumber(   "IMU_Accel_X",          gyro.getWorldLinearAccelX());
//            SmartDashboard.putNumber(   "IMU_Accel_Y",          gyro.getWorldLinearAccelY());
//            SmartDashboard.putBoolean(  "IMU_IsMoving",         gyro.isMoving());
//            SmartDashboard.putBoolean(  "IMU_IsRotating",       gyro.isRotating());
//
//            /* Display estimates of velocity/displacement.  Note that these values are  */
//            /* not expected to be accurate enough for estimating robot position on a    */
//            /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
//            /* of these errors due to single (velocity) integration and especially      */
//            /* double (displacement) integration.                                       */
//            
//            SmartDashboard.putNumber(   "Velocity_X",           gyro.getVelocityX());
//            SmartDashboard.putNumber(   "Velocity_Y",           gyro.getVelocityY());
//            SmartDashboard.putNumber(   "Displacement_X",       gyro.getDisplacementX());
//            SmartDashboard.putNumber(   "Displacement_Y",       gyro.getDisplacementY());
//            
//            /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
//            /* NOTE:  These values are not normally necessary, but are made available   */
//            /* for advanced users.  Before using this data, please consider whether     */
//            /* the processed data (see above) will suit your needs.                     */
//            
//            SmartDashboard.putNumber(   "RawGyro_X",            gyro.getRawGyroX());
//            SmartDashboard.putNumber(   "RawGyro_Y",            gyro.getRawGyroY());
//            SmartDashboard.putNumber(   "RawGyro_Z",            gyro.getRawGyroZ());
//            SmartDashboard.putNumber(   "RawAccel_X",           gyro.getRawAccelX());
//            SmartDashboard.putNumber(   "RawAccel_Y",           gyro.getRawAccelY());
//            SmartDashboard.putNumber(   "RawAccel_Z",           gyro.getRawAccelZ());
//            SmartDashboard.putNumber(   "RawMag_X",             gyro.getRawMagX());
//            SmartDashboard.putNumber(   "RawMag_Y",             gyro.getRawMagY());
//            SmartDashboard.putNumber(   "RawMag_Z",             gyro.getRawMagZ());
//            SmartDashboard.putNumber(   "IMU_Temp_C",           gyro.getTempC());
//            
//            /* Omnimount Yaw Axis Information                                           */
//            /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
//            AHRS.BoardYawAxis yaw_axis = gyro.getBoardYawAxis();
//            SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
//            SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );
//            
//            /* Sensor Board Information                                                 */
//            SmartDashboard.putString(   "FirmwareVersion",      gyro.getFirmwareVersion());
//            
//            /* Quaternion Data                                                          */
//            /* Quaternions are fascinating, and are the most compact representation of  */
//            /* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
//            /* from the Quaternions.  If interested in motion processing, knowledge of  */
//            /* Quaternions is highly recommended.                                       */
//            SmartDashboard.putNumber(   "QuaternionW",          gyro.getQuaternionW());
//            SmartDashboard.putNumber(   "QuaternionX",          gyro.getQuaternionX());
//            SmartDashboard.putNumber(   "QuaternionY",          gyro.getQuaternionY());
//            SmartDashboard.putNumber(   "QuaternionZ",          gyro.getQuaternionZ());
//            
//            /* Connectivity Debugging Support                                           */
//            SmartDashboard.putNumber(   "IMU_Byte_Count",       gyro.getByteCount());
//            SmartDashboard.putNumber(   "IMU_Update_Count",     gyro.getUpdateCount());
       
=======
    		
    		//dTrain.ramp_drive();
    		
    		if(drive_controller.get_a_button() && aLast) {
    			robotHeading.set_heading();
    			aLast = false;
    		}
    		else if( !drive_controller.get_a_button() ) {
    			aLast = true;
    		}
    		
    		if(drive_controller.get_b_button() && bLast) {
//    			robotHeading.rel_angle_turn(-drive_controller.get_deadband_right_x_axis() * 180);
    			robotHeading.rel_angle_turn(turn_angle);

    			bLast = false;
    		}
    		else if( !drive_controller.get_b_button() ) {
    			bLast = true;
    		}
    		

    		if(drive_controller.get_y_button() && yLast) {
    			p_pref = prefs.getDouble("PValue", 0.0);
    			i_pref = prefs.getDouble("IValue", 0.0);
    			d_pref = prefs.getDouble("DValue", 0.0);
    			turn_angle = prefs.getDouble("TurnAngle", 0.0);
    			robotHeading.set_vars(p_pref, i_pref, d_pref, 0.0);
    			SmartDashboard.putNumber("P", p_pref);
    			SmartDashboard.putNumber("I", i_pref);
    			SmartDashboard.putNumber("D", d_pref);
    			SmartDashboard.putNumber("turnAngle", turn_angle);
    			
    			
    			yLast = false;
    		}
    		else if( !drive_controller.get_y_button() ) {
    			yLast = true;
    		}

    		
    		drive.arcadeDrive( drive_controller.get_deadband_left_y_axis(), robotHeading.heading()  );
    		Timer.delay( 0.005 );// possibly Useless

>>>>>>> 7f445c7f4af873f44e9fc8a94c69a22b481e7153
    		
    	}
    }// end operatorControl

    public void test() {
    	
    }
}
