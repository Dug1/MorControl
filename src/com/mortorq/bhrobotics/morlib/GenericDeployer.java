package com.mortorq.bhrobotics.morlib;

import java.util.Vector;

public class GenericDeployer implements Deployer {
	private Vector handlers;
	private String target;
	private String[] requirements;
	
	public GenericDeployer(String name) {
		this(name, new Handler[0]);
	}
	
	public GenericDeployer(String name, Handler handler) {
		this(name, new String[0], toVector(handler));
	}
	
	public GenericDeployer(String name, Handler[] handlers) {
		this(name, new String[0], handlers);
	}
	
	public GenericDeployer(String name, String[] hasKeys) {
		this(name, hasKeys, new Vector());
	}
	
	public GenericDeployer(String name, String[] hasKeys, Handler handler) {
		this(name, hasKeys, toVector(handler));
	}
	
	public GenericDeployer(String name, String[] hasKeys, Handler[] handlers) {
		this(name, hasKeys, toVector(handlers));
	}
	
	public GenericDeployer(String name, String[] hasKeys, Vector handlers) {
		this.handlers = handlers;
		target = name;
		requirements = hasKeys;
	
	}

	private static Vector toVector(Handler handler) {
		Handler[] handlerArray = {handler};
		return toVector(handlerArray);
	}
	
	private static Vector toVector(Handler[] handlerArray) {
		Vector handlerVector = new Vector();
		for(int i = 0; i < handlerArray.length; i++) {
			handlerVector.addElement(handlerArray[i]);
		}
		return handlerVector;
	}
	
	public void addHandler(Handler handler) {
		handlers.addElement(handler);
	}
	
	public void removeHandler(Handler handler) {
		handlers.removeElement(handler);
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

	public Vector getHandlers() {
		return handlers;
	}
	
	public String[] getRequirement() {
		return requirements;
	}

	public void remove() {
		Reactor.getInstance().removeDeployer(this);
	}
}
