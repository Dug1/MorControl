package com.bhrobotics.morcontrol.devices.output;

import edu.wpi.first.wpilibj.Jaguar;

public class JaguarDevice extends MotorDevice {
	private Jaguar jaguar;
	
	public JaguarDevice(int slot, int channel) {
		super(slot, channel);
	}

	protected void initializeMotor(int slot, int channel) {
		jaguar = new Jaguar(slot, channel);
	}

	protected void setMotor(double value) {
		jaguar.set(value);
	}
}
