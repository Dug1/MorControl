package com.mortorq.bhrobotics.event;

import com.bhrobotics.morcontrol.event.Branch;
import com.bhrobotics.morcontrol.event.Context;
import com.bhrobotics.morcontrol.event.Deployer;
import com.bhrobotics.morcontrol.event.Event;
import com.bhrobotics.morcontrol.event.EveryExpression;
import com.bhrobotics.morcontrol.event.Handler;
import com.bhrobotics.morcontrol.event.Interpreter;
import com.bhrobotics.morcontrol.event.Node;
import com.bhrobotics.morcontrol.event.Reactor;
import com.bhrobotics.morcontrol.event.StringBuffer;

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
		
		Deployer deployer = node.register(handlers);
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() < start+5000) {
			Reactor.getInstance().tick();
		}
		Reactor.getInstance().removeDeployer(deployer);
		Reactor.getInstance().tick();
		Reactor.getInstance().tick();
		
		assertFalse(Reactor.getInstance().getRegistry().hasDeployer(deployer));
		assertEquals(0, Reactor.getInstance().getTickable().size());
		assertTrue(handler.isCalled);
		assertTrue(handler.timesCalled >= 3);
		assertTrue(handler.timesCalled <= 5);
	}
}