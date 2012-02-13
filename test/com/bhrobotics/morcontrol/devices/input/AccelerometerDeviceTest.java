package com.bhrobotics.morcontrol.devices.input;

import com.bhrobotics.morcontrol.devices.AnalogState;

import junit.framework.TestCase;

public class AccelerometerDeviceTest extends TestCase {
	private AccelerometerDevice device = new AccelerometerDevice(1, 1);
	
	public void testSlotAndChannel() {
		assertEquals(1, device.getSlot());
		assertEquals(1, device.getChannel());
	}
	
	public void testZeroAndSensitivity() {
		assertEquals(0.0, device.getZero(), 0.01);
		assertEquals(0.0, device.getSensitivity(), 0.01);
		
		device.setZero(0.3);
		assertEquals(0.3, device.getZero(), 0.01);
		
		device.setSensitivity(0.4);
		assertEquals(0.4, device.getSensitivity(), 0.01);
	}
	
	public void testAcceleration() {
		assertEquals(new AnalogState(0.0), device.getAcceleration());
		
		device.setAcceleration(new AnalogState(0.3));
		assertEquals(new AnalogState(0.3), device.getAcceleration());
	}
}
