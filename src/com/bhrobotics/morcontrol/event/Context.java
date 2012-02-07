package com.bhrobotics.morcontrol.event;

public class Context {
	private StringBuffer buffer;
	private Node currentNode;
	
	public Context(StringBuffer b, Node n) {
		setBuffer(b);
		setCurrentNode(n);
	}

	public StringBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(StringBuffer buffer) {
		this.buffer = buffer;
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}
}