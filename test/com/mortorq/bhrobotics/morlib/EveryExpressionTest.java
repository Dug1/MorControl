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
		Branch tree = new Branch("Container");
		
		Context c = trajan.parse(buffer, tree);
		
		Leaf child = (Leaf)c.currentNode.getChildren()[0];
		Assert.assertEquals(0, c.buffer.size());
		Assert.assertEquals("Every", child.getType());
		Assert.assertEquals("1 second", child.getData());
		
		buffer = new StringBuffer("every 500 millisecond and button 5 pressed");
		tree = new Branch("Container");
		
		c = trajan.parse(buffer, tree);
		
		child = (Leaf)c.currentNode.getChildren()[0];
		Assert.assertEquals(1, c.buffer.size());
		Assert.assertEquals("Every", child.getType());
		Assert.assertEquals("500 millisecond", child.getData());
	}
}