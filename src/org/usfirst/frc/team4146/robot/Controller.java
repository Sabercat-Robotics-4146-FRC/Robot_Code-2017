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
	public static final int A_button          = 1;
    public static final int B_button          = 2;
    public static final int X_button          = 3;
    public static final int Y_button          = 4;
    public static final int left_bumper       = 5;
    public static final int right_bumper      = 6;
    public static final int back_button       = 7;
    public static final int start_button      = 8;
    public static final int left_stick_press  = 9;
    public static final int right_stick_press = 10;
    
    // Axes
    private static final int left_x_axis      = 0;
    private static final int left_y_axis      = 1;
    private static final int left_trigger      = 2;			//This was public for seemingly no reason
    private static final int right_trigger     = 3;			//This was public for seemingly no reason
    private static final int right_x_axis     = 4;
    private static final int right_y_axis     = 5;
    // Constants
    public static final double ctrl_deadband  = 0.15; // This is the threshold for the controller joystick deadband
	/**
	 * Constructor takes the controller number (0 or 1 with two controllers)
	 * 
	 * @param number int controller number
	 */
	public Controller( int number ) {
		joy = new Joystick( number );
	}
	/**
	 * Gets whether the "X" button is being pushed on the controller.
	 * @return boolean whether "X" button is pressed
	 */
	public boolean get_x_button() {
		return joy.getRawButton( X_button );
	}
	/**
	 * Gets whether the "A" button is being pushed on the controller.
	 * @return boolean whether "A" button is pressed
	 */
	public boolean get_a_button() {
		return joy.getRawButton( A_button );
	}
	/**
	 * Gets whether the "B" button is being pushed on the controller.
	 * @return boolean whether "B" button is pressed
	 */
	public boolean get_b_button() {
		return joy.getRawButton( B_button );
	}
	/**
	 * Gets whether the "Y" button is being pushed on the controller.
	 * @return boolean whether "Y" button is pressed
	 */
	public boolean get_y_button() {
		return joy.getRawButton( Y_button );
	}
	/**
	 * Gets whether the left bumper is being pushed on the controller.
	 * @return boolean whether left bumper is pressed
	 */
	public boolean get_left_bumper() {
		return joy.getRawAxis( left_bumper ) > 0;
	}
	/**
	 * Gets whether the right bumper is being pushed on the controller.
	 * @return boolean whether right bumper button is pressed
	 */
	public boolean get_right_bumper() {
		return joy.getRawButton( right_bumper );
	}
	/**
	 * Gets whether the left trigger is being pushed on the controller.
	 * @return boolean whether left trigger is pressed
	 */
	public boolean get_left_trigger() {
		return joy.getRawAxis( left_trigger ) > 0;
	}
	/**
	 * Gets whether the right trigger is being pushed on the controller.
	 * @return boolean whether right trigger is pressed
	 */
	public boolean get_right_trigger() {
		return joy.getRawAxis( right_trigger ) > 0;
	}
	/**
	 * Gets whether the back button is being pushed on the controller.
	 * @return boolean whether back button is pressed
	 */
	public boolean get_back_button() {
		return joy.getRawButton( back_button );
	}
	/**
	 * Gets whether the start button is being pushed on the controller.
	 * @return boolean whether start button is pressed
	 */
	public boolean get_start_button() {
		return joy.getRawButton( start_button );
	}
	/**
	 * Gets whether the left stick is being pushed on the controller.
	 * @return boolean whether the left stick is pressed
	 */
	public boolean get_left_stick_press() {
		return joy.getRawButton( left_stick_press );
	}
	/**
	 * Gets whether the right stick is being pushed on the controller.
	 * @return boolean whether the right stick is pressed
	 */
	public boolean get_right_stick_press() {
		return joy.getRawButton( right_stick_press );
	}
	/**
	 * Gets the value of the x axis of the left stick. ( -1.0 to 1.0, 0.0 is centered )
	 * @return double x axis position
	 */
	public double get_left_x_axis() {
		return joy.getRawAxis( left_x_axis );
	}
	/**
	 * Gets the value of the x axis of the left stick. ( -1.0 to 1.0, 0.0 is centered )
	 * Applies deadband, which means -ctrl_deadband to ctrl_deadband values are set to 0
	 * @return double x axis position
	 */
	public double get_deadband_left_x_axis() {
		return joystick_deadband( joy.getRawAxis(left_x_axis) );
	}
	/**
	 * Gets the value of the y axis of the left stick. ( -1.0 to 1.0, 0.0 is centered )
	 * Value is negative to make it so forward is positive, default is backwards is positive
	 * @return double y axis position
	 */
	public double get_left_y_axis() {
		return -( joy.getRawAxis( left_y_axis ) );
	}
	/**
	 * Gets the value of the y axis of the left stick. ( -1.0 to 1.0, 0.0 is centered )
	 * Value is negative to make it so forward is positive, default is backwards is positive
	 * Applies deadband, which means -ctrl_deadband to ctrl_deadband values are set to 0
	 * @return double y axis position
	 */
	public double get_deadband_left_y_axis() {
		return joystick_deadband( -( joy.getRawAxis(left_y_axis) ) );
	}
	/**
	 * Gets the value of the x axis of the right stick. ( -1.0 to 1.0, 0.0 is centered )
	 * @return double x axis position
	 */
	public double get_right_x_axis() {
		return joy.getRawAxis( right_x_axis );
	}
	/**
	 * Gets the value of the x axis of the right stick. ( -1.0 to 1.0, 0.0 is centered )
	 * Applies deadband, which means -ctrl_deadband to ctrl_deadband values are set to 0
	 * @return double x axis position
	 */
	public double get_deadband_right_x_axis() {
		return joystick_deadband( joy.getRawAxis(right_x_axis) );
	}
	/**
	 * Gets the value of the y axis of the right stick. ( -1.0 to 1.0, 0.0 is centered )
	 * Value is negative to make it so forward is positive, default is backwards is positive
	 * @return double y axis position
	 */
	public double get_right_y_axis() {
		return -( joy.getRawAxis( right_y_axis ) );
	}
	/**
	 * Gets the value of the x axis of the left stick. ( -1.0 to 1.0, 0.0 is centered )
	 * Value is negative to make it so forward is positive, default is backwards is positive
	 * Applies deadband, which means -ctrl_deadband to ctrl_deadband values are set to 0
	 * @return double x axis position
	 */
	public double get_deadband_right_y_axis() {
		return joystick_deadband( -( joy.getRawAxis(right_y_axis) ) );
	}
	/**
	 * Takes the value of a joystick axis ( -1.0 to 1.0, 0.0 is centered )
	 * If value is between negative ctrl_deadband and ctrl_deadband then value is set to 0.0
	 * @return input or 0.0 depending on above condition
	 */
	private double joystick_deadband( double joystick_input ) {
		if( (joystick_input < ctrl_deadband) && (joystick_input > -ctrl_deadband) )
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
