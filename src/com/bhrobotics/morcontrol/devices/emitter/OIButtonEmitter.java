package com.bhrobotics.morcontrol.devices.emitter;

import com.bhrobotics.morcontrol.devices.DigitalState;
import com.bhrobotics.morcontrol.devices.DriverStationDevice;
import com.bhrobotics.morcontrol.event.Emitter;
import com.bhrobotics.morcontrol.event.Event;
import com.bhrobotics.morcontrol.event.EventBuilder;
import com.bhrobotics.morcontrol.event.Reactor;

public class OIButtonEmitter extends Emitter {
	public static final String NEW_BUTTON_VALUE = "io button value";
	
	private String name;
	private int channel;
	private DigitalState currentState;
	private DriverStationDevice device;
	
	public OIButtonEmitter(DriverStationDevice oi, String name, int channel) {
		device = oi;
		this.name = name;
		this.channel = channel;
	}
	
	public void tick() {
		if(digitalChanged()) {
			Event event = new EventBuilder(name).with(NEW_BUTTON_VALUE, currentState.toBoolean()).build();
			Reactor.getInstance().addEvent(event);
		}
	}

	public void newData() {
	}

	public boolean digitalChanged() {
		if(!device.getDigital(channel).equals(currentState)) {
			currentState = device.getDigital(channel);
			return true;
		}
		return false;
	}

	public void start() {
	}

	public void stop() {
	}
}