package com.bhrobotics.morcontrol.event;

import com.bhrobotics.morcontrol.Ticker;

public class TickDeployer extends Timer implements Deployer {
	private Handler[] handlers;
	
	public TickDeployer(Handler handler) {
		Handler[] handlerArray = {handler};
		handlers = handlerArray;
		start();
	}
	
	public TickDeployer(Handler[] handlerArray) {
		handlers = handlerArray;
		start();
	}
	
	public boolean matches(Event event) {
		return (event.fetch(Event.NAME).equals(this.toString()));
	}
	
	public void remove() {
		Ticker.getInstance().unregisterTickable(this);
	}
	
	public Handler[] getHandlers() {
		return handlers;
	}

	public void tick() {
		if(!Reactor.getInstance().getRegistry().hasDeployer(this)) {
			stop();
		}
		Event event = new EventBuilder(this.toString()).build();
		Reactor.getInstance().addEvent(event);
	}
}