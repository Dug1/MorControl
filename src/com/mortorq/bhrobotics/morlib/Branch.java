package com.mortorq.bhrobotics.morlib;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;

public class Branch implements Node {
	private Vector nodes;
	private String type;
	private Hashtable data;
	
	public Branch(String t) {
		type = t;
		nodes = new Vector();
		data = new Hashtable();
	}
	
	public void addNode(Node child)  {
		nodes.addElement(child);
	}
	
	public void removeNode(Node child)  {
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
	
	public void setType(String t) {
		type = t;
	}
	
	public String getType() {
		return type;
	}
	
	public Hashtable getData() {
		return data;
	}
	
	public Object getData(String tag) {
		return data.get(tag);
	}
	
	public void removeData(String tag) {
		data.remove(tag);
	}

	public void putData(String tag, Object newData) {
		data.put(tag, newData);
	}
}