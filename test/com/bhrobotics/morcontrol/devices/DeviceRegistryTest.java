package com.bhrobotics.morcontrol.devices;

import com.bhrobotics.morcontrol.devices.DeviceRegistry;
import com.bhrobotics.morcontrol.devices.UnknownDeviceException;
import com.bhrobotics.morcontrol.devices.input.AccelerometerDevice;
import com.bhrobotics.morcontrol.devices.input.JoystickDevice;
import com.bhrobotics.morcontrol.devices.output.RelayDevice;
import com.bhrobotics.morcontrol.devices.output.SolenoidDevice;
import com.bhrobotics.morcontrol.devices.output.VictorDevice;

import junit.framework.TestCase;

public class DeviceRegistryTest extends TestCase {
	private DeviceRegistry registry = new DeviceRegistry();
	private VictorDevice motorDevice = new VictorDevice(1, 1);
	private RelayDevice relayDevice = new RelayDevice(1, 1);
	private SolenoidDevice solenoidDevice = new SolenoidDevice(1, 1);
	private AccelerometerDevice accelerometerDevice = new AccelerometerDevice(1, 1);
	private JoystickDevice joystickDevice = new JoystickDevice(0, 3, 12);
	private DriverStationDevice driverStationDevice = new DriverStationDevice();
	
	public void testRegisterAndUnregisterMotorDevices() {
		try {
			registry.getMotor("left drive");
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
		
		registry.registerMotor("left drive", motorDevice);
		assertEquals(motorDevice, registry.getMotor("left drive"));
		registry.unregisterMotor("left drive");
		
		try {
			registry.getMotor("left drive");
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
	}
	
	public void testRegisterAndUnregisterRelayDevices() {
		try {
			registry.getRelay("compressor");
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
		
		registry.registerRelay("compressor", relayDevice);
		assertEquals(relayDevice, registry.getRelay("compressor"));
		registry.unregisterRelay("compressor");
		
		try {
			registry.getRelay("compressor");
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
	}
	
	public void testRegisterAndUnregisterSolenoidDevices() {
		try {
			registry.getSolenoid("elbow");
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
		
		registry.registerSolenoid("elbow", solenoidDevice);
		assertEquals(solenoidDevice, registry.getSolenoid("elbow"));
		registry.unregisterSolenoid("elbow");
		
		try {
			registry.getSolenoid("elbow");
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
	}
	
	public void testRegisterAndUnregisterAccelerometerDevices() {
		try {
			registry.getAccelerometer("accel");
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
		
		registry.registerAccelerometer("accel", accelerometerDevice);
		assertEquals(accelerometerDevice, registry.getAccelerometer("accel"));
		registry.unregisterAccelerometer("accel");
		
		try {
			registry.getAccelerometer("accel");
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
	}

	public void testRegisterAndUnregisterJoystickDevices() {
		try {
			registry.getJoystick("drive");
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
		
		registry.registerJoystick("drive", joystickDevice);
		assertEquals(joystickDevice, registry.getJoystick("drive"));
		registry.unregisterJoystick("drive");
		
		try {
			registry.getJoystick("drive");
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
	}

	public void testRegisterAndUnregisterDriverStationDevices() {
		try {
			registry.getDriverStation("ds");
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
		
		registry.registerDriverStation("ds", driverStationDevice);
		assertEquals(driverStationDevice, registry.getDriverStation("ds"));
		registry.unregisterDriverStation("ds");
		
		try {
			registry.getDriverStation("ds");
			fail("Exception not thrown.");
		} catch (UnknownDeviceException e) {
		}
	}
}
