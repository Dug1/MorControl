package com.bhrobotics.morcontrol.devices;

import com.bhrobotics.morcontrol.devices.InvalidStateException;

import junit.framework.TestCase;

public class AnalogStateTest extends TestCase {
	public void testValue() {
		AnalogState state = new AnalogState(0.2);
		assertEquals(0.2, state.toDouble(), 0.01);
	}
	
	public void testInvalidValue() {
		try {
			new AnalogState(3.2);
			fail("Exception not thrown.");
		} catch (InvalidStateException e) {
		}
		
		try {
			new AnalogState(-3.2);
			fail("Exception not thrown.");
		} catch (InvalidStateException e) {
		}
	}
	
	public void testEquals() {
		AnalogState state1 = new AnalogState(0.0);
		AnalogState state2 = new AnalogState(1.0);
		AnalogState state3 = new AnalogState(1.0);
		
		assertFalse(state1.equals(state2));
		assertFalse(state1.equals(state3));
		assertEquals(state2, state3);
	}
}
