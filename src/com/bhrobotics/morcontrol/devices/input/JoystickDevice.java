package com.bhrobotics.morcontrol.devices.input;

import com.bhrobotics.morcontrol.devices.AnalogState;
import com.bhrobotics.morcontrol.devices.DigitalState;
import com.bhrobotics.morcontrol.devices.InvalidStateException;
import com.bhrobotics.morcontrol.util.OperatingSystem;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickDevice {	
	
	private int port;
	private int numberOfAxes;
	private int numberOfButtons;
	private Joystick joystick;
	
	private AnalogState[] axes;
	private DigitalState[] buttons;
	
	private class CustomizableJoystick extends Joystick {
		private CustomizableJoystick(int port, int numberOfAxes, int numberOfButtons) {
			super(port, numberOfAxes, numberOfButtons);
		}
	}
	
	public JoystickDevice(int port, int numberOfAxes, int numberOfButtons) {
		this.port = port;
		this.numberOfAxes = numberOfAxes;
		this.numberOfButtons = numberOfButtons;
		
		if (OperatingSystem.isCRio()) {
			joystick = new CustomizableJoystick(port, numberOfAxes, numberOfButtons);
		} else {
			axes = new AnalogState[numberOfAxes];
			for (int i = 0; i < numberOfAxes; i++) {
				axes[i] = new AnalogState(0.0);
			}
			
			buttons = new DigitalState[numberOfButtons];
			for (int i = 0; i < numberOfButtons; i++) {
				buttons[i] = DigitalState.OFF;
			}
		}
	}
	
	public int getPort() {
		return port;
	}

	public int getNumberOfAxes() {
		return numberOfAxes;
	}
	
	public int getNumberOfButtons() {
		return numberOfButtons;
	}
	
	public AnalogState getAxis(int axis) {
		if (OperatingSystem.isCRio()) {
			return new AnalogState(joystick.getRawAxis(axis));
		} else {
			return axes[axis];
		}
	}
	
	public void setAxis(int axis, AnalogState state) {
		if (OperatingSystem.isCRio()) {
			throw new InvalidStateException("Cannot override input device states on cRIO.");
		}
		
		axes[axis] = state;
	}
	
	public DigitalState getButton(int button) {
		if (OperatingSystem.isCRio()) {
			boolean value = joystick.getRawButton(button);
			if (value) {
				return DigitalState.ON;
			} else {
				return DigitalState.OFF;
			}
		} else {
			return buttons[button];
		}
	}
	
	public void setButton(int button, DigitalState state) {
		if (OperatingSystem.isCRio()) {
			throw new InvalidStateException("Cannot override input device states on cRIO.");
		}
		
		buttons[button] = state;
	}
}