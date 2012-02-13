package com.bhrobotics.morcontrol.event;

import com.bhrobotics.morcontrol.Ticker;
import com.bhrobotics.morcontrol.event.Branch;
import com.bhrobotics.morcontrol.event.Context;
import com.bhrobotics.morcontrol.event.DelayExpression;
import com.bhrobotics.morcontrol.event.Event;
import com.bhrobotics.morcontrol.event.Handler;
import com.bhrobotics.morcontrol.event.Interpreter;
import com.bhrobotics.morcontrol.event.Leaf;
import com.bhrobotics.morcontrol.event.Node;
import com.bhrobotics.morcontrol.event.OnceExpression;
import com.bhrobotics.morcontrol.event.Reactor;
import com.bhrobotics.morcontrol.event.StringBuffer;

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
		Ticker.getInstance().registerTickable(Reactor.getInstance());
		Ticker.getInstance().start();
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
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
		assertFalse(handler.isCalled);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}

		assertTrue(handler.isCalled);
		assertEquals(1,handler.timesCalled);
	}
}