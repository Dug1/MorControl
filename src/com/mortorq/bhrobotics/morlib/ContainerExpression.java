package com.mortorq.bhrobotics.morlib;

public class ContainerExpression implements Expression {
	
	public boolean matches() {
		return true;
	}
	
	public Context parse(StringBuffer buffer, Node tree) {
		new Context(buffer, tree);
	}
	
	public void clean() {
	}
}