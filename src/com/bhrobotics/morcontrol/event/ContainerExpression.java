package com.bhrobotics.morcontrol.event;

import java.util.Stack;

public class ContainerExpression extends Expression {;
	private Stack nodeStack = new Stack();	
	
	public ContainerExpression() {
		super("[(|)]");
	}
	
	public Context parse(StringBuffer buffer, Node tree) {
		if (buffer.readOne().equals("(")) {
			Node newNode = new ContainerNode(); 
			tree.addChild(newNode);
			nodeStack.push(tree);
			buffer.remove();
			return new Context(buffer, newNode);
		} else {
			buffer.remove();
			return new Context(buffer, (Node)nodeStack.pop());
		}
	}
	
	public void clean() {
		nodeStack.removeAllElements();
	}
	
	public boolean matchesNode(Node node) {
		return node instanceof ContainerNode;
	}
	
	public static class ContainerNode extends Branch {
		public Deployer register(Handler[] handlers) {
			Node child = getChildren()[0];
			return child.register(handlers);
		}
	}
}