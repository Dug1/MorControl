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
		Branch tree = new Branch();
		
		Context c = tiberius.parse(buffer, tree);
		
		Node child = c.getCurrentNode().getChildren()[0];
		Assert.assertEquals(0, c.getBuffer().size());
		Assert.assertTrue(child instanceof OnceExpression.OnceNode);
		
		buffer = new StringBuffer("once and button 5 pressed");
		tree = new Branch();
		
		c = tiberius.parse(buffer, tree);
		
		child = c.getCurrentNode().getChildren()[0];
		Assert.assertEquals(1, c.getBuffer().size());
		Assert.assertTrue(child instanceof OnceExpression.OnceNode);
	}
	
	public void testOnceNode() {
		Reactor.getInstance().start();
		class TestHandler implements Handler {
			boolean isCalled = false;
			
			public void execute(Event event) {
				System.out.println("Squash");
				isCalled = true;
			}
		}
		OnceExpression.OnceNode node = new OnceExpression.OnceNode();
		TestHandler handler = new TestHandler();
		Handler[] handlers = {handler};
		node.register(handlers);
		
		Reactor.getInstance().tick();
		Reactor.getInstance().tick();
		Reactor.getInstance().tick();
		Reactor.getInstance().tick();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
		
		assertTrue(handler.isCalled);
	}
}