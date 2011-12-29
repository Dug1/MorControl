package com.mortorq.bhrobotics.morlib;

import junit.framework.*;

public class ContainerExpressionTest extends TestCase {
	private ContainerExpression nero;

	protected void setUp() throws Exception{
		super.setUp();
		nero = new ContainerExpression();
	}

	public void testInit() {
		Assert.assertNotNull(nero);
	}
	
	public void testMatches() {
		Assert.assertTrue(nero.matches("("));
		Assert.assertTrue(nero.matches(")"));
		Assert.assertFalse(nero.matches("(hello)"));
	}

	public void testParse() {
		StringBuffer test = new StringBuffer("()");
		Node seed = new Branch("Container");
		
		Context c = nero.parse(test, seed);
		
		Assert.assertNotSame(c.currentNode, seed);
		Assert.assertEquals(1,c.buffer.size());
		Assert.assertEquals(c.currentNode.getType(), "Container");
		
		c = nero.parse(c.buffer, c.currentNode);
		
		Assert.assertSame(c.currentNode, seed);
		Assert.assertEquals(0,c.buffer.size());
		Assert.assertEquals(1,seed.getChildren().length);
	}
}