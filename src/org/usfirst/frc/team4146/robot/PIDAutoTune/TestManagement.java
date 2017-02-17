package org.usfirst.frc.team4146.robot.PIDAutoTune;

import org.usfirst.frc.team4146.robot.*;

public class TestManagement {
	int runNumber = 0;
	int testNumber = 0;
	int testThisRun = 0;
	
	double timeOut = 0;
	Node currentNode;
	
	private enum RunMode {
		Heading_Mode, 
		MoveDistance_Mode
	}
	
	Heading tune_heading;
	MoveDistance tune_md;
	
	RunMode mode;
	
	NodeHandler handler;
	
	IterativeTimer testTimer = new IterativeTimer();
	
	public TestManagement(Heading head, Node one, Node two, Node three, Node four, double timeout) {
		tune_heading = head;
		handler = new NodeHandler(one, two, three, four);
		timeOut = timeout;
	}
	
	public TestManagement(MoveDistance move, Node one, Node two, Node three, Node four, double timeout) {
		tune_md = move;
		handler = new NodeHandler(one, two, three, four);
		timeOut = timeout;
	}
	
	public void run() {
		switch( mode ) {
		
		case Heading_Mode:
			Node CurrentNode;
			while( true ) {
				testNumber++;
				testThisRun++;
				handler.findTestNode();
				currentNode = handler.getCurrentNode();
				tune_heading.set_vars(currentNode.getP(), currentNode.getI(), currentNode.getD());
				testTimer.reset();
				//while( error > acceptableError and time < timeOut ) {
				//pid.update
				//arcadeDrive(pid.get)
				//}
				//if(error < acceptableError) {
				//handler.setTimeAndError(time, error)
				//} else {
				//handler.setTimeAndError(10000.0, error)
				//}
				//arcadeDrive(0);
				//wait(5 sec);
				//print handler.getRunBest p i d and time
				//print handler.getGlobalBest p i d and time
			}
			
			
			
		case MoveDistance_Mode:
			//Same as Heading Mode but adapted for Move Distance
			break;
		
		}
	}
}
