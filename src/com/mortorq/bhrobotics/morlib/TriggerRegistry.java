package com.mortorq.bhrobotics.morlib;

import java.util.Hashtable;
import java.util.Enumeration;
import edu.emory.mathcs.backport.java.util.concurrent.ScheduledFuture;

public class TriggerRegistry implements Tickable{
	private Hashtable relations;
	private Hashtable handlerRegistry = new Hashtable();
	
	public TriggerRegistry() {
		relations = new Hashtable();
	}
	
	public TriggerRegistry(int intial) {
		relations = new Hashtable(intial);
	}
	
	public void tryRemove(Trigger t) {
		handlerRegistry.remove(relations.get(t));
		relations.remove(t);
	}
	
	public void clear() {
		relations.clear();
	}
	
	public int getSize() {
		return relations.size();
	}
	
	public Enumeration getTriggers() {
		return relations.keys();
	}
	
	public Enumeration getHandlers() {
		return relations.elements();
	}
	
	public FutureReference register(Trigger trigger, Handler[] handlers) {
		FutureReference tag = new FutureReference();
		handlerRegistry.put(handlers, tag);
		relations.put(trigger,handlers);
		return tag;
	}	
	
	public FutureReference register(Trigger trigger, Handler handler) {
		Handler[] h = {handler};
		return register(trigger,h);
	}
	
	public void start() {
	}
	
	public void stop() {
	}
	
	public void tick() {
		Enumeration e = getTriggers();
		while(e.hasMoreElements()) {
			Trigger current = (Trigger)e.nextElement();
			if(current.isTriggered()) {
				Handler[] handlers = (Handler[])relations.get(current);
				ScheduledFuture[] futures = new ScheduledFuture[handlers.length];
				for(int i=0; i<handlers.length; i++) {
					futures[i] = Reactor.Instance().submit(handlers[i], current.getConfig());
				}
				((FutureReference)handlerRegistry.get(handlers)).setFutures(futures);
			}
		}
	}
}