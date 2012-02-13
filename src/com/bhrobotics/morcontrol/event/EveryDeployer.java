package com.bhrobotics.morcontrol.event;

import com.bhrobotics.morcontrol.Ticker;


public class EveryDeployer extends Timer implements Deployer {
	private long startTime = System.currentTimeMillis();
	private long sinceLast = (long) 0.0;
	private long lastTime = startTime;
	private long period;
	private Handler[] handlers;
		
	public EveryDeployer(long p, Handler handlers) {
		Handler[] handlerArray = {handlers};
		this.handlers = handlerArray;
		period = p;
		start();
	}
	
	public EveryDeployer(long p, Handler[] handlers) {
		period = p;
		this.handlers = handlers;
		start();
	}
	
	public boolean matches(Event event) {
		return (event.fetch(Event.NAME).equals(this.toString()));
	}
	
	public Handler[] getHandlers() {
		return handlers;
	}

	public void tick() {
		sinceLast = System.currentTimeMillis() - lastTime;
		if(sinceLast > period) {
			lastTime = System.currentTimeMillis();
			Event event = new EventBuilder(this.toString()).build();
			Reactor.getInstance().addEvent(event);
		}
		
		if(!Reactor.getInstance().getRegistry().hasDeployer(this)) {
			Ticker.getInstance().unregisterTickable(this);
		}
	}
}