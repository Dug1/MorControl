package com.bhrobotics.morcontrol.util;

public abstract class CausableRuntimeException extends RuntimeException {
	private Throwable cause;
	
	public CausableRuntimeException() {
		super();
	}

	public CausableRuntimeException(String s) {
		super(s);
	}
	
	public CausableRuntimeException(Throwable cause) {
		super();
		this.cause = cause;
	}
	
	public CausableRuntimeException(String s, Throwable cause) {
		super(s);
		this.cause = cause;
	}
	
	public Throwable getCause() {
		return cause;
	}
}
