package com.bhrobotics.morcontrol.devices.output;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.bhrobotics.morcontrol.devices.AnalogState;
import com.bhrobotics.morcontrol.devices.DigitalState;
import com.bhrobotics.morcontrol.devices.DeviceRegistry;
import com.bhrobotics.morcontrol.devices.RelayState;
import com.bhrobotics.morcontrol.devices.UnknownDeviceException;

public class OutputDeviceUpdaterTest extends MockObjectTestCase {
	private DeviceRegistry registry = new DeviceRegistry();
	private OutputDeviceUpdater updater = new OutputDeviceUpdater(registry);
	private VictorDevice motorDevice = new VictorDevice(1, 1);
	private RelayDevice relayDevice = new RelayDevice(1, 1);
	private SolenoidDevice solenoidDevice = new SolenoidDevice(1, 1);
	
	public void setUp() {
		registry.registerMotor("motor", motorDevice);
		registry.registerRelay("relay", relayDevice);
		registry.registerSolenoid("solenoid", solenoidDevice);
	}
	
	public void testUpdateMotor() {
		updater.updateMotor("motor", new AnalogState(0.3));
		assertEquals(new AnalogState(0.3), motorDevice.getState());
	}
	
	public void testUpdateRelay() {
		updater.updateRelay("relay", RelayState.FORWARD);
		assertEquals(RelayState.FORWARD, relayDevice.getState());
	}
	
	public void testUpdateSolenoid() {
		updater.updateSolenoid("solenoid", DigitalState.ON);
		assertEquals(DigitalState.ON, solenoidDevice.getState());
	}
	
	public void testUpdateUnknownMotor() {
		try {
			updater.updateMotor("foo", new AnalogState(0.5));
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
	}
	
	public void testUpdateUnknownRelay() {
		try {
			updater.updateRelay("foo", RelayState.FORWARD);
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
	}
	
	public void testUpdateUnknownSolenoid() {
		try {
			updater.updateSolenoid("foo", DigitalState.ON);
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
	}
	
	public void testObservers() {
		Mock mockObserver = mock(OutputDeviceObserver.class);
		mockObserver.expects(once()).method("updateMotor").with(eq("motor"), eq(motorDevice));
		mockObserver.expects(once()).method("updateRelay").with(eq("relay"), eq(relayDevice));
		mockObserver.expects(once()).method("updateSolenoid").with(eq("solenoid"), eq(solenoidDevice));
		OutputDeviceObserver observer = (OutputDeviceObserver) mockObserver.proxy();
		
		updater.registerObserver(observer);
		updater.updateMotor("motor", new AnalogState(0.6));
		updater.updateRelay("relay", RelayState.FORWARD);
		updater.updateSolenoid("solenoid", DigitalState.ON);
		updater.updateMotor("motor", new AnalogState(0.6));
		updater.updateRelay("relay", RelayState.FORWARD);
		updater.updateSolenoid("solenoid", DigitalState.ON);
		
		updater.unregisterObserver(observer);
		updater.updateMotor("motor", new AnalogState(0.7));
		updater.updateRelay("relay", RelayState.REVERSE);
		updater.updateSolenoid("solenoid", DigitalState.OFF);
	}
}
