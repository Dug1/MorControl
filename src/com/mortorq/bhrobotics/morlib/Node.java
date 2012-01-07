package com.mortorq.bhrobotics.morlib;

import java.util.Hashtable;
import java.util.Vector;

public interface Node {
	public String getType();
	public void setType(String name);
	public Hashtable getData();
	public Object getData(String tag);
	public void putData(String tag, Object data);
	public void addNode(Node e);
	public void removeNode(Node e);
	public Node[] getChildren();
}