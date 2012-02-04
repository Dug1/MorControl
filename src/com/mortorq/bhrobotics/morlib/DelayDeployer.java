package com.mortorq.bhrobotics.morlib;

import java.util.Vector;

public class DelayDeployer implements Deployer {
	private Handler[] handlers;
	private Deployer child;
	private long period;
	
	public DelayDeployer(Handler handler, Node child, long p) {
		Handler[] handlerArray = {handler};
		handlers = handlerArray;
		period = p;
	}
	
	public DelayDeployer(Handler[] handlerArray, Node child, long p) {
		handlers = handlerArray;
		period = p;
	}
	
	public boolean matches(Event event) {
		boolean match = event.fetch(Event.NAME).equals(this.toString()); 
		if (match) {
		}
		return match;
	}

	public Vector getHandlers() {
		Vector handlerVector = new Vector();
		for(int i = 0; i < handlers.length; i++) {
			handlerVector.addElement(handlers[i]);
		}
		return handlerVector;
	}
	
	public class DelayEmitter {
		
		public DelayEmitter() {
			
		}
	}
}