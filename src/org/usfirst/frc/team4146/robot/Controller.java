package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.;

/**
 * The Controller class is a wrapper for the WPI Joystick class for the use of Xbox 360 Controllers (was Logitech Controllers)
 * 
 * @author GowanR
 * @author JacobE
 * @author EliA
 * @version 2.2.1
 * // (MAJOR.MINOR.PATCH)
 * 
 */
public class Controller {
	Joystick joy;
	
	// Define buttons
	public static final int aButton = 1;
    public static final int bButton = 2;
    public static final int xButton = 3;
    public static final int yButton = 4;
    public static final int leftBumper = 5;
    public static final int rightBumper = 6;
    public static final int backButton = 7;
    public static final int startButton = 8;
    public static final int leftStickPress = 9;
    public static final int rightStickPress = 10;
    
    // Define Axes
    public static final int leftXAxis = 0;
    public static final int leftYAxis = 1;
    public static final int leftTrigger = 2;
    public static final int rightTrigger = 3;
    public static final int rightXAxis = 4;
    public static final int rightYAxis = 5;
    
    // Constant Variables
    /**
     * used by getDeadband functions. Values within the distance of this value from zero will be set to 0
     * 
     * @see #getDeadbandLeftXAxis()
     * @see #getDeadbandLeftYAxis()
     * @see #getDeadbandRightXAxis()
     * @see #getDeadbandRightYAxis()
     */
    public static final double CONTROLLER_DEADBAND  = 0.15;
    
	/**
	 * Constructor takes the controller number (0 or 1 with two controllers) and instantiates a joystick for that port
	 * 
	 * @param number integer to define which port a controller is using
	 */
	public Controller(int number) {
		joy = new Joystick(number);
	}
	
	/**
	 * Returns whether the "A" button is being pushed on the controller.
	 * 
	 * @return a boolean value of whether the "A" button is pressed
	 */
	public boolean getButtonA() {
		return joy.getRawButton(aButton);
	}
	
	/**
	 * Returns whether the "B" button is being pushed on the controller.
	 * 
	 * @return a boolean value of whether the "B" button is pressed
	 */
	public boolean getButtonB() {
		return joy.getRawButton(bButton);
	}
	
	/**
	 * Returns whether the "X" button is being pushed on the controller.
	 * 
	 * @return a boolean value of whether the "X" button is pressed
	 */
	public boolean getButtonX() {
		return joy.getRawButton(xButton);
	}
	
	/**
	 * Returns whether the "Y" button is being pushed on the controller.
	 * 
	 * @return a boolean value of whether the "Y" button is pressed
	 */
	public boolean getButtonY() {
		return joy.getRawButton(yButton);
	}
	
	/**
	 * Returns whether the left bumper is being pushed on the controller.
	 * 
	 * @return a boolean value of whether the left bumper button is pressed
	 */
	public boolean getLeftBumper() {
		return joy.getRawButton(leftBumper) ;
	}
	
	/**
	 * Returns whether the right bumper is being pushed on the controller.
	 * 
	 * @return a boolean value of whether the right bumper button is pressed
	 */
	public boolean getRightBumper() {
		return joy.getRawButton(rightBumper);
	}
	
	/**
	 * Returns whether the left trigger is being pushed on the controller.
	 * <p>
	 * 
	 * 
	 * @return a boolean value of whether the left trigger button is pressed
	 */
	public boolean getLeftTrigger() {
		return joy.getRawAxis(leftTrigger) > 0;
	}
	
	/**
	 * Returns whether the right trigger is being pushed on the controller.
	 * 
	 * @return a boolean value of whether the right trigger button is pressed
	 */
	public boolean getRightTrigger() {
		return joy.getRawAxis(rightTrigger) > 0;
	}
	
	/**
	 * Returns whether the back button is being pushed on the controller.
	 * 
	 * @return a boolean value of whether the back button is pressed
	 */
	public boolean getButtonBack() {
		return joy.getRawButton(backButton);
	}
	
	/**
	 * Return whether the start button is being pushed on the controller.
	 * 
	 * @return a boolean value of whether the start button is pressed
	 */
	public boolean getButtonStart() {
		return joy.getRawButton(startButton);
	}
	
	/**
	 * Returns whether the left stick is being pushed on the controller.
	 * 
	 * @return a boolean value of whether the left stick button is pressed
	 */
	public boolean getLeftStickPress() {
		return joy.getRawButton(leftStickPress);
	}
	
	/**
	 * Returns whether the right stick is being pushed on the controller.
	 * 
	 * @return a boolean value of whether the right stick button is pressed
	 */
	public boolean getRightStickPress() {
		return joy.getRawButton(rightStickPress);
	}
	
