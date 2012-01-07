package com.mortorq.bhrobotics.morlib;

import jregex.Pattern;
import jregex.Matcher;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class OrExpression implements Expression {
	private String matchRegex = "or";
	public final static String TYPE = "Or";
	
	public String getType() {
		return TYPE;
	}
	
	public boolean matches(String token) {
		Pattern pattern = new Pattern(token); 
		return pattern.matcher(matchRegex).matchesPrefix();
	}

	public Context parse(StringBuffer buffer, Node tree) {
		Pattern pattern = new Pattern(matchRegex); 
		Matcher matchMaker = pattern.matcher(buffer.readOne());
		tree.setType(TYPE);
		matchMaker.find();
		buffer.replace((buffer.readOne().substring(matchMaker.end())).trim());
		return new Context(buffer, tree);
	}
	
	public FutureReference makeTrigger(Node tree, Handler[] handlers) {
		long delay = 0;
		TimeUnit unit = TimeUnit.MILLISECONDS;
		if (tree.getData().containsKey(Interpreter.DELAY)) {
			delay = Long.parseLong((String)(tree.getData(Interpreter.DELAY)));
			unit = TimeUnit.valueOf(((String)tree.getData(Interpreter.DELAY_UNIT)).trim());
		}
		class AlertHandler implements Handler {
			int index;
			boolean[] flags;
			AlertHandler(int i, boolean[] f) {
				index = i;
				flags = f;
			}
			
			public Object execute() {
				flags[index] = true;
				return null;
			}
		}
		Node[] children  = tree.getChildren();
		boolean[] flags = new boolean[children.length];
		for(int i =0; i < children.length; i++) {
			Handler[] alerts =  {new AlertHandler(i, flags)};
			Reactor.Instance().getInterpreter().makeTrigger(children[i], alerts);
		}
		return Reactor.Instance().register(new OrTrigger(flags, delay, unit), handlers);
	}
	
	public void clean() {
	}
}