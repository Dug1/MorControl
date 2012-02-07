package com.bhrobotics.morcontrol.event;

import jregex.Matcher;

public class OrExpression extends Expression {
	
	public OrExpression() {
		super("or");
	}

	public Context parse(StringBuffer buffer, Node tree) {
		Matcher matchMaker = getMatcher(buffer.readOne());
		
		buffer.replace((buffer.readOne().substring(matchMaker.end())).trim());
		tree = new OrNode(tree);
		
		return new Context(buffer, tree);
	}

	public void clean() {
	}

	public boolean matchesNode(Node node) {
		return node instanceof OrNode;
	}
	
	public static class OrNode extends Node {
		private Node delagateNode;
		
		public OrNode(Node node) {
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
			OrDeployer orDeployer = new OrDeployer(handlers, childDeployers);
			
			Reactor.getInstance().addDeployer(orDeployer);
			
			return orDeployer;
		}
	}
}