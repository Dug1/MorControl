package com.mortorq.bhrobotics.morlib;

import java.util.Vector;

public class OnceDeployer implements Deployer {
	private Handler[] handlers;
	private String matchTo;
	private OnceEmitter child;
	
	public OnceDeployer(Handler handler) {
		Handler[] handlerArray = {handler};
		addEmitter();
		handlers = handlerArray;
	}
	
	public OnceDeployer(Handler[] handlerArray) {
		addEmitter();
		handlers = handlerArray;
	}
	
	private void addEmitter() {
		OnceEmitter emitter  = new OnceEmitter();
		emitter.start();
		matchTo = emitter.toString();
	}
	
	public boolean matches(Event event) {
		if (event.fetch(Event.NAME).equals(matchTo)) {
			return true;
		}
		return false;
	}

	public Vector getHandlers() {
		Vector handlerVector = new Vector();
		for(int i = 0; i < handlers.length; i++) {
			handlerVector.addElement(handlers[i]);
		}
		return handlerVector;
	}
	
	public class OnceEmitter extends Emitter {

		public void tick() {	
			Event event = new EventBuilder(this.toString()).build();
			Reactor.getInstance().addEvent(event);
			stop();
		}
	}
}