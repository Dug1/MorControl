package com.bhrobotics.morcontrol.devices;

public abstract class SingleChannelDevice {
	private int slot;
	private int channel;
	
	public SingleChannelDevice(int slot, int channel) {
		this.slot = slot;
		this.channel = channel;
	}

	public int getSlot() {
		return slot;
	}

	public int getChannel() {
		return channel;
	}
}
