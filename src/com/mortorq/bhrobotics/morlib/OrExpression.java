package com.mortorq.bhrobotics.morlib;

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
			// TODO register method for OrNode
			return null;
		}
		
	}
}