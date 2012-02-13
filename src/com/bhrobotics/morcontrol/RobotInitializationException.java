package com.bhrobotics.morcontrol;

import com.bhrobotics.morcontrol.util.CausableRuntimeException;

public class RobotInitializationException extends CausableRuntimeException {
	public RobotInitializationException() {
		super();
	}

	public RobotInitializationException(String s, Throwable cause) {
		super(s, cause);
	}

	public RobotInitializationException(String s) {
		super(s);
	}

	public RobotInitializationException(Throwable cause) {
		super(cause);
	}
}
