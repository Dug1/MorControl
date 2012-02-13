package com.bhrobotics.morcontrol.devices;

import com.bhrobotics.morcontrol.util.CausableRuntimeException;

public class InvalidStateException extends CausableRuntimeException {
	public InvalidStateException() {
		super();
	}

	public InvalidStateException(String s) {
		super(s);
	}
	
	public InvalidStateException(Throwable cause) {
		super(cause);
	}
	
	public InvalidStateException(String s, Throwable cause) {
		super(s, cause);
	}
}
