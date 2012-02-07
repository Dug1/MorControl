package com.bhrobotics.morcontrol.event;


public class GenericDeployer implements Deployer {
	private Handler[] handlers;
	private String target;
	private String[] requirements;
	
	public GenericDeployer(String name) {
		this(name, new Handler[0]);
	}
	
	public GenericDeployer(String name, Handler handler) {
		this(name, new String[0], toArray(handler));
	}
	
	public GenericDeployer(String name, Handler[] handlers) {
		this(name, new String[0], handlers);
	}
	
	public GenericDeployer(String name, String[] hasKeys, Handler handler) {
		this(name, hasKeys, toArray(handler));
	}
	
	public GenericDeployer(String name, String[] hasKeys, Handler[] handlers) {
		this.handlers = handlers;
		target = name;
		requirements = hasKeys;
	}

	private static Handler[] toArray(Handler handler) {
		Handler[] handlerArray = {handler};
		return handlerArray;
	}
	
	public boolean matches(Event event) {
		if(event.fetch(Event.NAME).equals(target)) {
			for(int i = 0; i < requirements.length; i++) {
				if(event.get(requirements[i]) == null) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public Handler[] getHandlers() {
		return handlers;
	}
	
	public String[] getRequirement() {
		return requirements;
	}

	public void remove() {
		Reactor.getInstance().removeDeployer(this);
	}
}
