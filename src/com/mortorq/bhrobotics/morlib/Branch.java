package com.mortorq.bhrobotics.morlib;

import java.util.Vector;

public class Branch implements Node {
	private Vector nodes;
	private String type;
	
	public Branch(String t) {
		type = t;
		nodes = new Vector();
	}
	
	public void addNode(Node child)  {
		nodes.addElement(child);
	}
	
	public Vector getChildren() {
		return nodes;
	}
	
	public void setType(String t) {
		type = t;
	}
	
	public String getType() {
		return type;
	}
}