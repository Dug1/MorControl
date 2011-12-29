package com.mortorq.bhrobotics.morlib;

import java.util.Vector;

public interface Node {
	public String getType();
	public void setType(String name);
	public void addNode(Node e);
	public Node[] getChildren();
}