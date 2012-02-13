package com.bhrobotics.morcontrol.devices;

public class DigitalState {
	public static final DigitalState OFF = new DigitalState(false);
	public static final DigitalState ON = new DigitalState(true);
	
	private boolean value;
	
	private DigitalState(boolean value) {
		this.value = value;
	}
	
	public boolean toBoolean() {
		return value;
	}
}
