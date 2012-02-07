package com.bhrobotics.morcontrol.event;

import java.util.Enumeration;
import java.util.Vector;

public class Branch extends Node {
	private Vector nodes = new Vector();
	
	public void addChild(Node child)  {
		nodes.addElement(child);
	}
	
	public void removeChild(Node child)  {
		nodes.removeElement(child);
	}
	
	public Node[] getChildren() {
		Node[] children = new Node[nodes.size()];
		Enumeration e = nodes.elements();
		int i = 0;
		while (e.hasMoreElements()) {
			children[i] = (Node)e.nextElement();
			i++;
		}
		return children;
	}

	public Deployer register(Handler[] handlers) {
		// TODO register method for Branch
		return null;
	}
}