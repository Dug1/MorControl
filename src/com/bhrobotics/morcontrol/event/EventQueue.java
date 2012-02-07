package com.bhrobotics.morcontrol.event;

import java.util.Enumeration;
import java.util.Vector;

public class EventQueue {
	private Vector entryQueue = new Vector();
	private Vector workQueue = new Vector();
	
	public synchronized Enumeration getEnumeration() {
		Enumeration e = entryQueue.elements();
		while (e.hasMoreElements()) {
			Event event = (Event)e.nextElement();
			workQueue.addElement(event);
		}
		entryQueue.removeAllElements();
		return workQueue.elements();
	}
	
	public synchronized void flush() {
		workQueue.removeAllElements();
	}
	
	public synchronized void clear() {
		flush();
		entryQueue.removeAllElements();
	}
	
	public synchronized void discard(Event e) {
		workQueue.removeElement(e);
	}
	
	public synchronized void addEvent(Event event) {
		entryQueue.addElement(event);
	}
	
	public int getSize() {
		return entryQueue.size() + workQueue.size();				
	}
}
