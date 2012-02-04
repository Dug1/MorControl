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
	}

	public void testParse() {
		StringBuffer test = new StringBuffer("()");
		Node seed = new Branch();
		
		Context c = nero.parse(test, seed);
		
		Assert.assertNotSame(c.getCurrentNode(), seed);
		Assert.assertEquals(1,c.getBuffer().size());
		assertTrue(c.getCurrentNode() instanceof ContainerExpression.ContainerNode);
		
		c = nero.parse(c.getBuffer(), c.getCurrentNode());
		
		Assert.assertSame(c.getCurrentNode(), seed);
		Assert.assertEquals(0,c.getBuffer().size());
		Assert.assertEquals(1,seed.getChildren().length);
	}
}