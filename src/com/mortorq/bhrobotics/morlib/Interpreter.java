package com.mortorq.bhrobotics.morlib;

import java.util.Enumeration;
import java.util.Vector;

public class Interpreter { 
	private Vector patterns = new Vector();
	public final static String PERIOD = "Period";
	public final static String PERIOD_UNIT = "Period Unit";
	public final static String DELAY = "Delay";
	public final static String DELAY_UNIT = "Delay Unit";	
	public final static String UNTIL = "Delay";
	public final static String UNTIL_UNIT = "Delay Unit";
	public final static String BUTTON_NUMBER = "Button Number";
	
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
	
	public FutureReference makeTrigger(Node tree, Handler[] handlers) {
		Enumeration e = patterns.elements();
		while(e.hasMoreElements()) {
			Expression pattern = (Expression)e.nextElement();
			if (pattern.getType().equals(tree.getType())) {
				return (pattern).makeTrigger(tree, handlers);
			}
		}
		//Should throw Exception
		return null;
	}
}