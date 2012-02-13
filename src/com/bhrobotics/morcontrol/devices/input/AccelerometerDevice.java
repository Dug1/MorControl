package com.bhrobotics.morcontrol.devices.input;

import com.bhrobotics.morcontrol.devices.AnalogState;
import com.bhrobotics.morcontrol.devices.InvalidStateException;
import com.bhrobotics.morcontrol.devices.SingleChannelDevice;
import com.bhrobotics.morcontrol.util.OperatingSystem;

import edu.wpi.first.wpilibj.Accelerometer;

public class AccelerometerDevice extends SingleChannelDevice {
	private double sensitivity;
	private double zero;
	private AnalogState acceleration = new AnalogState(0.0);
	private Accelerometer accelerometer;
	
	public AccelerometerDevice(int slot, int channel) {
		super(slot, channel);
		
		if (OperatingSystem.isCRio()) {
			accelerometer = new Accelerometer(slot, channel);
		}
	}
	
	public AccelerometerDevice(int slot, int channel, double sensitivity) {
		this(slot, channel);
		setSensitivity(sensitivity);
	}

	public AccelerometerDevice(int slot, int channel, double sensitivity, double zero) {
		this(slot, channel, sensitivity);
		setZero(zero);
	}
	
	public AnalogState getAcceleration() {
		if (OperatingSystem.isCRio()) {
			return new AnalogState(accelerometer.getAcceleration());
		} else {
			return acceleration;
		}
	}

	public void setAcceleration(AnalogState acceleration) {
		if (OperatingSystem.isCRio()) {
			throw new InvalidStateException("Cannot override input device states on cRIO.");
		}
		
		this.acceleration = acceleration;		
	}
	
	public double getSensitivity() {
		return sensitivity;
	}
	
	public void setSensitivity(double sensitivity) {
		this.sensitivity = sensitivity;
		
		if (OperatingSystem.isCRio()) {
			accelerometer.setSensitivity(sensitivity);
		}
	}

	public double getZero() {
		return zero;
	}
	
	public void setZero(double zero) {
		this.zero = zero;
		
		if (OperatingSystem.isCRio()) {
			accelerometer.setZero(zero);
		}
	}
}
