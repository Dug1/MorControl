package com.mortorq.bhrobotics.morlib;

import java.util.Vector;
import java.util.Enumeration;

public abstract class HardwareBase implements Tickable {
	Vector matchers = new Vector();
	
	public boolean register(String s, Handler h) {
		Enumeration e = matchers.elements();
		boolean registered = false;
		while(e.hasMoreElements()) {
			Pattern pattern = (Pattern)e.nextElement();
			if (pattern.matches()) {
				Reactor.Instance().submit(h,pattern.parse());
				return true;
			}
		}
		return false;
	}
	
	public void register(Pattern p) {
		matchers.addElement(p);
	}
}