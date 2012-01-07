package com.mortorq.bhrobotics.morlib;

import jregex.Matcher;
import jregex.Pattern;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class UntilExpression implements Expression {
	public static final String TYPE = "Until"; 
	private String matchRegex = "until ([0-9]+) (second|millisecond)[s]{0,1}";
	private Pattern pattern = new Pattern(matchRegex);
	
	public String getType() {
		return TYPE;
	}
	
	public boolean matches(String token) {
		Matcher matchMaker = pattern.matcher(token);
		return (matchMaker.find() && (matchMaker.start() == 0));
	}
	
	public Context parse(StringBuffer buffer, Node tree) {
		Node[] chilluns = tree.getChildren();
		Node childNode = chilluns[chilluns.length-1];
		tree.removeNode(childNode);
		Branch untilBranch = new Branch(TYPE);
		untilBranch.addNode(childNode);
		
		Matcher matchMaker = pattern.matcher(buffer.readOne());
		matchMaker.find();
		untilBranch.putData(Interpreter.UNTIL, matchMaker.group(1));
		untilBranch.putData(Interpreter.UNTIL_UNIT, matchMaker.group(2));
		tree.addNode(untilBranch);
		buffer.replace(buffer.readOne().substring(matchMaker.end()).trim());
		
		return new Context(buffer, tree);
	}
	
	public FutureReference makeTrigger(Node tree, Handler[] handlers) {
		long delay = 0;
		TimeUnit unit = TimeUnit.MILLISECONDS;
		if (tree.getData().containsKey(Interpreter.UNTIL)) {
			delay = Long.parseLong((String)(tree.getData(Interpreter.UNTIL)));
			unit = TimeUnit.valueOf(((String)tree.getData(Interpreter.UNTIL_UNIT)).trim());
		}
		class CancelHandler implements Handler {
			FutureReference target; 
			
			CancelHandler(FutureReference ref) {
				target = ref;
			}
			
			public Object execute() {
				target.cancel();
				return null;
			}
		}
		FutureReference reference = Reactor.Instance().getInterpreter().makeTrigger(tree.getChildren()[0], handlers);
		Reactor.Instance().register(new OnceTrigger(delay, unit), new CancelHandler(reference));
		return reference;
	}
	
	public void clean() {
	}
}