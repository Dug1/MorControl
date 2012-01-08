package com.mortorq.bhrobotics.morlib;

import jregex.Matcher;
import jregex.Pattern;

public class DelayExpression implements Expression {
	private String matchRegex = "with delay of ([0-9]+) (second|millisecond)[s]{0,1}";
	public final static String TYPE = "Delay";
	private Pattern pattern = new Pattern(matchRegex);
	
	public String getType() {
		return TYPE;
	}	
	
	public boolean matches(String token) {
		Matcher matchMaker = pattern.matcher(token);
		return (matchMaker.find() && (matchMaker.start() == 0));
 	}
	
	public Context parse(StringBuffer buffer, Node tree) {
		Node[] children = tree.getChildren();
		Matcher matchMaker = pattern.matcher(buffer.readOne());
		
		matchMaker.find();
		buffer.replace(buffer.readOne().substring(matchMaker.end()).trim());
		Node delayNode = children[children.length-1];
		delayNode.putData(Interpreter.DELAY, matchMaker.group(1));
		delayNode.putData(Interpreter.DELAY_UNIT, matchMaker.group(2));
		
		return new Context(buffer, tree);
	}
	
	public void clean() {
	}
	
	public FutureReference makeTrigger(Node tree, Handler[] handlers) throws TriggerException {
		throw new TriggerException(tree);
	}
}