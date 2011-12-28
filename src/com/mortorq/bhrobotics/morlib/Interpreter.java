package com.mortorq.bhrobotics.morlib;

import java.util.Enumeration;
import java.util.Vector;

public class Interpreter { 
	private Vector patterns = new Vector();
	
	public int size() {
		return patterns.size();
	}
	
	public void removePattern(Expression e) {
		patterns.removeElement(e);
	}
	public void addPattern(Expression e) {
		patterns.addElement(e);
	}
	
	public void cleanPatterns() {
		Enumeration e = patterns.elements();
		while(e.hasMoreElements()) {
			((Expression)e.nextElement()).clean();
		}
	}
	
	public void clear() {
		patterns.removeAllElements();
	}
	
	public Context interpret(Context context) {
		StringBuffer buffer = context.buffer;
		Node tree = context.currentNode;
		
		Enumeration e = patterns.elements();
		while(e.hasMoreElements()) {
			Expression pattern = (Expression)e.nextElement();
			if (pattern.matches(buffer.readOne())) {
				context = pattern.parse(buffer, tree);
				break;
			}
		}
		
		if (context.buffer.hasMoreTokens()) {
			return interpret(context);
		} else {
			return context;
		}
	}
}