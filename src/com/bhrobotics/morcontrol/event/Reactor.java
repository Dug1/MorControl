package com.bhrobotics.morcontrol.event;

import java.util.Enumeration;

import com.bhrobotics.morcontrol.Tickable;

public class Reactor implements Tickable { 
	//Reactor Singleton
	private static Reactor reactor = null;
	private int poolSize = 5;
	
	//Inner pools
	private EventQueue eventQueue;
	private DeployerRegistry deployerRegistry;
	
	private boolean active = false;
	
	public static Reactor getInstance() {
		if(reactor == null) {
			reactor = new Reactor();
		}
		return reactor;
	}
	
	private Reactor() {
		deployerRegistry = new DeployerRegistry();
		eventQueue = new EventQueue();
	}

	private Runnable createRunnable(Handler h, Event e) {
        class Task implements Runnable {
            Handler handler;
            Event event;

            Task(Handler h, Event e) {
            	handler = h;
                event = e;
            }
                    
            public void run() {
                handler.execute(event);
            }
        }
        return new Task(h, e);
	}
	
	private void schedule(Handler h, Event e, long delay) {
	}

	private void fireEvent(Event event, Handler[] handlers) {
		for(int i = 0; i < handlers.length; i++) {
			schedule(handlers[i], event, 0);
		}
	}
	
	private void checkEvents() {
		Enumeration e = eventQueue.getEnumeration();
		while(e.hasMoreElements()) {
			Event event = (Event)e.nextElement();
			Enumeration f = deployerRegistry.getElements();
			while(f.hasMoreElements()) {
				Deployer deployer = (Deployer)f.nextElement();
				if(deployer.matches(event)) {
					fireEvent(event, deployer.getHandlers());
				}
			}
			eventQueue.discard(event);
		}
	}
	
	public EventQueue getQueue() {
		return eventQueue;
	}
	
	public DeployerRegistry getRegistry() {
		return deployerRegistry;
	}
	
	public void addEvent(Event event) {
		if(active) {
			eventQueue.addEvent(event);
		}
	}
	
	public void addDeployer(Deployer deployer) {
		deployerRegistry.add(deployer);
	}
	
	public void removeDeployer(Deployer deployer) {
		deployerRegistry.remove(deployer);
	}
	
	public void start() {
		active = true;
	}
	
	public void stop() {
		eventQueue.clear();
		active = false;
	}
	
	public void tick() {
		if(active) {
			checkEvents();
		}
	}

	public void newData() {
		//ignore
	}
}