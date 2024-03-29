package com.bhrobotics.morcontrol.event;

import com.bhrobotics.morcontrol.Ticker;

import junit.framework.*;

public class TickExpressionTest extends TestCase {
	private TickExpression diocletian;

	protected void setUp() throws Exception {
		super.setUp();
		diocletian = new TickExpression();		
	}
	
	public void testMatches() {
		Assert.assertTrue(diocletian.matches("every tick"));
		Assert.assertFalse(diocletian.matches("every 25 seconds"));
		Assert.assertTrue(diocletian.matches("every tick and button 5 pressed"));
		Assert.assertFalse(diocletian.matches("button 5 pressed and every tick"));
	}
	
	public void testParse() {
		StringBuffer buffer = new StringBuffer("every tick");
		Branch node = new Branch();
		Leaf leaf = new Leaf();
			
		Context c = diocletian.parse(buffer, node);
		
		Node child = c.getCurrentNode().getChildren()[0];
		Assert.assertEquals(0, c.getBuffer().size());
		Assert.assertTrue(diocletian.matchesNode(child));
		Assert.assertFalse(diocletian.matchesNode(leaf));
	}
	
	public void testNodeMatches() {
		Node tickNode = new TickExpression.TickNode();
		Node leaf = new Leaf();
		assertTrue(diocletian.matchesNode(tickNode));
		assertFalse(diocletian.matchesNode(leaf));
	}
	
	public void testTickNode() {
		Ticker.getInstance().registerTickable(Reactor.getInstance());
		Ticker.getInstance().start();
		class TestHandler implements Handler {
			boolean isCalled = false;
			int timesCalled = 0;
			
			public void execute(Event event) {
				System.out.println("RaR");
				isCalled = true;
				timesCalled++;
			}
		}
		TickExpression.TickNode node = new TickExpression.TickNode();
		TestHandler handler = new TestHandler();
		Handler[] handlers = {handler};
		Deployer deployer = node.register(handlers);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
		Reactor.getInstance().removeDeployer(deployer);
		int timesCalled = handler.timesCalled; 
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
		
		assertFalse(Reactor.getInstance().getRegistry().hasDeployer(deployer));
		assertEquals(1, Ticker.getInstance().getTickables().size());
		assertTrue(handler.isCalled);
		assertEquals(timesCalled, handler.timesCalled);
	}
}