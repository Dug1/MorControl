package com.bhrobotics.morcontrol.devices.emitter;

import com.bhrobotics.morcontrol.devices.DigitalState;
import com.bhrobotics.morcontrol.devices.input.JoystickDevice;
import com.bhrobotics.morcontrol.event.Emitter;
import com.bhrobotics.morcontrol.event.Event;
import com.bhrobotics.morcontrol.event.EventBuilder;
import com.bhrobotics.morcontrol.event.Reactor;

public class JoystickButtonEmitter extends Emitter {
	public static final String NEW_BUTTON_VALUE = "joystick button value";
	
	private String name;
	private int channel;
	private DigitalState currentState;
	private JoystickDevice device;
	
	public JoystickButtonEmitter(JoystickDevice joystick, String name, int channel) {
		device = joystick;
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
		if(!device.getButton(channel).equals(currentState)) {
			currentState = device.getButton(channel);
			return true;
		}
		return false;
	}

	public void start() {	
	}

	public void stop() {
	}
}