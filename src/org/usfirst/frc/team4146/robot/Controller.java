package org.usfirst.frc.team4146.robot;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The Controller class is a wrapper for the wpi Joystick class for the use of Logitech controllers
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
    public static final int left_trigger      = 2;
    public static final int right_trigger     = 3;
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
	 * Gets weather the "X" button is being pushed on the controller.
	 * @return boolean weather "X" button is pressed
	 */
	public boolean get_x_button() {
		return joy.getRawButton( X_button );
	}
	/**
	 * Gets weather the "A" button is being pushed on the controller.
	 * @return boolean weather "A" button is pressed
	 */
	public boolean get_a_button() {
		return joy.getRawButton( A_button );
	}
	/**
	 * Gets weather the "B" button is being pushed on the controller.
	 * @return boolean weather "B" button is pressed
	 */
	public boolean get_b_button() {
		return joy.getRawButton( B_button );
	}
	/**
	 * Gets weather the "Y" button is being pushed on the controller.
	 * @return boolean weather "Y" button is pressed
	 */
	public boolean get_y_button() {
		return joy.getRawButton( Y_button );
	}
	/**
	 * Gets weather the left bumper is being pushed on the controller.
	 * @return boolean weather left bumper is pressed
	 */
	public boolean get_left_bumper() {
		return joy.getRawAxis( left_bumper ) > 0;
	}
	/**
	 * Gets weather the right bumper is being pushed on the controller.
	 * @return boolean weather right bumper button is pressed
	 */
	public boolean get_right_bumper() {
		return joy.getRawButton( right_bumper );
	}
	/**
	 * Gets weather the left trigger is being pushed on the controller.
	 * @return boolean weather left trigger is pressed
	 */
	public boolean get_left_trigger() {
		return joy.getRawAxis( left_trigger ) > 0;
	}
	/**
	 * Gets weather the right trigger is being pushed on the controller.
	 * @return boolean weather right trigger is pressed
	 */
	public boolean get_right_trigger() {
		return joy.getRawAxis( right_trigger ) > 0;
	}
	/**
	 * Gets weather the back button is being pushed on the controller.
	 * @return boolean weather back button is pressed
	 */
	public boolean get_back_button() {
		return joy.getRawButton( back_button );
	}
	/**
	 * Gets weather the start button is being pushed on the controller.
	 * @return boolean weather start button is pressed
	 */
	public boolean get_start_button() {
		return joy.getRawButton( start_button );
	}
	/**
	 * Gets weather the left stick is being pushed on the controller.
	 * @return boolean weather the left stick is pressed
	 */
	public boolean get_left_stick_press() {
		return joy.getRawButton( left_stick_press );
	}
	/**
	 * Gets weather the right stick is being pushed on the controller.
	 * @return boolean weather the right stick is pressed
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
	public double get_deadband_left_x_axis() {
		return joystick_deadband( joy.getRawAxis(left_x_axis) );
	}
	/**
	 * Gets the value of the y axis of the left stick. ( -1.0 to 1.0, 0.0 is centered )
	 * @return double y axis position
	 */
	public double get_left_y_axis() {
		return joy.getRawAxis( left_y_axis );
	}
	public double get_deadband_left_y_axis() {
		return joystick_deadband( joy.getRawAxis(left_y_axis) );
	}
	/**
	 * Gets the value of the x axis of the right stick. ( -1.0 to 1.0, 0.0 is centered )
	 * @return double x axis position
	 */
	public double get_right_x_axis() {
		return joy.getRawAxis( right_x_axis );
	}
	public double get_deadband_right_x_axis() {
		return joystick_deadband( joy.getRawAxis(right_x_axis) );
	}
	/**
	 * Gets the value of the y axis of the right stick. ( -1.0 to 1.0, 0.0 is centered )
	 * @return double y axis position
	 */
	public double get_right_y_axis() {
		return joy.getRawAxis( right_y_axis );
	}
	public double get_deadband_right_y_axis() {
		return joystick_deadband( joy.getRawAxis(right_y_axis) );
	}
	
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
	 *	Used just in case we need the raw joystick.
	 *
	 * @return Joystick used for the contorller
	 */
	public Joystick get_joystick() {
		return joy;
	}
	
}
