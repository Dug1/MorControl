package com.bhrobotics.morcontrol.util;


public class UnknownKeyException extends CausableRuntimeException {
	public UnknownKeyException() {
		super();
	}

	public UnknownKeyException(String s) {
		super(s);
	}
	
	public UnknownKeyException(Throwable cause) {
		super(cause);
	}
	
	public UnknownKeyException(String s, Throwable cause) {
		super(s, cause);
	}
}
