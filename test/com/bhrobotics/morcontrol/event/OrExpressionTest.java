package com.bhrobotics.morcontrol.event;

import com.bhrobotics.morcontrol.Ticker;
import com.bhrobotics.morcontrol.event.Branch;
import com.bhrobotics.morcontrol.event.ContainerExpression;
import com.bhrobotics.morcontrol.event.Context;
import com.bhrobotics.morcontrol.event.DelayExpression;
import com.bhrobotics.morcontrol.event.Event;
import com.bhrobotics.morcontrol.event.Handler;
import com.bhrobotics.morcontrol.event.Interpreter;
import com.bhrobotics.morcontrol.event.Leaf;
import com.bhrobotics.morcontrol.event.Node;
import com.bhrobotics.morcontrol.event.OnceExpression;
import com.bhrobotics.morcontrol.event.OrExpression;
import com.bhrobotics.morcontrol.event.Reactor;
import com.bhrobotics.morcontrol.event.StringBuffer;

import junit.framework.*;

public class OrExpressionTest extends TestCase {
	private OrExpression augustus;

	protected void setUp() throws Exception {
		super.setUp();
		augustus = new OrExpression();
	}

	public void testInit() {
		Assert.assertNotNull(augustus);
	}

	public void testMatches() {
		Assert.assertTrue(augustus.matches("or"));
		Assert.assertTrue(augustus.matches("or many days afterwords"));
		Assert.assertFalse(augustus.matches("chickens or beef"));
	}

	public void testParse() {
		StringBuffer buffer = new StringBuffer("or");
		Node seed = new Branch();
		
		Context c = augustus.parse(buffer, seed);
		
		Assert.assertNotSame(c.getCurrentNode(), seed);
		Assert.assertEquals(0, buffer.size());
		Assert.assertTrue(c.getCurrentNode() instanceof OrExpression.OrNode);

		
		buffer = new StringBuffer("or silky yuletides");
		Leaf leaf = new Leaf();
		seed = new Branch();
		seed.addChild(leaf);

		c = augustus.parse(buffer, seed);
		
		Assert.assertSame(c.getCurrentNode().getChildren()[0], leaf);
		Assert.assertEquals("silky yuletides", buffer.readOne());
		Assert.assertTrue(c.getCurrentNode() instanceof OrExpression.OrNode) ;
	}
	
	public void testOrNode() {
		Ticker.getInstance().registerTickable(Reactor.getInstance());
		Ticker.getInstance().start();
		ContainerExpression.ContainerNode containerNode = new ContainerExpression.ContainerNode();
		OnceExpression.OnceNode node = new OnceExpression.OnceNode();
		DelayExpression.DelayNode delayNode = new DelayExpression.DelayNode();
		OnceExpression.OnceNode onceNode = new OnceExpression.OnceNode();
		delayNode.putData(Interpreter.DELAY, "3");
		delayNode.putData(Interpreter.DELAY_UNIT, "SECONDS");
		delayNode.addChild(onceNode);
		containerNode.addChild(node);
		containerNode.addChild(delayNode);
		OrExpression.OrNode orNode = new OrExpression.OrNode(containerNode);
		class TestHandler implements Handler {
			int timesCalled = 0;
			
			public void execute(Event event){
				timesCalled ++;
			}
		}
		TestHandler handler = new TestHandler();
		Handler[] handlers = {handler};
		
		orNode.register(handlers);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
		
		assertEquals(2, handler.timesCalled);
	}
}