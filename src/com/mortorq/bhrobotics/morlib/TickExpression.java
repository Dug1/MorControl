package com.mortorq.bhrobotics.morlib;

import jregex.Matcher;
import jregex.Pattern;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class TickExpression implements Expression {
	private String matchRegex = "every tick";
	public final static String TYPE = "Tick"; 
	private Pattern pattern = new Pattern(matchRegex);
	
	public String getType() {
		return TYPE;
	}
	
	public boolean matches(String token) {
		Matcher matchMaker = pattern.matcher(token);
		return (matchMaker.find()) && (matchMaker.start() == 0);
	}

	public Context parse(StringBuffer buffer, Node tree) {
		Matcher matchMaker = pattern.matcher(buffer.readOne());
		matchMaker.find();
		buffer.replace((buffer.readOne().substring(matchMaker.end())).trim());
		Leaf tickLeaf = new Leaf(TYPE);
		tree.addNode(tickLeaf);
		return new Context(buffer, tree);
	}
	
	public FutureReference makeTrigger(Node tree, Handler[] handlers) {
		long delay = 0;
		TimeUnit unit = TimeUnit.MILLISECONDS;
		if (tree.getData().containsKey(Interpreter.DELAY)) {
			delay = Long.parseLong((String)(tree.getData(Interpreter.DELAY)));
			unit = TimeUnit.valueOf((((String)tree.getData(Interpreter.DELAY_UNIT)).toUpperCase()+ "S").trim());
		}
		return Reactor.Instance().register(new TickTrigger(delay, unit), handlers);
	}
	
	public void clean() {
	}
}