package com.mortorq.bhrobotics.morlib;

import jregex.Matcher;

public class TickExpression extends Expression {
	
	public TickExpression() {
		super("every tick");
	}

	public Context parse(StringBuffer buffer, Node tree) {
		Matcher matchMaker = getMatcher(buffer.readOne());
		matchMaker.find();
		int end = matchMaker.end();

		String newToken = buffer.readOne().substring(end); 
		buffer.replace(newToken);
		
		tree.addChild(new TickNode());
		
		return new Context(buffer, tree);
	}
	
	public boolean matchesNode(Node node) {
		return node instanceof TickNode;
	}
	
	public void clean() {
	}
	
	public static class TickNode extends Leaf {

		public Deployer register(Handler[] handlers) {
			Deployer deployer = new TickDeployer(handlers);
			Reactor.getInstance().addDeployer(deployer);
			return deployer;
		}
	}
}