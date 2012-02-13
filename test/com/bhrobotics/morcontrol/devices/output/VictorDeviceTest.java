package com.bhrobotics.morcontrol.devices.output;

import com.bhrobotics.morcontrol.devices.AnalogState;
import com.bhrobotics.morcontrol.devices.output.VictorDevice;

import junit.framework.TestCase;

public class VictorDeviceTest extends TestCase {
	public void testSlotAndChannel() {
		VictorDevice device = new VictorDevice(2, 4);
		assertEquals(2, device.getSlot());
		assertEquals(4, device.getChannel());
	}
	
	public void testState() {
		VictorDevice device = new VictorDevice(2, 4);
		
		AnalogState defaultState = new AnalogState(0.0);
		assertEquals(defaultState, device.getState());

		AnalogState newState = new AnalogState(1.0);
		assertTrue(device.update(newState));
		assertEquals(newState, device.getState());
		
		assertFalse(device.update(newState));
		assertEquals(newState, device.getState());
	}
}
