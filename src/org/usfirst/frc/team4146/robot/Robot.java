package org.usfirst.frc.team4146.robot;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import org.usfirst.frc.team4146.robot.PID.*;

public class Robot extends SampleRobot {
	
	Controller drive_controller;
	Talon front_left;
	Talon rear_left;
	Talon front_right;
	Talon rear_right;
	RobotDrive drive;
	AHRS gyro;
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
        
    }
	
    public void autonomous() {
    	
    }
    
    public void operatorControl() {
    	
    	//Ramp_Drive dTrain = new Ramp_Drive( drive_controller, drive );
    	Heading_Lock dTrain = new Heading_Lock( drive_controller, drive, gyro );
    	Pixy pixy = new Pixy(  );
    	
    	while ( isOperatorControl() && isEnabled() ) {
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
       
    		
    	}
    }// end operatorControl

    public void test() {
    	
    }
}
