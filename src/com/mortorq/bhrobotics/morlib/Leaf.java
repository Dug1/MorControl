package com.mortorq.bhrobotics.morlib;

import java.util.Vector;

public class Leaf implements Node {
	private String type;
	private String data;
	
	public Leaf(String t, String d) {
		type =t;
		data = d;
	}
	
	public void addNode(Node child)  {
	}
	
	public Vector getChildren() {
		return new Vector();
	}
	
	public void setType(String t) {
		type = t;
	}
	
	public String getType() {
		return type;
	}
	public void setData(String d) {
		data = d;
	}
	
	public String getData() {
		return data;
	}
}