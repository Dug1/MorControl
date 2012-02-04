package com.mortorq.bhrobotics.morlib;

import java.util.Enumeration;
import java.util.Vector;

public class EventQueue {
	private Vector entryQueue = new Vector();
	private Vector workQueue = new Vector();
	
	public Enumeration getEnumeration() {
		Enumeration e = entryQueue.elements();
		while (e.hasMoreElements()) {
			workQueue.addElement(e.nextElement());
		}
		entryQueue.removeAllElements();
		return workQueue.elements();
	}
	
	public void flush() {
		workQueue.removeAllElements();
	}
	
	public void clear() {
		flush();
		entryQueue.removeAllElements();
	}
	
	public void addEvent(Event event) {
		entryQueue.addElement(event);
	}
	
	public int getSize() {
		return entryQueue.size() + workQueue.size();				
	}
}
