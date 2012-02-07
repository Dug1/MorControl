package com.bhrobotics.morcontrol.event;

import java.util.Enumeration;
import java.util.Vector;

import com.sun.squawk.util.StringTokenizer;

public class StringBuffer {
	private Vector tokens = new Vector();
	
	public StringBuffer(String s) {
		StringTokenizer leftSplitter = new StringTokenizer(s, "(", true);
		while (leftSplitter.hasMoreTokens()) {
			StringTokenizer rightSplitter = new StringTokenizer(leftSplitter.nextToken(), ")",true);
			while (rightSplitter.hasMoreTokens()) {
				String current = rightSplitter.nextToken();
				tokens.addElement(current.trim());
			}
		}
	}
	
	public String readOne() {
		return (String)tokens.firstElement();
	}
	
	public Vector getTokens() {
		return tokens;
	}
	
	public void replace(String s) {
		this.remove();
		if (!s.equals("")) {
			tokens.insertElementAt(s,0);
		}
	}
	
	public boolean hasMoreTokens() {
		return (tokens.size() > 0);
	}
	
	public int size() {
		return tokens.size();
	}
	
	public void remove() {
		tokens.removeElementAt(0);
	}
	
	public String toString() {
		String returnString = "";
		Enumeration e = tokens.elements();
		while (e.hasMoreElements()) {
			returnString = returnString + (String)e.nextElement();
		}
		return returnString;
	}
}