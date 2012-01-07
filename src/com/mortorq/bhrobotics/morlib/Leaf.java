package com.mortorq.bhrobotics.morlib;

import java.util.Vector;
import java.util.Hashtable;

public class Leaf implements Node {
	private String type;
	private Hashtable data;
	
	public Leaf(String t) {
		type =t;
		data = new Hashtable();
	}
	
	public void addNode(Node child)  {
	}
	
	public void removeNode(Node child)  {
	}
	
	public Node[] getChildren() {
		return new Node[0];
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