package com.bhrobotics.morcontrol.event;

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
	private static Interpreter interpreter;
	
	private Interpreter() {
	}
	
	public static Interpreter getInterpreter() {
		if(interpreter == null) {
			interpreter = new Interpreter();
		}
		return interpreter;
	}
	
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
	
	public Context interpret(Context context) throws ParseException {
		StringBuffer buffer = context.getBuffer();
		Node tree = context.getCurrentNode();
		Enumeration e = patterns.elements();
		while(e.hasMoreElements()) {
			Expression pattern = (Expression)e.nextElement();
			if (pattern.matches(buffer.readOne())) {
				Context newContext = pattern.parse(buffer, tree);
				if (context.getBuffer().hasMoreTokens()) {
					return interpret(newContext);
				}
				return newContext;
			}
		}
		throw new ParseException(buffer.readOne());
	}
}