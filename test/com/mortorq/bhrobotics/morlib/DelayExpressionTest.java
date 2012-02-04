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
		Branch tree = new Branch();
		Node buttonLeaf = new Leaf();
		tree.addChild(new Branch());
		tree.addChild(buttonLeaf);
		
		Context c = cladius.parse(buffer, tree);
		
		Assert.assertEquals("or button 5 pressed", c.getBuffer().readOne());
		Assert.assertSame(tree, c.getCurrentNode());
		Node delayNode = c.getCurrentNode().getChildren()[1];
		Node[] children = delayNode.getChildren();
		
		Assert.assertTrue(delayNode instanceof DelayExpression.DelayNode);
		Assert.assertSame(buttonLeaf, children[children.length-1]);
	}
	
	public void testDelayNode() {
		Reactor.getInstance().start();
		Node delayNode = new DelayExpression.DelayNode();
		delayNode.putData(Interpreter.DELAY, "3");
		delayNode.putData(Interpreter.DELAY_UNIT, "SECONDS");
		Node onceNode = new OnceExpression.OnceNode();
		delayNode.addChild(onceNode);
		class TestHandler implements Handler {
			boolean isCalled = false;
			int timesCalled = 0;
			
			public void execute(Event event){
				isCalled = true;
				timesCalled ++;
			}
		}
		TestHandler handler = new TestHandler();
		Handler[] handlers = {handler};
		
		delayNode.register(handlers);
		
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() < start+5000) {
			Reactor.getInstance().tick();
		}

		assertTrue(handler.isCalled);
		assertEquals(1,handler.timesCalled);
		assertFalse(Reactor.getInstance().getRegistry().getElements().hasMoreElements());
	}
}