package org.usfirst.frc.team4146.robot.PIDAutoTune;
import java.util.ArrayList;
import java.util.Random;


public class NodeHandler {
	public Node globalBest;
	public Node thisRunBest;
	public Node currentNode;
	
	ArrayList<Node> vertices = new ArrayList<Node>(4);
	
	private Node unVisitedNode;
	
	
	public NodeHandler(Node startNode1, Node startNode2, Node startNode3, Node startNode4) {
		vertices.add(startNode1);
		vertices.add(startNode2);
		vertices.add(startNode3);
		vertices.add(startNode4);
		thisRunBest = startNode1;
		globalBest = startNode1;
	}
	
	public void findTestNode() {
		if( isAnyNodeUnVisited() ) {
			currentNode = unVisitedNode; 
		} else {
			currentNode = throwAndCreateNode();
		}
	}
	
	public Node getCurrentNode() {
		return currentNode;
	}
	
	public void setCurrentNodeTimeAndError(double time, double error) {
		currentNode.postRun(time, error);
		if(currentNode.getTime() < thisRunBest.getTime()) {
			thisRunBest = currentNode;
		}
		 
		if(currentNode.getTime() < globalBest.getTime()) {
			globalBest = currentNode;
		}
	}
	
	public Node getThisRunBest() {
		return thisRunBest;
	}
	
	public Node getGlobalBest() {
		return globalBest;
	}
	
	private boolean isAnyNodeUnVisited() {
		for(Node checkNode : vertices) {
			if(checkNode.checkVisitStatus() == false)
			{
				unVisitedNode = checkNode;
				return true;
			}
		}
		return false;
	}
	
	private Node throwAndCreateNode() {
		Random rand = new Random();
		Node bestNode = vertices.get(0);
		Node worstNode = vertices.get(0);
		for(Node checkNode : vertices) {
			if(checkNode.getTime() > worstNode.getTime() )
			{
				worstNode = checkNode;
			}
			
			if(checkNode.getTime() < bestNode.getTime())
			{
				bestNode = checkNode;
			}
			
		}
		
		if(bestNode.equals(worstNode))
		{
			int one = rand.nextInt(4);
			int two = rand.nextInt(4);
			while(one == two)
			{
				two = rand.nextInt(4);
			}
			
			bestNode = vertices.get(one);
			worstNode = vertices.get(two);
		}
		
		double p_dif = bestNode.getP() - worstNode.getP();
		double i_dif = bestNode.getI() - worstNode.getI();
		double d_dif = bestNode.getD() - worstNode.getD();
	
		Node newNode = vertices.set(vertices.indexOf(worstNode), new Node(bestNode.getP() + p_dif, bestNode.getI() + i_dif, bestNode.getD() + d_dif));
		return newNode;
	}
}
	

