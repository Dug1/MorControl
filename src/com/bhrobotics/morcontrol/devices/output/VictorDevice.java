package com.bhrobotics.morcontrol.devices.output;

import edu.wpi.first.wpilibj.Victor;

public class VictorDevice extends MotorDevice {
	private Victor victor;
	
	public VictorDevice(int slot, int channel) {
		super(slot, channel);
	}
	
	protected void initializeMotor(int slot, int channel) {
		victor = new Victor(slot, channel);
	}

	protected void setMotor(double value) {
		victor.set(value);
	}
}