	//Non-Deadbanded Axes:
	
	/**
	 * Returns the value of the x axis of the left stick. (-1.0 to 1.0, 0.0 is centered)
	 * 
	 * @return a double of the x axis position for the left stick
	 */
	public double getLeftXAxis() {
		return joy.getRawAxis(leftXAxis);
	}
	
	/**
	 * Returns the value of the y axis of the left stick. (-1.0 to 1.0, 0.0 is centered)
	 * Value is inverted to make it so forward is positive because the default is backwards is positive
	 * 
	 * @return a double of the y axis position for the left stick
	 */
	public double getLeftYAxis() {
		return -(joy.getRawAxis(leftYAxis));
	}
	
	/**
	 * Returns the value of the x axis of the right stick. (-1.0 to 1.0, 0.0 is centered)
	 * 
	 * @return a double of the x axis position for the right stick
	 */
	public double getRightXAxis() {
		return joy.getRawAxis(rightXAxis);
	}
	
	/**
	 * Returns the value of the y axis of the right stick. (-1.0 to 1.0, 0.0 is centered)
	 * Value is inverted to make it so forward is positive because the default is backwards is positive
	 * 
	 * @return a double of the y axis position for the right stick
	 */
	public double getRightYAxis() {
		return -(joy.getRawAxis(rightYAxis));
	}
	
	//Deadbanded Axes:
	
	/**
	 * Returns the value of the x axis of the left stick with a deadband. (-1.0 to 1.0, 0.0 is centered)
	 * ctrlDeadband is applied. Meaning -{@link #ctrlDeadband} to {@link #ctrlDeadband} values are set to 0
	 * 
	 * @return a double from the result of {@link #joystickDeadband(double)} for the x axis position of the left stick
	 */
	public double getDeadbandLeftXAxis() {
		return joystickDeadband(joy.getRawAxis(leftXAxis));
	}
	
	/**
	 * Returns the value of the y axis of the left stick with a deadband. (-1.0 to 1.0, 0.0 is centered)
	 * Value is inverted to make it so forward is positive because the default is backwards is positive.
	 * ctrlDeadband is applied. Meaning -{@link #ctrlDeadband} to {@link #ctrlDeadband} values are set to 0
	 * 
	 * @return a double from the result of {@link #joystickDeadband(double)} for the y axis position of the left stick
	 */
	public double getDeadbandLeftYAxis() {
		return joystickDeadband(-(joy.getRawAxis(leftYAxis)));
	}
	
	/**
	 * Returns the value of the x axis of the right stick with a deadband. (-1.0 to 1.0, 0.0 is centered)
	 * ctrlDeadband is applied. Meaning -{@link #ctrlDeadband} to {@link #ctrlDeadband} values are set to 0
	 * 
	 * @return a double from the result of {@link #joystickDeadband(double)} for the x axis position of the right stick
	 */
	public double getDeadbandRightXAxis() {
		return joystickDeadband(joy.getRawAxis(rightXAxis));
	}
	
	/**
	 * Returns the value of the y axis of the right stick with a deadband. (-1.0 to 1.0, 0.0 is centered)
	 * Value is inverted to make it so forward is positive because the default is backwards is positive.
	 * ctrlDeadband is applied. Meaning -{@link #ctrlDeadband} to {@link #ctrlDeadband} values are set to 0
	 * 
	 * @return a double from the result of {@link #joystickDeadband(double)} for the y axis position of the right stick
	 */
	public double getDeadbandRightYAxis() {
		return joystickDeadband(-(joy.getRawAxis(rightYAxis)));
	}
	
	/**
	 * Takes the value of a joystick axis and returns it deadbanded. (-1.0 to 1.0, 0.0 is centered)
	 * If value is between -{@link #ctrlDeadband} and {@link #ctrlDeadband} then value is set to 0.0
	 * 
	 * @param joystickInput the value given to the function to be deadbanded
	 * @return the input deadbanded. (
	 */
	private double joystickDeadband(double joystickInput) {
		if(Math.abs(joystickInput) < CONTROLLER_DEADBAND) { 
			//Was (joystickInput < CONTROLLER_DEADBAND) && (joystickInput > -CONTROLLER_DEADBAND)
			return (double)(0.0);//does this have to be cast?
		} else {
			return joystickInput;
		}
	}
	
	/**
	 * Rumble Code!!! (this code is written on the Programming Laptop.)
	 */
	
//	public void rumble(float l, float r){
//		joy.setRumble( l, r);
//		joy.setRumble( l, r)
//	}
//	
	/**
	 * Used just in case we need the raw joystick.
	 *
	 * @return Joystick used for the controller
	 */
	public Joystick getJoystick() {
		return joy;
	}
	
}
