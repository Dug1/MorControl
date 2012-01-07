package com.mortorq.bhrobotics.morlib;

import junit.framework.*;

public class EveryExpressionTest extends TestCase {
	private EveryExpression trajan;

	protected void setUp() throws Exception {
		super.setUp();
		trajan = new EveryExpression();		
	}
	
	public void testMatches() {
		Assert.assertTrue(trajan.matches("every 1 second"));
		Assert.assertTrue(trajan.matches("every 25 seconds"));
		Assert.assertFalse(trajan.matches("every"));
		Assert.assertTrue(trajan.matches("every 5 seconds and button 5 pressed"));
		Assert.assertFalse(trajan.matches("button 5 pressed and every 5 seconds"));
	}
	
	public void testParse() {
		StringBuffer buffer = new StringBuffer("every 1 second");
		Branch tree = new Branch(ContainerExpression.TYPE);
		
		Context c = trajan.parse(buffer, tree);
		
		Node child = c.currentNode.getChildren()[0];
		Assert.assertEquals(0, c.buffer.size());
		Assert.assertEquals(EveryExpression.TYPE, child.getType());
		Assert.assertEquals("1", (String)child.getData(Interpreter.PERIOD));
		Assert.assertEquals("second", (String)child.getData(Interpreter.PERIOD_UNIT));
		
		buffer = new StringBuffer("every 500 milliseconds and button 5 pressed");
		tree = new Branch(ContainerExpression.TYPE);
		
		c = trajan.parse(buffer, tree);
		
		child = c.currentNode.getChildren()[0];
		Assert.assertEquals(1, c.buffer.size());
		Assert.assertEquals(EveryExpression.TYPE, child.getType());
		Assert.assertEquals("500", (String)child.getData(Interpreter.PERIOD));
		Assert.assertEquals("millisecond", (String)child.getData(Interpreter.PERIOD_UNIT));
	}
}