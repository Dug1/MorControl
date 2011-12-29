package com.mortorq.bhrobotics.morlib;

import java.util.Stack;
import jregex.Pattern;
import jregex.Matcher;

public class ContainerExpression implements Expression {
	private Pattern pattern = new Pattern("[(|)]"); 
	private Matcher matchMaker = pattern.matcher();
	private Stack nodeStack = new Stack();
	
	public boolean matches(String token) {
		return matchMaker.matches(token);
	}

	public Context parse(StringBuffer buffer, Node tree) {
		if (buffer.readOne().equals("(")) {
			Node newNode = new Branch("Container"); 
			tree.addNode(newNode);
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
}