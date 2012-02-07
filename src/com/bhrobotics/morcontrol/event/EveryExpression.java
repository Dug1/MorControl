package com.bhrobotics.morcontrol.event;

import jregex.Matcher;

public class EveryExpression extends Expression {
	
	public EveryExpression() {
		super("every ([0-9]+) (millisecond|second)[s]{0,1}");
	}

	public Context parse(StringBuffer buffer, Node tree) {
		Matcher matchMaker = getMatcher(buffer.readOne());
		int end = matchMaker.end();

		String newToken = buffer.readOne().substring(end); 
		buffer.replace(newToken);
		
		Node everNode = new EveryNode();
		everNode.putData(Interpreter.PERIOD, matchMaker.group(0));
		everNode.putData(Interpreter.PERIOD_UNIT, matchMaker.group(1).toUpperCase() + "S");
		tree.addChild(new EveryNode());
		
		return new Context(buffer, tree);
	}
	
	public void clean() {
	}
	
	public boolean matchesNode(Node node) {
		return(node instanceof EveryNode);
	}
	
	public static class EveryNode extends Leaf {
		
		public Deployer register(Handler[] handlers) {
			String timeUnit = getData(Interpreter.PERIOD_UNIT);
			long period = Long.parseLong(getData(Interpreter.PERIOD)); 
			if(timeUnit.equalsIgnoreCase("SECONDS")) {
				period *= 1000;
			}
			
			Deployer deployer = new EveryDeployer(period, handlers);
			Reactor.getInstance().addDeployer(deployer);
			return deployer;
		}
	}
}