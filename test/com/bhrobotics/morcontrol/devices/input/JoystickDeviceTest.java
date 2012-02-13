package com.bhrobotics.morcontrol.devices.input;

import com.bhrobotics.morcontrol.devices.AnalogState;
import com.bhrobotics.morcontrol.devices.DigitalState;

import junit.framework.TestCase;

public class JoystickDeviceTest extends TestCase {
	private JoystickDevice device = new JoystickDevice(0, 5, 10);
	
	public void testPort() {
		assertEquals(0, device.getPort());
	}
	
	public void testNumberOfAxes() {
		assertEquals(5, device.getNumberOfAxes());
	}
	
	public void testNumberOfButtons() {
		assertEquals(10, device.getNumberOfButtons());
	}
	
	public void testAxis() {
		assertEquals(new AnalogState(0.0), device.getAxis(0));
		
		device.setAxis(0, new AnalogState(0.5));
		assertEquals(new AnalogState(0.5), device.getAxis(0));
	}
	
	public void testButtons() {
		assertEquals(DigitalState.OFF, device.getButton(0));
		
		device.setButton(0, DigitalState.ON);
		assertEquals(DigitalState.ON, device.getButton(0));
	}
}
