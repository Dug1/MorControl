package com.mortorq.bhrobotics.morlib;

import junit.framework.*;

public class OrExpressionTest extends TestCase {
	private OrExpression augustus;

	protected void setUp() throws Exception {
		super.setUp();
		augustus = new OrExpression();
	}

	public void testInit() {
		Assert.assertNotNull(augustus);
	}

	public void testMatches() {
		Assert.assertTrue(augustus.matches("or"));
		Assert.assertTrue(augustus.matches("or many days afterwords"));
		Assert.assertFalse(augustus.matches("chickens or beef"));
	}

	public void testParse() {
		StringBuffer buffer = new StringBuffer("or");
		Node seed = new Branch();
		
		Context c = augustus.parse(buffer, seed);
		
		Assert.assertNotSame(c.getCurrentNode(), seed);
		Assert.assertEquals(0, buffer.size());
		Assert.assertTrue(c.getCurrentNode() instanceof OrExpression.OrNode);

		
		buffer = new StringBuffer("or silky yuletides");
		Leaf leaf = new Leaf();
		seed = new Branch();
		seed.addChild(leaf);

		c = augustus.parse(buffer, seed);
		
		Assert.assertSame(c.getCurrentNode().getChildren()[0], leaf);
		Assert.assertEquals("silky yuletides", buffer.readOne());
		Assert.assertTrue(c.getCurrentNode() instanceof OrExpression.OrNode) ;
	}
}