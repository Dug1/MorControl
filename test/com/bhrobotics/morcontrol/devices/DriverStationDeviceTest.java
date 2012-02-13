package com.bhrobotics.morcontrol.devices;

import junit.framework.TestCase;

public class DriverStationDeviceTest extends TestCase {
	private DriverStationDevice device = new DriverStationDevice();
	
	public void testDigitalType() {
		for (int i = 1; i < 17; i++) {
			assertEquals(IOType.OUTPUT, device.getType(i));
		}
		
		try {
			device.getType(17);
			fail("Exception not thrown.");
		} catch (DriverStationException e) {
		}
	}
	
	public void testDigital() {
		for (int i = 1; i < 17; i++) {
			assertEquals(DigitalState.OFF, device.getDigital(i));
			device.setDigital(i, DigitalState.ON);
			assertEquals(DigitalState.ON, device.getDigital(i));
		}
	}
}
