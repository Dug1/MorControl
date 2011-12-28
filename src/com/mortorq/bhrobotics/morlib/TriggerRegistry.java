package com.mortorq.bhrobotics.morlib;

import java.util.Hashtable;
import java.util.Enumeration;

public class TriggerRegistry implements Tickable{
	private Hashtable relations;
	
	public TriggerRegistry() {
		relations = new Hashtable();
	}
	
	public TriggerRegistry(int intial) {
		relations = new Hashtable(intial);
	}
	
	public void tryRemove(Trigger t) {
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
	
	public void register(Trigger trigger, Handler[] handlers) {
		relations.put(trigger,handlers);
	}	
	
	public void register(Trigger trigger, Handler handler) {
		Handler[] h = {handler};
		register(trigger,h);
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
				for(int i=0; i<handlers.length; i++) {
					Reactor.Instance().submit(handlers[i], current.getConfig());
				}
			}
		}
	}
}