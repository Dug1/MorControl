package com.bhrobotics.morcontrol.event;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.bhrobotics.morcontrol.Ticker;

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
		Node seed = new Branch();
		
		Context c = caligula.parse(buffer, seed);
		
		Assert.assertNotSame(c.getCurrentNode(), seed);
		Assert.assertEquals(0, buffer.size());
		Assert.assertTrue(c.getCurrentNode() instanceof AndExpression.AndNode);

		
		buffer = new StringBuffer("and silky yuletides");
		seed = new Branch();
		Leaf child = new Leaf();
		seed.addChild(child);

		c = caligula.parse(buffer, seed);

		Assert.assertSame(c.getCurrentNode().getChildren()[0], child);
		Assert.assertEquals("silky yuletides", buffer.readOne());
		Assert.assertTrue(c.getCurrentNode() instanceof AndExpression.AndNode);
	}
	
	public void testAndNode() {
		Ticker.getInstance().registerTickable(Reactor.getInstance());
		Ticker.getInstance().start();
		
		ContainerExpression.ContainerNode node = new ContainerExpression.ContainerNode();
		TickExpression.TickNode tickNode = new TickExpression.TickNode();
		DelayExpression.DelayNode delayNode = new DelayExpression.DelayNode();
		OnceExpression.OnceNode onceNode = new OnceExpression.OnceNode();
		delayNode.putData(Interpreter.DELAY, "3");
		delayNode.putData(Interpreter.DELAY_UNIT, "SECONDS");
		delayNode.addChild(onceNode);
		node.addChild(tickNode);
		node.addChild(delayNode);
		AndExpression.AndNode andNode = new AndExpression.AndNode(node);
		class TestHandler implements Handler {
			int timesCalled = 0;
			
			public void execute(Event event){
				timesCalled ++;
			}
		}
		TestHandler handler = new TestHandler();
		Handler[] handlers = {handler};
		
		andNode.register(handlers);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			fail("Main Thread Interrupted");
		}
		
		assertEquals(1, handler.timesCalled);
		
		Ticker.getInstance().unregisterTickable(Reactor.getInstance());
		Ticker.getInstance().stop();
	}
}