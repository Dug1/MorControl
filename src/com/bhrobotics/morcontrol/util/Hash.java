package com.bhrobotics.morcontrol.util;

import java.util.Enumeration;
import java.util.Hashtable;


public class Hash {
	private Hashtable hashtable;
	
	public Hash() {
		hashtable = new Hashtable(); 
	}
	
	public Hash(Hashtable hashtable) {
		this.hashtable = hashtable;
	}
	
	public Hashtable toHashtable() {
		return hashtable;
	}
	
	public boolean isEmpty() {
		return hashtable.isEmpty();
	}
	
	public boolean contains(Object key) {
		return hashtable.contains(key);
	}
	
	public boolean containsKey(Object key) {
		return hashtable.containsKey(key);
	}
	
	public Enumeration keys() {
		return hashtable.keys();
	}
	
	public Enumeration elements() {
		return hashtable.elements();
	}
	
	public Object get(Object key) {
		return hashtable.get(key);
	}
	
	public String getAsString(Object key) {
		return (String) hashtable.get(key);
	}
	
	public char getAsChar(Object key) {
		return ((Character) get(key)).charValue();
	}
	
	public byte getAsByte(Object key) {
		return ((Byte) get(key)).byteValue();
	}
	
	public short getAsShort(Object key) {
		return ((Short) get(key)).shortValue();
	}

	public int getAsInt(Object key) {
		return ((Integer) get(key)).intValue();
	}
	
	public long getAsLong(Object key) {
		return ((Long) get(key)).longValue();
	}
	
	public float getAsFloat(Object key) {
		return ((Float) get(key)).floatValue();
	}
	
	public double getAsDouble(Object key) {
		return ((Double) get(key)).doubleValue();
	}
	
	public boolean getAsBoolean(Object key) {
		return ((Boolean) get(key)).booleanValue();
	}
	
	public Object fetch(Object key) {
		if (containsKey(key)) {
			return get(key);
		} else {
			throw new UnknownKeyException("Unknown key " + key + ".");
		}
	}
	
	public Object fetch(Object key, Object defaultValue) {
		if (containsKey(key)) {
			return get(key);
		} else {
			return defaultValue;
		}
	}
	
	public String fetchAsString(Object key) {
		if (containsKey(key)) {
			return (String) get(key);
		} else {
			throw new UnknownKeyException("Unknown key " + key + ".");
		}
	}
	
	public String fetchAsString(Object key, String defaultValue) {
		if (containsKey(key)) {
			return (String) get(key);
		} else {
			return defaultValue;
		}
	}
	
	public char fetchAsChar(Object key) {
		return ((Character) fetch(key)).charValue();
	}
	
	public char fetchAsChar(Object key, char defaultValue) {
		if (containsKey(key)) {
			return getAsChar(key);
		} else {
			return defaultValue;
		}
	}
	
	public byte fetchAsByte(Object key) {
		return ((Byte) fetch(key)).byteValue();
	}
	
	public byte fetchAsByte(Object key, byte defaultValue) {
		if (containsKey(key)) {
			return getAsByte(key);
		} else {
			return defaultValue;
		}
	}
	
	public short fetchAsShort(Object key) {
		return ((Short) fetch(key)).shortValue();
	}
	
	public short fetchAsShort(Object key, short defaultValue) {
		if (containsKey(key)) {
			return getAsShort(key);
		} else {
			return defaultValue;
		}
	}

	public int fetchAsInt(Object key) {
		return ((Integer) fetch(key)).intValue();
	}
	
	public int fetchAsInt(Object key, int defaultValue) {
		if (containsKey(key)) {
			return getAsInt(key);
		} else {
			return defaultValue;
		}
	}
	
	public long fetchAsLong(Object key) {
		return ((Long) fetch(key)).longValue();
	}
	
	public long fetchAsLong(Object key, long defaultValue) {
		if (containsKey(key)) {
			return getAsLong(key);
		} else {
			return defaultValue;
		}
	}
	
	public float fetchAsFloat(Object key) {
		return ((Float) fetch(key)).floatValue();
	}
	
	public float fetchAsFloat(Object key, float defaultValue) {
		if (containsKey(key)) {
			return getAsFloat(key);
		} else {
			return defaultValue;
		}
	}
	
	public double fetchAsDouble(Object key) {
		return ((Double) fetch(key)).doubleValue();
	}
	
	public double fetchAsDouble(Object key, double defaultValue) {
		if (containsKey(key)) {
			return getAsDouble(key);
		} else {
			return defaultValue;
		}
	}
	
	public boolean fetchAsBoolean(Object key) {
		return ((Boolean) fetch(key)).booleanValue();
	}
	
	public boolean fetchAsBoolean(Object key, boolean defaultValue) {
		if (containsKey(key)) {
			return getAsBoolean(key);
		} else {
			return defaultValue;
		}
	}
	
	public void put(Object key, Object value) {
		hashtable.put(key, value);
	}
	
	public void put(Object key, char value) {
		hashtable.put(key, new Character(value));
	}
	
	public void put(Object key, byte value) {
		hashtable.put(key, new Byte(value));
	}
	
	public void put(Object key, short value) {
		hashtable.put(key, new Short(value));
	}
	
	public void put(Object key, int value) {
		hashtable.put(key, new Integer(value));
	}
	
	public void put(Object key, long value) {
		hashtable.put(key, new Long(value));
	}
	
	public void put(Object key, float value) {
		hashtable.put(key, new Float(value));
	}
	
	public void put(Object key, double value) {
		hashtable.put(key, new Double(value));
	}
	
	public void put(Object key, boolean value) {
		hashtable.put(key, new Boolean(value));
	}
	
	public void remove(Object key) {
		hashtable.remove(key);
	}
	
	public int size() {
		return hashtable.size();
	}
	
	public void clear() {
		hashtable.clear();
	}
	
	public String toString() {
		return hashtable.toString();
	}
	
	public boolean equals(Object other) {
		return hashtable.equals(other);
	}
}
