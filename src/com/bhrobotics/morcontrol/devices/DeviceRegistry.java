package com.bhrobotics.morcontrol.devices;

import com.bhrobotics.morcontrol.devices.input.AccelerometerDevice;
import com.bhrobotics.morcontrol.devices.input.JoystickDevice;
import com.bhrobotics.morcontrol.devices.output.MotorDevice;
import com.bhrobotics.morcontrol.devices.output.RelayDevice;
import com.bhrobotics.morcontrol.devices.output.SolenoidDevice;
import com.bhrobotics.morcontrol.util.Hash;
import com.bhrobotics.morcontrol.util.UnknownKeyException;

public class DeviceRegistry {
	private Hash motorRegistry = new Hash();
	private Hash relayRegistry = new Hash();
	private Hash solenoidRegistry = new Hash();
	private Hash accelerometerRegistry = new Hash();
	private Hash joystickRegistry = new Hash();
	private Hash driverStationRegistry = new Hash();
	
	public void registerMotor(String name, MotorDevice device) {
		motorRegistry.put(name, device);
	}
	
	public MotorDevice getMotor(String name) {
		try {
			return (MotorDevice) motorRegistry.fetch(name);
		} catch (UnknownKeyException e) {
			throw new UnknownDeviceException(e);
		}
	}
	
	public void unregisterMotor(String name) {
		motorRegistry.remove(name);
	}
	
	public void registerRelay(String name, RelayDevice device) {
		relayRegistry.put(name, device);
	}
	
	public RelayDevice getRelay(String name) {
		try {
			return (RelayDevice) relayRegistry.fetch(name);
		} catch (UnknownKeyException e) {
			throw new UnknownDeviceException(e);
		}
	}
	
	public void unregisterRelay(String name) {
		relayRegistry.remove(name);
	}
	
	public void registerSolenoid(String name, SolenoidDevice device) {
		solenoidRegistry.put(name, device);
	}
	
	public SolenoidDevice getSolenoid(String name) {
		try {
			return (SolenoidDevice) solenoidRegistry.fetch(name);
		} catch (UnknownKeyException e) {
			throw new UnknownDeviceException(e);
		}
	}
	
	public void unregisterSolenoid(String name) {
		solenoidRegistry.remove(name);
	}

	public void registerAccelerometer(String name, AccelerometerDevice device) {
		accelerometerRegistry.put(name, device);
	}
	
	public AccelerometerDevice getAccelerometer(String name) {
		try {
			return (AccelerometerDevice) accelerometerRegistry.fetch(name);
		} catch (UnknownKeyException e) {
			throw new UnknownDeviceException(e);
		}
	}
	
	public void unregisterAccelerometer(String name) {
		accelerometerRegistry.remove(name);
	}
	
	public void registerJoystick(String name, JoystickDevice device) {
		joystickRegistry.put(name, device);
	}
	
	public JoystickDevice getJoystick(String name) {
		try {
			return (JoystickDevice) joystickRegistry.fetch(name);
		} catch (UnknownKeyException e) {
			throw new UnknownDeviceException(e);
		}
	}
	
	public void unregisterJoystick(String name) {
		joystickRegistry.remove(name);
	}
	
	public void registerDriverStation(String name, DriverStationDevice device) {
		driverStationRegistry.put(name, device);
	}
	
	public DriverStationDevice getDriverStation(String name) {
		try {
			return (DriverStationDevice) driverStationRegistry.fetch(name);
		} catch (UnknownKeyException e) {
			throw new UnknownDeviceException(e);
		}
	}
	
	public void unregisterDriverStation(String name) {
		driverStationRegistry.remove(name);
	}
}