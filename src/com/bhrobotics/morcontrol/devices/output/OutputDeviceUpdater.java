package com.bhrobotics.morcontrol.devices.output;

import java.util.Enumeration;
import java.util.Vector;

import com.bhrobotics.morcontrol.devices.AnalogState;
import com.bhrobotics.morcontrol.devices.DigitalState;
import com.bhrobotics.morcontrol.devices.DeviceRegistry;
import com.bhrobotics.morcontrol.devices.RelayState;

public class OutputDeviceUpdater {
	private Vector observers = new Vector();
	private DeviceRegistry registry;
	
	public OutputDeviceUpdater(DeviceRegistry registry) {
		this.registry = registry;
	}
	
	public void updateMotor(String name, AnalogState state) {
		MotorDevice device = registry.getMotor(name);
		boolean updated = device.update(state);
		
		if (updated) {
			Enumeration e = observers.elements(); 
			while (e.hasMoreElements()) {
				OutputDeviceObserver observer = (OutputDeviceObserver) e.nextElement();
				observer.updateMotor(name, device);
			}
		}
	}
	
	public void updateRelay(String name, RelayState state) {
		RelayDevice device = registry.getRelay(name);
		boolean updated = device.update(state);
		
		if (updated) {
			Enumeration e = observers.elements(); 
			while (e.hasMoreElements()) {
				OutputDeviceObserver observer = (OutputDeviceObserver) e.nextElement();
				observer.updateRelay(name, device);
			}
		}
	}

	public void updateSolenoid(String name, DigitalState state) {
		SolenoidDevice device = registry.getSolenoid(name);
		boolean updated = device.update(state);
		
		if (updated) {
			Enumeration e = observers.elements(); 
			while (e.hasMoreElements()) {
				OutputDeviceObserver observer = (OutputDeviceObserver) e.nextElement();
				observer.updateSolenoid(name, device);
			}
		}
	}

	public void registerObserver(OutputDeviceObserver observer) {
		observers.addElement(observer);
	}
	
	public void unregisterObserver(OutputDeviceObserver observer) {
		observers.removeElement(observer);
	}
}