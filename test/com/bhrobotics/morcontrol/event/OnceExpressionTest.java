package com.bhrobotics.morcontrol.event;

import com.bhrobotics.morcontrol.Ticker;
import com.bhrobotics.morcontrol.event.Branch;
import com.bhrobotics.morcontrol.event.Context;
import com.bhrobotics.morcontrol.event.Deployer;
import com.bhrobotics.morcontrol.event.Event;
import com.bhrobotics.morcontrol.event.Handler;
import com.bhrobotics.morcontrol.event.Node;
import com.bhrobotics.morcontrol.event.OnceExpression;
import com.bhrobotics.morcontrol.event.Reactor;
import com.bhrobotics.morcontrol.event.StringBuffer;

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
		Ticker.getInstance().registerTickable(Reactor.getInstance());
		Ticker.getInstance().start();
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
		Deployer deployer = node.register(handlers);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
		
		assertFalse(Reactor.getInstance().getRegistry().hasDeployer(deployer));
		assertEquals(1, Ticker.getInstance().getTickables().size());
		assertTrue(handler.isCalled);
	}
}