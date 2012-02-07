package com.bhrobotics.morcontrol.event;

import jregex.Matcher;

public class UntilExpression extends Expression {
	public UntilExpression() {
		super("until ([0-9]+) (second|millisecond)[s]{0,1}");
	}
	
	public Context parse(StringBuffer buffer, Node tree) {
		Node[] chilluns = tree.getChildren();
		Node childNode = chilluns[chilluns.length-1];
		tree.removeChild(childNode);
		Node node = new UntilNode();
		node.addChild(childNode);
		
		Matcher matchMaker = getMatcher(buffer.readOne());
		node.putData(Interpreter.UNTIL, matchMaker.group(1));
		node.putData(Interpreter.UNTIL_UNIT, matchMaker.group(2));
		tree.addChild(node);
		buffer.replace(buffer.readOne().substring(matchMaker.end()).trim());
		
		return new Context(buffer, tree);
	}
	
	public void clean() {
	}

	public boolean matchesNode(Node node) {
		return node instanceof UntilNode;
	}
	
	public static class UntilNode extends Branch {
		public Deployer register(Handler[] handlers) {
			Node childNode = getChildren()[0];
			String timeUnit = getData(Interpreter.UNTIL_UNIT);
			long period = Long.parseLong(getData(Interpreter.UNTIL)); 
			if(timeUnit.equalsIgnoreCase("SECONDS")) {
				period *= 1000;
			}
			
			Deployer childDeployer = childNode.register(handlers);
			Deployer deployer = new UntilDeployer(period,childDeployer);
			Reactor.getInstance().addDeployer(deployer);
			
			return childDeployer;
		}
	}
}