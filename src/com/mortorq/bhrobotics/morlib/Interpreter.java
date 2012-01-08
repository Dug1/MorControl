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
	
	public FutureReference compile(String arg, Handler[] handlers) throws ParseException, TriggerException {
		cleanPatterns();
		Context context = Reactor.Instance().getInterpreter().interpret(new Context(new StringBuffer(arg), new Branch(ContainerExpression.TYPE)));
		return Reactor.Instance().getInterpreter().makeTrigger(context.currentNode, handlers);
	}
	
	public void clear() {
		patterns.removeAllElements();
	}
	
	protected Context interpret(Context context) throws ParseException {
		StringBuffer buffer = context.buffer;
		Node tree = context.currentNode;
		Enumeration e = patterns.elements();
		while(e.hasMoreElements()) {
			Expression pattern = (Expression)e.nextElement();
			if (pattern.matches(buffer.readOne())) {
				Context newContext = pattern.parse(buffer, tree);
				if (context.buffer.hasMoreTokens()) {
					return interpret(newContext);
				}
				return newContext;
			}
		}
		throw new ParseException(buffer.readOne());
	}
	
	protected FutureReference makeTrigger(Node tree, Handler[] handlers) throws TriggerException {
		Enumeration e = patterns.elements();
		while(e.hasMoreElements()) {
			Expression pattern = (Expression)e.nextElement();
			if (pattern.getType().equals(tree.getType())) {
				System.out.println("Making " + pattern.getType() + "Trigger");
				return (pattern).makeTrigger(tree, handlers);
			}
		}
		throw new TriggerException(tree); 
	}
}