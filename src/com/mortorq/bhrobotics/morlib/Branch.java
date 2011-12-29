package com.mortorq.bhrobotics.morlib;

import java.util.Vector;
import java.util.Enumeration;

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
	
	public Node[] getChildren() {
		Node[] children = new Node[nodes.size()];
		Enumeration e = nodes.elements();
		int i = 0;
		while (e.hasMoreElements()) {
			children[i] = (Node)e.nextElement();
		}
		return children;
	}
	
	public void setType(String t) {
		type = t;
	}
	
	public String getType() {
		return type;
	}
}