package com.bhrobotics.morcontrol.devices.output;

import com.bhrobotics.morcontrol.devices.DigitalState;
import com.bhrobotics.morcontrol.devices.SingleChannelDevice;
import com.bhrobotics.morcontrol.util.OperatingSystem;

import edu.wpi.first.wpilibj.Solenoid;

public class SolenoidDevice extends SingleChannelDevice {
	private DigitalState state = DigitalState.OFF;
	private Solenoid solenoid;
	
	public SolenoidDevice(int slot, int channel) {
		super(slot, channel);
		
		if (OperatingSystem.isCRio()) {
			solenoid = new Solenoid(slot, channel);
			solenoid.set(state.toBoolean());
		}
	}
	
	public boolean update(DigitalState state) {
		if (state.equals(this.state)) {
			return false;
		}
		
		this.state = state;
		if (OperatingSystem.isCRio()) {
			solenoid.set(state.toBoolean());
		}
		
		return true;
	}
	
	public DigitalState getState() {
		return state;
	}
}
