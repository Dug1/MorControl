package com.bhrobotics.morcontrol.event;

import java.util.Enumeration;
import java.util.Vector;

import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import edu.emory.mathcs.backport.java.util.concurrent.ScheduledExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.ScheduledFuture;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class Reactor implements Tickable { 
	//Reactor Singleton
	private static Reactor reactor = null;
	private int poolSize = 5;
	
	//Inner pools
	private ScheduledExecutorService threadPool;
	private Vector tickables;
	private EventQueue eventQueue;
	private DeployerRegistry deployerRegistry;
	private Interpreter interpreter = new Interpreter();
	
	public static Reactor getInstance() {
		if(reactor == null) {
			reactor = new Reactor();
		}
		return reactor;
	}
	
	private Reactor() {
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
	
	private ScheduledFuture schedule(Handler h, Event e, long delay) {
		return threadPool.schedule(createRunnable(h,e),delay,TimeUnit.MILLISECONDS);
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
	
	public Interpreter getInterpreter() {
		return interpreter;
	}
	
	public EventQueue getQueue() {
		return eventQueue;
	}
	
	public DeployerRegistry getRegistry() {
		return deployerRegistry;
	}
	
	public Vector getTickable() {
		return tickables;
	}
	
	public void addPattern(Expression e) {
		interpreter.addPattern(e);
	}
	
	public void removePattern(Expression e) {
		interpreter.removePattern(e);
	}
	
	public void addEvent(Event event) {
		eventQueue.addEvent(event);
	}
	
	public void addDeployer(Deployer deployer) {
		deployerRegistry.add(deployer);
	}
	
	public void removeDeployer(Deployer deployer) {
		deployerRegistry.remove(deployer);
	}
	
	public void addTickable(Tickable tickable) {
		tickables.addElement(tickable);
	}
	
	public void removeTickable(Tickable tickable) {
		tickables.removeElement(tickable);
	}
	
	public void start() {
		threadPool = Executors.newScheduledThreadPool(poolSize);
		tickables = new Vector();
		eventQueue = new EventQueue();
		deployerRegistry = new DeployerRegistry();
	}
	
	public void stop() {
		threadPool.shutdown();
		eventQueue.clear();
		tickables.removeAllElements();
	}
	
	public void tick() {
		Enumeration e = tickables.elements();
		while(e.hasMoreElements()) {
			Tickable tickable = (Tickable)e.nextElement();
			tickable.tick();
		}
		checkEvents();
	}
}