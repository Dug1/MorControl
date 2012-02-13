package com.bhrobotics.morcontrol.devices.output;

import com.bhrobotics.morcontrol.devices.RelayState;
import com.bhrobotics.morcontrol.devices.SingleChannelDevice;
import com.bhrobotics.morcontrol.util.OperatingSystem;

import edu.wpi.first.wpilibj.Relay;

public class RelayDevice extends SingleChannelDevice {
	private RelayState state = RelayState.OFF;
	private Relay relay;
	
	public RelayDevice(int slot, int channel) {
		super(slot, channel);
		
		if (OperatingSystem.isCRio()) {
			relay = new Relay(slot, channel);
			relay.set(state.toRelayValue());
		}
	}
	
	public boolean update(RelayState state) {
		if (state.equals(this.state)) {
			return false;
		}
		
		this.state = state;
		if (OperatingSystem.isCRio()) {
			relay.set(state.toRelayValue());
		}
		
		return true;
	}
	
	public RelayState getState() {
		return state;
	}
}
