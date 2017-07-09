package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.;

/**
 * The Controller class is a wrapper for the WPI Joystick class for the use of Xbox 360 Controllers (was Logitech Controllers)
 * 
 * @author GowanR
 * @version 8/20/2016
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
    
    // Axes
    private static final int left$X$Axis = 0;
    private static final int left$Y$Axis = 1;
    private static final int leftTrigger = 2;
    private static final int rightTrigger = 3;
    private static final int right$X$Axis = 4;
    private static final int right$Y$Axis = 5;
    // Constants
    public static final double ctrlDeadband  = 0.15; // This is the threshold for the controller joystick deadband
	/**
	 * Constructor takes the controller number (0 or 1 with two controllers)
	 * 
	 * @param number int controller number
	 */
	public Controller(int number) {
		joy = new Joystick(number);
	}
	/**
	 * Gets whether the "X" button is being pushed on the controller.
	 * @return boolean whether "X" button is pressed
	 */
	public boolean getButtonX() {
		return joy.getRawButton( xButton );
	}
	/**
	 * Gets whether the "A" button is being pushed on the controller.
	 * @return boolean whether "A" button is pressed
	 */
	public boolean getButtonA() {
		return joy.getRawButton( aButton );
	}
	/**
	 * Gets whether the "B" button is being pushed on the controller.
	 * @return boolean whether "B" button is pressed
	 */
	public boolean getButtonB() {
		return joy.getRawButton( bButton );
	}
	/**
	 * Gets whether the "Y" button is being pushed on the controller.
	 * @return boolean whether "Y" button is pressed
	 */
	public boolean getButtonY() {
		return joy.getRawButton( yButton );
	}
	/**
	 * Gets whether the left bumper is being pushed on the controller.
	 * @return boolean whether left bumper is pressed
	 */
	public boolean getLeftBumper() {
		return joy.getRawButton( leftBumper ) ;
	}
	/**
	 * Gets whether the right bumper is being pushed on the controller.
	 * @return boolean whether right bumper button is pressed
	 */
	public boolean getRightBumper() {
		return joy.getRawButton( rightBumper );
	}
	/**
	 * Gets whether the left trigger is being pushed on the controller.
	 * @return boolean whether left trigger is pressed
	 */
	public boolean getLeftTrigger() {
		return joy.getRawAxis( leftTrigger ) > 0;
	}
	/**
	 * Gets whether the right trigger is being pushed on the controller.
	 * @return boolean whether right trigger is pressed
	 */
	public boolean getRightTrigger() {
		return joy.getRawAxis( rightTrigger ) > 0;
	}
	/**
	 * Gets whether the back button is being pushed on the controller.
	 * @return boolean whether back button is pressed
	 */
	public boolean getButtonBack() {
		return joy.getRawButton( backButton );
	}
	/**
	 * Gets whether the start button is being pushed on the controller.
	 * @return boolean whether start button is pressed
	 */
	public boolean getButtonStart() {
		return joy.getRawButton( startButton );
	}
	/**
	 * Gets whether the left stick is being pushed on the controller.
	 * @return boolean whether the left stick is pressed
	 */
	public boolean getLeftStickPress() {
		return joy.getRawButton( leftStickPress );
	}
	/**
	 * Gets whether the right stick is being pushed on the controller.
	 * @return boolean whether the right stick is pressed
	 */
	public boolean getRightStickress() {
		return joy.getRawButton( rightStickPress );
	}
	/**
	 * Gets the value of the x axis of the left stick. ( -1.0 to 1.0, 0.0 is centered )
	 * @return double x axis position
	 */
	public double getLeft$X$Axis() {
		return joy.getRawAxis( left$X$Axis );
	}
	/**
	 * Gets the value of the x axis of the left stick. ( -1.0 to 1.0, 0.0 is centered )
	 * Applies deadband, which means -ctrl_deadband to ctrl_deadband values are set to 0
	 * @return double x axis position
	 */
	public double getDeadbandLeft$X$Axis() {
		return joystick_deadband( joy.getRawAxis(left$X$Axis) );
	}
	/**
	 * Gets the value of the y axis of the left stick. ( -1.0 to 1.0, 0.0 is centered )
	 * Value is negative to make it so forward is positive, default is backwards is positive
	 * @return double y axis position
	 */
	public double getLeft$Y$Axis() {
		return -( joy.getRawAxis( left$Y$Axis ) );
	}
	/**
	 * Gets the value of the y axis of the left stick. ( -1.0 to 1.0, 0.0 is centered )
	 * Value is negative to make it so forward is positive, default is backwards is positive
	 * Applies deadband, which means -ctrl_deadband to ctrl_deadband values are set to 0
	 * @return double y axis position
	 */
	public double getDeadbandLeft$Y$Axis() {
		return joystick_deadband( -( joy.getRawAxis(left$Y$Axis) ) );
	}
	/**
	 * Gets the value of the x axis of the right stick. ( -1.0 to 1.0, 0.0 is centered )
	 * @return double x axis position
	 */
	public double getRight$X$Axis() {
		return joy.getRawAxis( right$X$Axis );
	}
	/**
	 * Gets the value of the x axis of the right stick. ( -1.0 to 1.0, 0.0 is centered )
	 * Applies deadband, which means -ctrl_deadband to ctrl_deadband values are set to 0
	 * @return double x axis position
	 */
	public double getDeadbandRight$X$Axis() {
		return joystick_deadband( joy.getRawAxis(right$X$Axis) );
	}
	/**
	 * Gets the value of the y axis of the right stick. ( -1.0 to 1.0, 0.0 is centered )
	 * Value is negative to make it so forward is positive, default is backwards is positive
	 * @return double y axis position
	 */
	public double getRight$Y$Axis() {
		return -( joy.getRawAxis( right$Y$Axis ) );
	}
	/**
	 * Gets the value of the x axis of the left stick. ( -1.0 to 1.0, 0.0 is centered )
	 * Value is negative to make it so forward is positive, default is backwards is positive
	 * Applies deadband, which means -ctrl_deadband to ctrl_deadband values are set to 0
	 * @return double x axis position
	 */
	public double getDeadbandRight$Y$Axis() {
		return joystick_deadband( -( joy.getRawAxis(right$Y$Axis) ) );
	}
	/**
	 * Takes the value of a joystick axis ( -1.0 to 1.0, 0.0 is centered )
	 * If value is between negative ctrl_deadband and ctrl_deadband then value is set to 0.0
	 * @return input or 0.0 depending on above condition
	 */
	private double joystick_deadband( double joystick_input ) {
		if( (joystick_input < ctrlDeadband) && (joystick_input > -ctrlDeadband) )
		{
			return (double)( 0.0 );
		}
		else
		{
			return joystick_input;
		}
	}
	
	/**
	 *	Rumble Code!!!
	 *@return Joystick used for the contorller
	 */
	
//	public void rumble( float l, float r ){
//		setRumble( RumbleType.kLeftRumble, l );
//		setRumble( RumbleType.kRightRumble, r );
//	}
	
	/**
	 *	Used just in case we need the raw joystick.
	 *
	 * @return Joystick used for the contorller
	 */
	public Joystick get_joystick() {
		return joy;
	}
	
}
