package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
//import main.PixyPacket;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid; 
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Relay;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//public class Robot extends TimedRobot{
public class Robot extends SampleRobot {
	                             //defines solenoids


	private static boolean initialized = false; 
    private static final long CYCLE_USEC = 250000; 

    
   // test
	
	public Robot() {
		
	
	         
	 //public static void initialize() 
	
	 } 
	
	@Override
	public void robotInit() {
		RobotMap.init();
	}

	@Override
	public void autonomous() {
		
	}
	
	@Override
	public void operatorControl() {
		
		
		while (isOperatorControl() && isEnabled()) {
		
			
			
//Differential drive
			
//drive controller
			 RobotMap.prDrive.arcadeDrive(RobotMap.driveController.getDeadbandLeftXAxis(),RobotMap.driveController.getDeadbandLeftYAxis());
//t-shirt controller
			 RobotMap.prDrive.arcadeDrive(RobotMap.tshirtController.getDeadbandLeftXAxis(),RobotMap.tshirtController.getDeadbandLeftYAxis());
	
//flywheel initiation
//drive controller left trigger
						
			FlyWheel.updateFly();
           if (FlyWheel.flyOn) {
           RobotMap.flyWheel.set(.55);
           }
           else {
           RobotMap.flyWheel.set(0.0);
           }
           

       }
           
//Firing basketball
//drive controller right trigger		
      if(RobotMap.driveController.getRightTrigger())
      {
    	  RobotMap.Solenoid0.set(false);
    	  RobotMap.Solenoid1.set(true);
          Timer.delay(.25);
          RobotMap.Solenoid0.set(true);
          RobotMap.Solenoid1.set(false);
      }
      
      
      
      
//compressor
//drive controller button A
      compressor.updateCompressor();
      if (compressor.compressorOn) {
     
      RobotMap.Compressor.setClosedLoopControl(true);
      }
      else {
      
      RobotMap.Compressor.setClosedLoopControl(false);
      }
      
//pressure switch
//automatic
      if  (RobotMap.Compressor.getPressureSwitchValue() ) {
   	   RobotMap.Compressor.setClosedLoopControl(true);
      }
      else {
   	   RobotMap.Compressor.setClosedLoopControl(false);  
      
      
      
// intake 
//drive controller button X
      intake.updateintake();
      if (intake.intakeOn) 
      {
    	  RobotMap.Intake.set(.20 );
    	  
    	  if (RobotMap.limitSwitchUp.get()) 
    	  {
    		  RobotMap.Intake.set(0.0);
    		  
    		  RobotMap.driveController.setRumble(RumbleType.kLeftRumble, 1);
        	  RobotMap.driveController.setRumble(RumbleType.kRightRumble, 1);
    	  }

       }
      else 
      {
   	  RobotMap.Intake.set(-.60);
   	  	  
    	  if (RobotMap.limitSwitchDown.get()) 
    	  {
    		  RobotMap.Intake.set(0.0);
          }
       }  	  
      

      
//intake roller
//drive controller button Y       
      intakerotator.updateintakeRotator();
      if (intakerotator.intakerotatorOn) {
    	  RobotMap.intakeRotator.set(0.75); 
      }
      else {
    	  RobotMap.intakeRotator.set(0.0);
      }  
      
      
      
      
      
//turret
//drive controller, left bumper to go left,right bumper to go right     
      if(RobotMap.driveController.getLeftBumper()) {
    	  RobotMap.turretRotator.set(-.75); 
      }
      else if(RobotMap.driveController.getRightBumper()) {
    	  RobotMap.turretRotator.set(.75); 
      }
      else {
    	  RobotMap.turretRotator.set(0);
      }
      
     
      
      
      
      
//t-shirt launcher
//t-shirt controller start button = 0%
//t-shirt controller B button = 25%
//t-shirt controller A button = 50%     
//t-shirt controller X button = 75%     
//t-shirt controller Y button = 100%     
      if(RobotMap.tshirtController.getButtonStart()) {
    	  RobotMap.tshirtL.set(0); 
    	  RobotMap.tshirtR.set(0); 
      }
      
      if(RobotMap.tshirtController.getButtonB()) {
    	  RobotMap.tshirtL.set(0.25); 
    	  RobotMap.tshirtR.set(0.25); 
      }
      
      if(RobotMap.tshirtController.getButtonA()) {
    	  RobotMap.tshirtL.set(0.50); 
    	  RobotMap.tshirtR.set(0.50); 
      }
      
      if(RobotMap.tshirtController.getButtonX()) {
    	  RobotMap.tshirtL.set(0.75); 
    	  RobotMap.tshirtR.set(0.75); 
      }
      
      if(RobotMap.tshirtController.getButtonY()) {
    	  RobotMap.tshirtL.set(1); 
    	  RobotMap.tshirtR.set(1); 
      
      }      
      
}

	
	
	

		
//		try {
//			wait(300);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
	}
	

	
	@Override
	public void test() {
		
		System.out.println("In test mode");
	
		final AnalogPotentiometer pot;
		pot = new AnalogPotentiometer(1);
	
		int i = 0;
		while (true) {
		
			if (i >= 1000) {
				System.out.println("pot value" + pot.get());
				i = 0;
			}
			i++;
		}



	}
	
}


