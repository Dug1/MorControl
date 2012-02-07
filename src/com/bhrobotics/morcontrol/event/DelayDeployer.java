package com.bhrobotics.morcontrol.event;

public class DelayDeployer implements Deployer {
	private Handler[] handlers;
	
	public DelayDeployer(Handler handler) {
		Handler[] handlerArray = {handler};
		handlers = handlerArray;
	}
	
	public DelayDeployer(Handler[] handlerArray) {
		handlers = handlerArray;
	}
	
	public boolean matches(Event event) {
		boolean matches = event.fetch(Event.NAME).equals(this.toString());
		return matches;
	}

	public Handler[] getHandlers() {
		return handlers;
	}
}