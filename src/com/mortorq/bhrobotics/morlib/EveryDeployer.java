package com.mortorq.bhrobotics.morlib;

import java.util.Vector;

public class EveryDeployer implements Deployer {
	private long startTime = System.currentTimeMillis();
	private long sinceLast = (long) 0.0;
	private long lastTime = startTime;
	private long period;
	private Handler[] handlers;
	private EveryEmitter child;
		
	public EveryDeployer(long p, Handler handlers) {
		Handler[] handlerArray = {handlers};
		this.handlers = handlerArray;
		period = p;
		addEmitter();
	}
	
	public EveryDeployer(long p, Handler[] handlers) {
		period = p;
		this.handlers = handlers;
		addEmitter();
	}
	
	private void addEmitter() {
		child = new EveryEmitter();
		child.start();
	}
	
	public boolean matches(Event event) {
		return (event.fetch(Event.NAME).equals(child.toString()));
	}

	public void remove() {
		Reactor.getInstance().removeTickable(child);
	}
	
	public Vector getHandlers() {
		Vector handlerVector = new Vector();
		for(int i = 0; i < handlers.length; i++) {
			handlerVector.addElement(handlers[i]);
		}
		return handlerVector;
	}

	public class EveryEmitter extends Emitter {
		public void tick() {
			sinceLast = System.currentTimeMillis() - lastTime;
			if(sinceLast > period) {
				lastTime = System.currentTimeMillis();
				Event event = new EventBuilder(this.toString()).build();
				Reactor.getInstance().addEvent(event);
			}
		}
	}
}