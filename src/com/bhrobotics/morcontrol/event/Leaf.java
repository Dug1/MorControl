package com.bhrobotics.morcontrol.event;

public class Leaf extends Node {
	
	public void addChild(Node child)  {
	}
	
	public void removeChild(Node child)  {
	}
	
	public Node[] getChildren() {
		return new Node[0];
	}

	public Deployer register(Handler[] handlers) {
		// TODO register method for Leaf
		return null;
	}
}