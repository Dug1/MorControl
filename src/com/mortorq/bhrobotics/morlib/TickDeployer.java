package com.mortorq.bhrobotics.morlib;

import java.util.Vector;

public class TickDeployer implements Deployer {
	private Handler[] handlers;
	private TickEmitter trigger;
	
	public TickDeployer(Handler handler) {
		Handler[] handlerArray = {handler};
		handlers = handlerArray;
		addEmitter();
	}
	
	public TickDeployer(Handler[] handlerArray) {
		handlers = handlerArray;
		addEmitter();
	}
	
	private void addEmitter() {
		trigger = new TickEmitter();
		trigger.start();
	}
	
	public boolean matches(Event event) {
		return (event.fetch(Event.NAME).equals(trigger.toString()));
	}
	
	public void remove() {
		Reactor.getInstance().removeTickable(trigger);
	}
	
	public Vector getHandlers() {
		Vector handlerVector = new Vector();
		for(int i = 0; i < handlers.length; i++) {
			handlerVector.addElement(handlers[i]);
		}
		return handlerVector;
	}
	
	public class TickEmitter extends Emitter {

		public void tick() {
			Event event = new EventBuilder(this.toString()).build();
			Reactor.getInstance().addEvent(event);
		}
	}
}