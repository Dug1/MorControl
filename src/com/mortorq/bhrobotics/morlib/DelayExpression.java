package com.mortorq.bhrobotics.morlib;

import jregex.Matcher;

public class DelayExpression extends Expression {
	
	public DelayExpression() {
		super("with delay of ([0-9]+) (second|millisecond)[s]{0,1}");
	}
	
	public Context parse(StringBuffer buffer, Node tree) {
		Node[] children = tree.getChildren();
		Matcher matchMaker = getMatcher(buffer.readOne());
		
		buffer.replace(buffer.readOne().substring(matchMaker.end()).trim());
		Node delayNode = new DelayNode();
		Node delayedNode = children[children.length-1];
		delayNode.putData(Interpreter.DELAY, matchMaker.group(1));
		delayNode.putData(Interpreter.DELAY_UNIT, matchMaker.group(1).toUpperCase() + "S");
		delayNode.addChild(delayedNode);
		tree.removeChild(delayedNode);
		tree.addChild(delayNode);
		
		return new Context(buffer, tree);
	}
	
	public void clean() {
	}

	public boolean matchesNode(Node node) {
		return node instanceof DelayNode;
	}

	public static class DelayNode extends Branch {

		public Deployer register(Handler[] handlers) {
			Node childNode = getChildren()[0];
			String timeUnit = getData(Interpreter.DELAY_UNIT);
			long period = Long.parseLong(getData(Interpreter.DELAY)); 
			if(timeUnit.equalsIgnoreCase("SECONDS")) {
				period *= 1000;
			}
			
			Deployer delayFilter = new DelayDeployer(handlers, childNode, period);
			Reactor.getInstance().addDeployer(delayFilter);

			return delayFilter;
		}	
	}
}