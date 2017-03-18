package org.usfirst.frc.team4146.robot.PID;

/*
 * Used for storing the last n values. Its like a value buffer with nice functions built in.
 * Written by GowanR
 */
import java.util.ArrayList;

class SizedStack {
  ArrayList<Double> data;
  int size;

  /**
  * Constructs the SizedStack based on desired size.
  * @param desired size of stack.
  */
  public SizedStack( int size ) {
    this.size = size;
    this.data = new ArrayList<Double>( size );

  }
  /**
  * Overloaded constructor for SizedStack that takes an ArrayList and keeps its size.
  * @param the ArrayList the SizedStack will be based on.
  */
  public SizedStack( ArrayList<Double> list ) {
    this.size = list.size();
    this.data = list;
  }
  /**
  * Used to retreve the data in a SizedStack.
  * @return ArrayList representation of data held in SizedStack.
  */
  public ArrayList<Double> get_data() {
    return this.data;
  }
  /**
  * Returns summation of a SizedStack.
  */
  public double sum() {
    this.trim();
    double sum = 0;
    for ( int i = 0; i < data.size(); i ++ ) {
      sum += data.get(i);
    }
    return sum;
  }
  /**
  * Returns the mean of a sized stack.
  */
  public double mean() {
    return this.sum()/data.size();
  }
  /**
   * Returns summation of the absolute values of every number in a SizedStack.
   */
  public double absolute_sum() {
     this.trim();
     double sum = 0;
     for ( int i = 0; i < data.size(); i ++ ) {
    	 sum += Math.abs(data.get(i));
     }
     return sum;
  }
   /**
   * Returns the mean of the absolute values of every number in a SizedStack.
   */
  public double absolute_mean() {
	  return this.absolute_sum()/data.size();
  }
  /**
  * Removes the oldest entered data point and returns it.
  */
  public double right_pop() {
    return data.remove( data.size()-1 );
  }
  /**
  * Retrims the SizedStack.. Keeps it within the defined size.
  */
  private void trim() {
    while ( data.size() > this.size ) {
      this.right_pop();
    }
  }
  /**
  * Pushes a value to the left of the sized stack.
  */
  public void push( double a ) {
    data.add( 0, a );
    this.trim();
  }
  /**
  * Resizes the SizedStack to the given integral value.
  */
  public void resize( int n ) {
    this.size = n;
    this.trim();
  }
  /**
  * Returns the standard deviation of the data held within the SizedStack
  */
  public double stdev() {
    this.trim();
    double mean = this.mean();
    double sum = 0;
    for ( int i = 0; i < data.size(); i ++ ) {
      sum += Math.pow( data.get(i) - mean, 2 );
    }
    return Math.sqrt( sum/(data.size()-1) );
  }
  /**
  * Prints an ArrayList<Double> in a nice way.
  */
  private void array_print( ArrayList<Double> array ) {
    System.out.print("[ ");
    for ( int i = 0; i < array.size() - 1; i ++ ) {
      System.out.print( array.get( i ) + " ,");
    }
    System.out.println( array.get( array.size()-1 ) + " ]\n");
  }
  /**
  * Used to print out the SizedStack data if you're too lazy to use overloaded toString() in a print statement. Pretty much depricated.
  */
  public void debug_print() {
    this.trim();
    array_print( data );
  }
  
  @Override
  public String toString() {
    this.trim();
    String s = "";
    s += "[ ";
    for ( int i = 0; i < data.size() - 1; i ++ ) {
      s += data.get( i ) + " ,";
    }
    s += data.get( data.size()-1 ) + " ]\n";
    return s;
  }
}
