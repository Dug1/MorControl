package com.mortorq.bhrobotics.morlib;

public class Context {
	public StringBuffer buffer;
	public Node currentNode;
	
	public Context(StringBuffer b, Node n) {
		buffer = b;
		currentNode = n;
	}
}