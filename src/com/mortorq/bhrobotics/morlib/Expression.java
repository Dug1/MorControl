package com.mortorq.bhrobotics.morlib;

public interface Expression {
	public String getType();
	public boolean matches(String argument);
	public Context parse(StringBuffer buffer, Node tree);
	public FutureReference makeTrigger(Node tree, Handler[] handlers) throws TriggerException;
	public void clean();
}