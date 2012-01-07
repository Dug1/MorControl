package com.mortorq.bhrobotics.morlib;

import jregex.Matcher;
import jregex.Pattern;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class EveryExpression implements Expression{
	private String matchRegex = "every ([0-9]+) (millisecond|second)[s]{0,1}";
	public final static String TYPE = "Every";
	Pattern pattern = new Pattern(matchRegex);
	
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
		Leaf everyLeaf = new Leaf(TYPE);
		everyLeaf.putData(Interpreter.PERIOD, matchMaker.group(1).trim());
		everyLeaf.putData(Interpreter.PERIOD_UNIT, matchMaker.group(2).trim());
		tree.addNode(everyLeaf);
		return new Context(buffer, tree);
	}
	
	public FutureReference makeTrigger(Node tree, Handler[] handlers) {
		long delay = 0;
		TimeUnit unit = TimeUnit.MILLISECONDS;
		if (tree.getData().containsKey(Interpreter.DELAY)) {
			delay = Long.parseLong((String)(tree.getData(Interpreter.DELAY)));
			unit = TimeUnit.valueOf(((String)tree.getData(Interpreter.DELAY_UNIT)).trim());
		}
		long period = 1;
		TimeUnit periodUnit = TimeUnit.MILLISECONDS;
		if (tree.getData().containsKey(Interpreter.DELAY)) {
			period = Long.parseLong((String)(tree.getData(Interpreter.PERIOD)));
			periodUnit = TimeUnit.valueOf(((String)tree.getData(Interpreter.PERIOD_UNIT)).trim());
		}
		return Reactor.Instance().register(new EveryTrigger(delay, unit, period, periodUnit), handlers);
	}
	
	public void clean() {
	}
}