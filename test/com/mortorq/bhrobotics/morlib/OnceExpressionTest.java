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
		Branch tree = new Branch("Container");
		
		Context c = tiberius.parse(buffer, tree);
		
		Leaf child = (Leaf)c.currentNode.getChildren()[0];
		Assert.assertEquals(0, c.buffer.size());
		Assert.assertEquals("Once", child.getType());
		
		buffer = new StringBuffer("once and button 5 pressed");
		tree = new Branch("Container");
		
		c = tiberius.parse(buffer, tree);
		
		child = (Leaf)c.currentNode.getChildren()[0];
		Assert.assertEquals(1, c.buffer.size());
		Assert.assertEquals("Once", child.getType());
	}
}