package com.bhrobotics.morcontrol.event;

import jregex.Matcher;

public class AndExpression extends Expression {
	
	public AndExpression() {
		super("and");
	}

	public Context parse(StringBuffer buffer, Node tree) {
		Matcher matchMaker = getMatcher(buffer.readOne());
		
		buffer.replace((buffer.readOne().substring(matchMaker.end())).trim());
		tree = new AndNode(tree);
		
		return new Context(buffer, tree);
	}
	
	public void clean() {
	}
	
	public boolean matchesNode(Node node) {
		return node instanceof AndNode;
	}
	
	public static class AndNode extends Node {
		private Node delagateNode;
		
		public AndNode(Node node) {
			delagateNode = node;
		}
		
		public void addChild(Node e) {
			delagateNode.addChild(e);
			
		}

		public void removeChild(Node e) {
			delagateNode.removeChild(e);
			
		}

		public Node[] getChildren() {
			return delagateNode.getChildren();
		}
		
		public Deployer register(Handler[] handlers) {
			Node[] children = getChildren();
			Deployer[] childDeployers = new Deployer[children.length];
			class HandlerDummy implements Handler {
				public void execute(Event event) {
				}
			}
	
			Handler[] handlerArray = {new HandlerDummy()};
			for(int i = 0; i < children.length; i++) {
				childDeployers[i] = children[i].register(handlerArray);
			}
			
			Deployer andDeployer = new AndDeployer(handlers, childDeployers);
			Reactor.getInstance().addDeployer(andDeployer);
			
			return andDeployer;
		}
	}
}