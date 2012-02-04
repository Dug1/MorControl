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
		Branch tree = new Branch();
		
		Context c = trajan.parse(buffer, tree);
		
		Node child = c.getCurrentNode().getChildren()[0];
		Assert.assertEquals(0, c.getBuffer().size());
		Assert.assertTrue(trajan.matchesNode(child));

		buffer = new StringBuffer("every 500 milliseconds and button 5 pressed");
		tree = new Branch();
		
		c = trajan.parse(buffer, tree);
		
		child = c.getCurrentNode().getChildren()[0];
		Assert.assertEquals(1, c.getBuffer().size());
		Assert.assertTrue(trajan.matchesNode(child));
	}
	
	public void testEveryNode() {
		Reactor.getInstance().start();
		EveryExpression.EveryNode node = new EveryExpression.EveryNode();
		node.putData(Interpreter.PERIOD, "1");
		node.putData(Interpreter.PERIOD_UNIT, "SECONDS");
		class TestHandler implements Handler {
			boolean isCalled = false;
			int timesCalled = 0;
			
			public void execute(Event event){
				System.out.println("Every Test");
				isCalled = true;
				timesCalled ++;
			}
		}
		TestHandler handler = new TestHandler();
		Handler[] handlers = {handler};
		
		node.register(handlers);
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() < start+5000) {
			Reactor.getInstance().tick();
		}
		
		assertTrue(handler.isCalled);
		assertTrue(handler.timesCalled >= 3);
		assertTrue(handler.timesCalled <= 5);
	}
}