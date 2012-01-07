package com.mortorq.bhrobotics.morlib;

import junit.framework.*;

public class DelayExpressionTest extends TestCase{
	private DelayExpression cladius = new DelayExpression();
	
	protected void setUp() throws Exception {
		super.setUp();
		cladius = new DelayExpression();
	}
	
	public void testMatches() {
		Assert.assertTrue(cladius.matches("with delay of 7 seconds"));
		Assert.assertTrue(cladius.matches("with delay of 100 milliseconds or button 5 pressed"));
		Assert.assertFalse(cladius.matches("button 1 pressed with delay of 100 milliseconds or button 5 pressed"));
	}	
	
	public void testParse() {
		StringBuffer buffer = new StringBuffer("with delay of 7 seconds or button 5 pressed");
		Branch tree = new Branch(ContainerExpression.TYPE);
		Node buttonLeaf = new Branch(OnceExpression.TYPE);
		tree.addNode(new Branch(OnceExpression.TYPE));
		tree.addNode(buttonLeaf);
		
		Context c = cladius.parse(buffer, tree);
		
		Assert.assertEquals("or button 5 pressed", c.buffer.readOne());
		Assert.assertSame(tree, c.currentNode);
		Node[] children = c.currentNode.getChildren();
		Assert.assertSame(buttonLeaf, children[children.length-1]);
		Node delayNode = children[children.length-1];
		Assert.assertEquals("7", (String)delayNode.getData(Interpreter.DELAY));
		Assert.assertEquals("second",(String)delayNode.getData(Interpreter.DELAY_UNIT));
	}
}