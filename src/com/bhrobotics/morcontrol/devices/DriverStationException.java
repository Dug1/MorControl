package com.bhrobotics.morcontrol.devices;

import com.bhrobotics.morcontrol.util.CausableRuntimeException;

public class DriverStationException extends CausableRuntimeException {
	public DriverStationException() {
	}

	public DriverStationException(String s) {
		super(s);
	}

	public DriverStationException(Throwable cause) {
		super(cause);
	}

	public DriverStationException(String s, Throwable cause) {
		super(s, cause);
	}
}
