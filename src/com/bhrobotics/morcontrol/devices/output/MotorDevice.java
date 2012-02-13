package com.bhrobotics.morcontrol.devices.output;

import com.bhrobotics.morcontrol.devices.AnalogState;
import com.bhrobotics.morcontrol.devices.SingleChannelDevice;
import com.bhrobotics.morcontrol.util.OperatingSystem;

public abstract class MotorDevice extends SingleChannelDevice {
	private AnalogState state = new AnalogState(0.0);

	public MotorDevice(int slot, int channel) {
		super(slot, channel);
		
		if (OperatingSystem.isCRio()) {
			initializeMotor(slot, channel);
			setMotor(state.toDouble());
		}
	}
	
	public boolean update(AnalogState state) {
		if (state.equals(this.state)) {
			return false;
		}
		
		this.state = state;	
		if (OperatingSystem.isCRio()) {
			setMotor(state.toDouble());
		}
		
		return true;
	}
	
	public AnalogState getState() {
		return state;
	}
	
	protected abstract void initializeMotor(int slot, int channel);
	protected abstract void setMotor(double value); 
}
