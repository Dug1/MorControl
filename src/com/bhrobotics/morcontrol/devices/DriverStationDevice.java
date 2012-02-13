package com.bhrobotics.morcontrol.devices;

import java.util.Vector;

import com.bhrobotics.morcontrol.util.OperatingSystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;

public class DriverStationDevice {
	private DriverStationEnhancedIO ds;
	private Vector digitals = new Vector();
	
	public DriverStationDevice() {
		if (OperatingSystem.isCRio()) {
			ds = DriverStation.getInstance().getEnhancedIO();
		}
		
		for (int i = 0; i < 17; i++) {
			digitals.insertElementAt(DigitalState.OFF, i);
		}
	}
	
	public IOType getType(int channel) {
		checkChannel(channel);
		
		if (OperatingSystem.isCRio()) {
			DriverStationEnhancedIO.tDigitalConfig config;
			try {
				config = ds.getDigitalConfig(channel);
				if (config.equals(DriverStationEnhancedIO.tDigitalConfig.kOutput)) {
					return IOType.OUTPUT;
				} else {
					return IOType.INPUT;
				}
			} catch (EnhancedIOException e) {
				throw new DriverStationException(e);
			}
		} else {
			return IOType.OUTPUT;
		}
	}
	
	public DigitalState getDigital(int channel) {
		checkChannel(channel);
		
		if (OperatingSystem.isCRio()) {
			try {
				if (ds.getDigital(channel)) {
					return DigitalState.ON;
				} else {
					return DigitalState.OFF;
				}
			} catch (EnhancedIOException e) {
				throw new DriverStationException(e);
			}
		} else {
			return (DigitalState) digitals.elementAt(channel);
		}
	}
	
	public void setDigital(int channel, DigitalState state) {
		checkChannel(channel);
		
		if (OperatingSystem.isCRio()) {
			if (getType(channel).equals(IOType.OUTPUT)) {
				try {
					ds.setDigitalOutput(channel, state.toBoolean());
				} catch (EnhancedIOException e) {
					throw new DriverStationException(e);
				}
			} else {
				throw new DriverStationException("Cannot set the state of a digital output.");
			}
		} else {
			digitals.setElementAt(state, channel);
		}
	}
	
	private void checkChannel(int channel) {
		if (channel < 1 || channel > 16) {
			throw new DriverStationException("Channel out of range.");
		}
	}
}