package com.mortorq.bhrobotics.morlib;

public class TriggerException extends Exception {
	private String message;
	
	public TriggerException() {
		super();
		message = "Trigger was malformed";
	}
	
	public TriggerException(Node node) {
		message = "Node of type " + node.getType() + " creates malformed Trigger";
	}
	
	public String getMessage() {
		return message;
	}
}