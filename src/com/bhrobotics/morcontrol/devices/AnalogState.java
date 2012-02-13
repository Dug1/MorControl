package com.bhrobotics.morcontrol.devices;

public class AnalogState {
	private double value;
	
	public AnalogState(double value) {
		if (value > 1.0 || value < -1.0) {
			throw new InvalidStateException("Invalid value " + value + ".");
		}

		this.value = value;
	}
	
	public double toDouble() {
		return value;
	}
	
	public boolean equals(Object other) {
		if (other instanceof AnalogState) {
			AnalogState otherState = (AnalogState) other;
			return value == otherState.toDouble();
		} else {
			return false;
		}
	}
}
