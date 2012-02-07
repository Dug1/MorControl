package com.bhrobotics.morcontrol.event;

public class OrDeployer implements Deployer {
	public static final String ID = "or ID";
	public static final String DEPLOY = "FIORE!";
	private Handler[] handlers;
	private Deployer[] children;
	
	public OrDeployer(Handler handler, Deployer[] deployers) {
		Handler[] handlerArray = {handler};
		handlers = handlerArray;
		children = deployers;
	}
	
	public OrDeployer(Handler[] handlerArray, Deployer[] deployers) {
		handlers = handlerArray;
		children = deployers;
	}
	
	public boolean matches(Event event) {
		for (int i = 0; i < children.length; i++) {
			if (children[i].matches(event)) {
				return true;
			}
		}
		return false;
	}

	public Handler[] getHandlers() {
		return handlers;
	}
}