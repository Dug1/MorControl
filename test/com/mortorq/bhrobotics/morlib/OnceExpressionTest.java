package com.mortorq.bhrobotics.morlib;

import junit.framework.*;

public class OnceExpressionTest extends TestCase {
	private OnceExpression tiberius;

	protected void setUp() throws Exception {
		super.setUp();
		tiberius = new OnceExpression();
	}
	
	public void testMatches() {
		Assert.assertTrue(tiberius.matches("once"));
		Assert.assertTrue(tiberius.matches("once and button 5 pressed"));
		Assert.assertFalse(tiberius.matches("button 5 pressed and once"));
	}
	
	public void testParse() {
		StringBuffer buffer = new StringBuffer("once");
		Branch tree = new Branch(ContainerExpression.TYPE);
		
		Context c = tiberius.parse(buffer, tree);
		
		Node child = c.currentNode.getChildren()[0];
		Assert.assertEquals(0, c.buffer.size());
		Assert.assertEquals(OnceExpression.TYPE, child.getType());
		
		buffer = new StringBuffer("once and button 5 pressed");
		tree = new Branch(ContainerExpression.TYPE);
		
		c = tiberius.parse(buffer, tree);
		
		child = c.currentNode.getChildren()[0];
		Assert.assertEquals(1, c.buffer.size());
		Assert.assertEquals(OnceExpression.TYPE, child.getType());
	}
}