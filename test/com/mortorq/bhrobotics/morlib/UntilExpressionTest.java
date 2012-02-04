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
		Branch tree = new Branch();
		Node everyLeaf = new Leaf();
		tree.addChild(everyLeaf);
		tree.addChild(everyLeaf);

		Context c = constantine.parse(buffer, tree);

		Assert.assertEquals("or button 5 pressed", c.getBuffer().readOne());
		Assert.assertSame(tree, c.getCurrentNode());
		Node[] children = c.getCurrentNode().getChildren();
		Node untilNode = children[children.length-1];
		Assert.assertEquals(2, children.length);
		Assert.assertTrue(untilNode instanceof UntilExpression.UntilNode);
		Assert.assertSame(everyLeaf, untilNode.getChildren()[0]);
	}
	
	public void testUntilNode() {
		Reactor.getInstance().start();
		UntilExpression.UntilNode untilNode = new UntilExpression.UntilNode();
		EveryExpression.EveryNode tickNode = new EveryExpression.EveryNode();
		tickNode.putData(Interpreter.PERIOD, "500");
		tickNode.putData(Interpreter.PERIOD_UNIT, "MILLISECONDS");
		untilNode.addChild(tickNode);
		untilNode.putData(Interpreter.UNTIL, "3");
		untilNode.putData(Interpreter.UNTIL_UNIT, "SECONDS");
		class TestHandler implements Handler {
			boolean isCalled = false;
			int timesCalled = 0;
			
			public void execute(Event event){
				System.out.println("Hello!!");
				isCalled = true;
				timesCalled ++;
			}
		}
		TestHandler handler = new TestHandler();
		Handler[] handlers = {handler};
		
		untilNode.register(handlers);
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() < start+5000) {
			Reactor.getInstance().tick();
		}
		
		assertTrue(handler.isCalled);
		assertTrue(6 > handler.timesCalled);
	}
}