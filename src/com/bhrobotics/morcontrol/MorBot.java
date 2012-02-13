package com.bhrobotics.morcontrol;

import com.bhrobotics.morcontrol.devices.AnalogState;
import com.bhrobotics.morcontrol.devices.DeviceRegistry;
import com.bhrobotics.morcontrol.devices.emitter.AxisEmitter;
import com.bhrobotics.morcontrol.devices.output.MotorDevice;
import com.bhrobotics.morcontrol.event.Event;
import com.bhrobotics.morcontrol.event.Handler;
import com.bhrobotics.morcontrol.event.Reactor;

public class MorBot extends Robot {
	private ControlMode mode;
	DeviceRegistry registry;
	
	public void startOperatorControl() {
		Ticker.getInstance().registerTickable(Reactor.getInstance());
		Ticker.getInstance().start();
		registry = new DeviceRegistry();
		
		Config config = new Config();
		config.configure(registry);
		
		Handler[] handlers = {new DriveHandler()};
		Condition[] conditions = {new Condition("Event drive joystick called", handlers)}; 
		
		mode = new ControlMode(conditions);
		mode.setUp();
	}
	
	public void stopOperatorControl() {
		mode.tearDown();
	}
	
	public class DriveHandler implements Handler {
		private MotorDevice tl = registry.getMotor("top-left");
		private MotorDevice tr = registry.getMotor("top-right");
		private MotorDevice bl = registry.getMotor("bottom-left");
		private MotorDevice br = registry.getMotor("bottom-right");
		
		private double xValue = 0.0;
		private double yValue = 0.0;
		private double tValue = 1.0;
		
		public void execute(Event event) {
			switch(event.fetchAsInt(AxisEmitter.AXIS_INDEX)) {
				case 1:
					xValue = event.getAsDouble(AxisEmitter.NEW_AXIS_VALUE);
					update();
					break;
				case 2:
					yValue = event.getAsDouble(AxisEmitter.NEW_AXIS_VALUE);
					update();
					break;					
				case 3:
					tValue = (event.fetchAsDouble(AxisEmitter.NEW_AXIS_VALUE) + 1)*.4 + .2;
					update();
					break;
			}
		}
		
		public void update() {
			tl.update(new AnalogState((yValue - xValue)*tValue));
			tr.update(new AnalogState((-yValue - xValue)*tValue));
			bl.update(new AnalogState((yValue - xValue)*tValue));
			br.update(new AnalogState((-yValue - xValue)*tValue));
		}
	}
}