package com.bhrobotics.morcontrol.event;

import java.util.Hashtable;

public class EventBuilder {
	private Hashtable data = new Hashtable();
	private String target;
	
	public EventBuilder(String target) {
		this.target = target;
	}
	
	public EventBuilder with(String key, Object obj) {
		data.put(key, obj);
		return this;
	}
	
	public EventBuilder with(String key, char c) {
		data.put(key, new Character(c));
		return this;
	}
	
	public EventBuilder with(String key, byte b) {
		data.put(key, new Byte(b));
		return this;
	}
	
	public EventBuilder with(String key, short s) {
		data.put(key, new Short(s));
		return this;
	}
	
	public EventBuilder with(String key, int i) {
		data.put(key, new Integer(i));
		return this;
	}
	
	public EventBuilder with(String key, long l) {
		data.put(key, new Long(l));
		return this;
	}
	
	public EventBuilder with(String key, float f) {
		data.put(key, new Float(f));
		return this;
	}

	public EventBuilder with(String key, double d) {
		data.put(key, new Double(d));
		return this;
	}

	public EventBuilder with(String key, boolean b) {
		data.put(key, new Boolean(b));
		return this;
	}
	
	public Event build() {
		return new Event(target ,data);
	}
}