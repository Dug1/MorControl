package com.bhrobotics.morcontrol.devices.output;

public interface OutputDeviceObserver {
	public void updateMotor(String name, MotorDevice device);
	public void updateRelay(String name, RelayDevice device);
	public void updateSolenoid(String name, SolenoidDevice device);
}
