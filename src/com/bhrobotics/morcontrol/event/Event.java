package com.bhrobotics.morcontrol.event;

import java.util.Enumeration;
import java.util.Hashtable;

public class Event {
	public static final String NAME = "Name";	
	private Hashtable data;
	
	public Event(Hashtable data) {
		this("ALL", data);
	}
	
	public Event(String target, Hashtable data) {
		data.put(NAME, target);
		this.data = data;
	}

	public Object get(String key) {
		return data.get(key);
	}
	
	public char getAsChar(String key) {
		return ((Character) get(key)).charValue();
	}
	
	public byte getAsByte(String key) {
		return ((Byte) get(key)).byteValue();
	}
	
	public short getAsShort(String key) {
		return ((Short) get(key)).shortValue();
	}
	
	public int getAsInt(String key) {
		return ((Integer) get(key)).intValue();
	}
	
	public long getAsLong(String key) {
		return ((Long) get(key)).longValue();
	}
	
	public float getAsFloat(String key) {
		return ((Float) get(key)).floatValue();
	}
	
	public double getAsDouble(String key) {
		return ((Double) get(key)).doubleValue();
	}
	
	public boolean getAsBoolean(String key) {
		return ((Boolean) get(key)).booleanValue();
	}

	public Object fetch(String key) {
		Object obj = get(key);
		
		if (obj == null) {
			throw new UnknownKeyException(key);
		} else {
			return obj;
		}
	}
	
	public char fetchAsChar(String key) {
		return ((Character) fetch(key)).charValue();
	}
	
	public byte fetchAsByte(String key) {
		return ((Byte) fetch(key)).byteValue();
	}
	
	public short fetchAsShort(String key) {
		return ((Short) fetch(key)).shortValue();
	}
	
	public int fetchAsInt(String key) {
		return ((Integer) fetch(key)).intValue();
	}
	
	public long fetchAsLong(String key) {
		return ((Long) fetch(key)).longValue();
	}
	
	public float fetchAsFloat(String key) {
		return ((Float) fetch(key)).floatValue();
	}
	
	public double fetchAsDouble(String key) {
		return ((Double) fetch(key)).doubleValue();
	}
	
	public boolean fetchAsBoolean(String key) {
		return ((Boolean) fetch(key)).booleanValue();
	}
	
	public Hashtable getData() {
		return data;
	}
	
	public Event copy() {
		Hashtable newData = new Hashtable();
		Enumeration e = data.keys();
		while(e.hasMoreElements()) {
			Object key = e.nextElement();
			newData.put(key, data.get(key));
		}
		return new Event(newData);
	}
}