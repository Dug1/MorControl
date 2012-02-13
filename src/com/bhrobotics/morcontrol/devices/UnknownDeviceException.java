package com.bhrobotics.morcontrol.devices;

import com.bhrobotics.morcontrol.util.CausableRuntimeException;

public class UnknownDeviceException extends CausableRuntimeException {
	public UnknownDeviceException() {
		super();
	}

	public UnknownDeviceException(String s) {
		super(s);
	}

	public UnknownDeviceException(Throwable cause) {
		super(cause);
	}

	public UnknownDeviceException(String s, Throwable cause) {
		super(s, cause);
	}
}
