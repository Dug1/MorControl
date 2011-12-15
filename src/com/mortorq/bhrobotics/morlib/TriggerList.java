package com.mortorq.bhrobotics.morlib;

import java.util.Hashtable;
import java.util.Enumeration;

public class TriggerList implements Tickable{
	private Hashtable relations;
	
	public TriggerList() {
		relations = new Hashtable();
	}
	
	public TriggerList(int intial) {
		relations = new Hashtable(intial);
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
					//Change upon completing reactor
					System.out.println(current.toString()+ " called " + handlers[i].toString());
				}
			}
			if(current.isFinished()) {
				relations.remove(current);
			}
		}
	}
}