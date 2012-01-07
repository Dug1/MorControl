package com.mortorq.bhrobotics.morlib;

import junit.framework.*;

public class UntilExpressionTest extends TestCase{
	private UntilExpression constantine = new UntilExpression();
	
	protected void setUp() throws Exception {
		super.setUp();
		constantine = new UntilExpression();
	}
	
	public void testMatches() {
		Assert.assertTrue(constantine.matches("until 14 seconds"));
		Assert.assertTrue(constantine.matches("until 100 milliseconds or button 5 pressed"));
		Assert.assertFalse(constantine.matches("button 1 pressed until 700 milliseconds or button 5 pressed"));
	}	
	
	public void testParse() {
		StringBuffer buffer = new StringBuffer("until 7 seconds or button 5 pressed");
		Branch tree = new Branch(ContainerExpression.TYPE);
		Node everyLeaf = new Branch(EveryExpression.TYPE);
		tree.addNode(new Branch(OnceExpression.TYPE));
		tree.addNode(everyLeaf);
		
		Context c = constantine.parse(buffer, tree);
		
		Assert.assertEquals("or button 5 pressed", c.buffer.readOne());
		Assert.assertSame(tree, c.currentNode);
		Node[] children = c.currentNode.getChildren();
		Node untilNode = children[children.length-1];
		Assert.assertEquals(2, children.length);
		Assert.assertEquals(UntilExpression.TYPE, untilNode.getType());
		Assert.assertEquals("7", untilNode.getData(Interpreter.UNTIL));
		Assert.assertEquals("second", untilNode.getData(Interpreter.UNTIL_UNIT));
		Assert.assertSame(everyLeaf, untilNode.getChildren()[0]);
	}
}