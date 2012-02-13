package com.bhrobotics.morcontrol.util;

public abstract class CausableException extends Exception {
	private Throwable cause;
	
	public CausableException() {
		super();
	}

	public CausableException(String s) {
		super(s);
	}
	
	public CausableException(Throwable cause) {
		super();
		this.cause = cause;
	}
	
	public CausableException(String s, Throwable cause) {
		super(s);
		this.cause = cause;
	}
	
	public Throwable getCause() {
		return cause;
	}
}
