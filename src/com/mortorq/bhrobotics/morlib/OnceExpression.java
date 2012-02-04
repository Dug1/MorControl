package com.mortorq.bhrobotics.morlib;

import jregex.Matcher;

public class OnceExpression extends Expression {
	public OnceExpression() {
		super("once");
	}

	public Context parse(StringBuffer buffer, Node tree) {
		String token = buffer.readOne();
		Matcher matcher = getMatcher(token);
		
		token = token.substring(matcher.end());
		buffer.replace(token);
		tree.addChild(new OnceNode());
		
		return new Context(buffer, tree);
	}

	public void clean() {
	}

	public boolean matchesNode(Node node) {
		return node instanceof OnceNode;
	}
	
	public static class OnceNode extends Leaf {
		
		public Deployer register(Handler[] handlers) {
			Deployer deployer = new OnceDeployer(handlers);
			Reactor.getInstance().addDeployer(deployer);
			return deployer;
		}
	}
}