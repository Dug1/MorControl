package com.mortorq.bhrobotics.morlib;

import junit.framework.*;

public class TickExpressionTest extends TestCase {
	private TickExpression diocletian;

	protected void setUp() throws Exception {
		super.setUp();
		diocletian = new TickExpression();		
	}
	
	public void testMatches() {
		Assert.assertTrue(diocletian.matches("every tick"));
		Assert.assertFalse(diocletian.matches("every 25 seconds"));
		Assert.assertTrue(diocletian.matches("every tick and button 5 pressed"));
		Assert.assertFalse(diocletian.matches("button 5 pressed and every tick"));
	}
	
	public void testParse() {
		StringBuffer buffer = new StringBuffer("every tick");
		Branch tree = new Branch(ContainerExpression.TYPE);
		
		Context c = diocletian.parse(buffer, tree);
		
		Leaf child = (Leaf)c.currentNode.getChildren()[0];
		Assert.assertEquals(0, c.buffer.size());
		Assert.assertEquals(TickExpression.TYPE, child.getType());
		
		buffer = new StringBuffer("every tick and button 5 pressed");
		tree = new Branch(ContainerExpression.TYPE);
		
		c = diocletian.parse(buffer, tree);
		
		child = (Leaf)c.currentNode.getChildren()[0];
		Assert.assertEquals(1, c.buffer.size());
		Assert.assertEquals(TickExpression.TYPE, child.getType());
	}
}