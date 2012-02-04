package com.mortorq.bhrobotics.morlib;

import java.util.Vector;

public class UntilDeployer implements Deployer {
	private long end;
	private Handler[] handlers;
	
	public UntilDeployer(long after, Handler[] handlers) {
		end = after + System.currentTimeMillis();
		this.handlers = handlers;
	}

	public boolean matches(Event event) {
		return System.currentTimeMillis() > end;
	}

	public Vector getHandlers() {
		Vector handlerVector = new Vector();
		for(int i = 0; i < handlers.length; i++) {
			handlerVector.addElement(handlers[i]);
		}
		return handlerVector;
	}

	public void remove() {
		
	} 
}
