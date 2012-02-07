package com.bhrobotics.morcontrol.event;


public interface Deployer {
	public boolean matches(Event event);
	public Handler[] getHandlers();
}
