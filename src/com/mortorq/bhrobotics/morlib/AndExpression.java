package com.mortorq.bhrobotics.morlib;

import jregex.Pattern;
import jregex.Matcher;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class AndExpression implements Expression {
	private String matchRegex = "and";
	public final static String TYPE = "And";
	
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
	
	public void clean() {
	}
	
	public FutureReference makeTrigger(Node tree, Handler[] handlers) throws TriggerException {
		long delay = 0;
		TimeUnit unit = TimeUnit.MILLISECONDS;
		if (tree.getData().containsKey(Interpreter.DELAY)) {
			delay = Long.parseLong((String)(tree.getData(Interpreter.DELAY)));
			unit = TimeUnit.valueOf((((String)tree.getData(Interpreter.DELAY_UNIT)).toUpperCase()+ "S").trim());
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
		return Reactor.Instance().register(new AndTrigger(flags, delay, unit), handlers);
	}
}