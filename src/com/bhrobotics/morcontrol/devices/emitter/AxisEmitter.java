package com.bhrobotics.morcontrol.devices.emitter;

import com.bhrobotics.morcontrol.devices.AnalogState;
import com.bhrobotics.morcontrol.devices.input.JoystickDevice;
import com.bhrobotics.morcontrol.event.Emitter;
import com.bhrobotics.morcontrol.event.Event;
import com.bhrobotics.morcontrol.event.EventBuilder;
import com.bhrobotics.morcontrol.event.Reactor;

public class AxisEmitter extends Emitter {
	public static final String NEW_AXIS_VALUE = "new axis value";
	public static final String AXIS_INDEX = "axis index";
	
	private String name;
	private JoystickDevice joystick;
	private AnalogState currentState;
	private int channel;
	
	public AxisEmitter(String name, JoystickDevice joystick, int channel) {
		this.name = name;
		this.joystick = joystick;
		this.channel = channel;
		currentState = new AnalogState(0.0);
		 
	}

	public void tick() {
		if(axisChanged()) {
			Event event = new EventBuilder(name).with(AXIS_INDEX, channel).with(NEW_AXIS_VALUE, currentState.toDouble()).build();
			currentState = getAxisValue();
			Reactor.getInstance().addEvent(event);
		}
	}

	public void newData() {
		//ignore
	}
	
	private boolean axisChanged() {
		AnalogState state = joystick.getAxis(channel);
		return !currentState.equals(state); 
	}
	
	public AnalogState getAxisValue() {
		return joystick.getAxis(channel);
	}

	public void start() {	
	}

	public void stop() {	
	}
}