package com.bhrobotics.morcontrol.event;

public class OnceDeployer extends Emitter implements Deployer {
	private Handler[] handlers;
	
	public OnceDeployer(Handler handler) {
		Handler[] handlerArray = {handler};
		handlers = handlerArray;
		start();
	}
	
	public OnceDeployer(Handler[] handlerArray) {
		handlers = handlerArray;
		start();
	}
	
	public boolean matches(Event event) {
		if (event.fetch(Event.NAME).equals(this.toString())) {
			Reactor.getInstance().removeDeployer(this);
			return true;
		}
		return false;
	}

	public Handler[] getHandlers() {
		return handlers;
	}

	public void tick() {	
		Event event = new EventBuilder(this.toString()).build();
		Reactor.getInstance().addEvent(event);
		stop();
	}
}