package com.mortorq.bhrobotics.morlib;

import junit.framework.*;

public class AndExpressionTest extends TestCase {
	private AndExpression caligula;

	protected void setUp() throws Exception {
		super.setUp();
		caligula = new AndExpression();
	}

	public void testInit() {
		Assert.assertNotNull(caligula);
	}
	
	public void testMatches() {
		Assert.assertTrue(caligula.matches("and"));
		Assert.assertTrue(caligula.matches("and many days afterwords"));
		Assert.assertFalse(caligula.matches("chickens and beef"));
	}

	public void testParse() {
		StringBuffer buffer = new StringBuffer("and");
		Node seed = new Branch("Container");
		
		Context c = caligula.parse(buffer, seed);
		
		Assert.assertSame(c.currentNode, seed);
		Assert.assertEquals(0, buffer.size());
		Assert.assertEquals(c.currentNode.getType(), "And");

		
		buffer = new StringBuffer("and silky yuletides");
		seed = new Branch("Container");

		c = caligula.parse(buffer, seed);

		Assert.assertSame(c.currentNode, seed);
		Assert.assertEquals("silky yuletides", buffer.readOne());
		Assert.assertEquals(c.currentNode.getType(), "And");
	}
}