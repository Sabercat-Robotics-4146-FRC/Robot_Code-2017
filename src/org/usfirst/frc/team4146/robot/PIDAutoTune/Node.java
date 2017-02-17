package org.usfirst.frc.team4146.robot.PIDAutoTune;

class Node {
	private double node_P;
	private double node_I;
	private double node_D;
	private double time_taken;
	private double error_after;
	private boolean visited;
	
	public Node(double p, double i, double d) {
		node_P = p;
		node_I = i;
		node_D = d;
		visited = false;
		
	}
	
	public void postRun(double time, double error) {
		time_taken = time;
		error_after = error;
		visited = true;
	}
	
	public boolean checkVisitStatus() {
		return visited;
	}
	
	public double getTime() {
		return time_taken;
	}
	
	public double getError() {
		return error_after;
	}
	
	public double getP() {
		return node_P;
	}

	public double getI() {
		return node_I;
	}

	public double getD() {
		return node_D;
	}
	
}
