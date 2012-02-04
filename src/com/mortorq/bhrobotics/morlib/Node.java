package com.mortorq.bhrobotics.morlib;

import java.util.Hashtable;

public abstract class Node {
	private Hashtable data = new Hashtable();
	
	public void putData(String tag, String info) {
		data.put(tag, info);
	}
	
	public String getData(String tag) {
		return (String)data.get(tag);
	}
	
	public Hashtable getData() {
		return data;
	}
	
	public abstract void addChild(Node e);
	public abstract void removeChild(Node e);
	public abstract Node[] getChildren();
	public abstract Deployer register(Handler[] handlers);
}