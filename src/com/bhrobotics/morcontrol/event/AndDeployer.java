package com.bhrobotics.morcontrol.event;

public class AndDeployer extends Emitter implements Deployer {
	private Handler[] handlers;
	private boolean[] flags;
	private Deployer[] children;
	
	public AndDeployer(Handler handler, Deployer[] deployers) {
		Handler[] handlerArray = {handler};
		handlers = handlerArray;
		children = deployers;
		flags = new boolean[deployers.length];
		start();
	}
	
	public AndDeployer(Handler[] handlerArray, Deployer[] deployers) {
		handlers = handlerArray;
		flags = new boolean[deployers.length];
		children = deployers;
		start();
	}
	
	public boolean matches(Event event) {		
		for (int i = 0; i < children.length; i++) {
			if (children[i].matches(event)) {
				flags[i] = true;
			}
		}
		boolean allTrue = true;
		for(int i = 0; i < flags.length; i++) {
			if (!flags[i]) {
				allTrue = false;
			}
		}
		
		if(allTrue) {
			return true;
		}
		
		return false;
	}

	public Handler[] getHandlers() {
		return handlers;
	}

	public void tick() {
		for(int i = 0; i < flags.length; i++) {
			flags[i] = false;
		}
	}
}