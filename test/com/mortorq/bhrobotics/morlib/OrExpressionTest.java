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
		Node seed = new Branch("Container");
		
		Context c = augustus.parse(buffer, seed);
		
		Assert.assertSame(c.currentNode, seed);
		Assert.assertEquals(0, buffer.size());
		Assert.assertEquals(c.currentNode.getType(), "Or");

		
		buffer = new StringBuffer("or silky yuletides");
		seed = new Branch("Container");

		c = augustus.parse(buffer, seed);
		
		Assert.assertSame(c.currentNode, seed);
		Assert.assertEquals("silky yuletides", buffer.readOne());
		Assert.assertEquals(c.currentNode.getType(), "Or");
	}
}