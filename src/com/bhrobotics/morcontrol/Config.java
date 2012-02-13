package com.bhrobotics.morcontrol;

import com.bhrobotics.morcontrol.devices.DeviceRegistry;
import com.bhrobotics.morcontrol.devices.emitter.AxisEmitter;
import com.bhrobotics.morcontrol.devices.input.JoystickDevice;
import com.bhrobotics.morcontrol.devices.output.VictorDevice;

public class Config {
	public void configure(DeviceRegistry registry) {
		JoystickDevice joystick = new JoystickDevice(1,6,10);
		registry.registerJoystick("main joystick", joystick);
		Ticker.getInstance().registerTickable(new AxisEmitter("drive joystick", joystick, 1));
		Ticker.getInstance().registerTickable(new AxisEmitter("drive joystick", joystick, 2));
		Ticker.getInstance().registerTickable(new AxisEmitter("drive joystick", joystick, 3));
		
		registry.registerMotor("top-left", new VictorDevice(3,1));
		registry.registerMotor("top-right", new VictorDevice(3,2));
		registry.registerMotor("bottom-left", new VictorDevice(3,3));
		registry.registerMotor("bottom-right", new VictorDevice(3,4));
	}
}
