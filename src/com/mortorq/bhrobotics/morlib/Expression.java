package com.mortorq.bhrobotics.morlib;

public interface Expression {
	public boolean matches(String argument);
	public Context parse(StringBuffer buffer, Node tree);
	public void clean();
}