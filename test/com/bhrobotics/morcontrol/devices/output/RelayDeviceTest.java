package com.bhrobotics.morcontrol.devices.output;

import com.bhrobotics.morcontrol.devices.RelayState;
import com.bhrobotics.morcontrol.devices.output.RelayDevice;

import junit.framework.TestCase;

public class RelayDeviceTest extends TestCase {
	public void testSlotAndChannel() {
		RelayDevice device = new RelayDevice(2, 4);
		assertEquals(2, device.getSlot());
		assertEquals(4, device.getChannel());
	}
	
	public void testState() {
		RelayDevice device = new RelayDevice(2, 4);
		
		RelayState defaultState = RelayState.OFF;
		assertEquals(defaultState, device.getState());

		RelayState newState = RelayState.FORWARD;
		assertTrue(device.update(newState));
		assertEquals(newState, device.getState());
		
		assertFalse(device.update(newState));
		assertEquals(newState, device.getState());
	}
}
