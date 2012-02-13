package com.bhrobotics.morcontrol.devices.output;

import com.bhrobotics.morcontrol.devices.DigitalState;
import com.bhrobotics.morcontrol.devices.output.SolenoidDevice;

import junit.framework.TestCase;

public class SolenoidDeviceTest extends TestCase {
	public void testSlotAndChannel() {
		SolenoidDevice device = new SolenoidDevice(2, 4);
		assertEquals(2, device.getSlot());
		assertEquals(4, device.getChannel());
	}
	
	public void testState() {
		SolenoidDevice device = new SolenoidDevice(2, 4);
		
		DigitalState defaultState = DigitalState.OFF;
		assertEquals(defaultState, device.getState());

		DigitalState newState = DigitalState.ON;
		assertTrue(device.update(newState));
		assertEquals(newState, device.getState());
		
		assertFalse(device.update(newState));
		assertEquals(newState, device.getState());
	}
}
