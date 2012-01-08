package com.mortorq.bhrobotics.morlib;

import junit.framework.*;

public class MakeTriggerTest extends TestCase {
	
	protected void setUp() throws Exception {
		Reactor.Instance().start();
		super.setUp();
	}
	
	protected void tearDown() throws Exception {
		Reactor.Instance().stop();
		super.tearDown();
	}
	
	public void testEndpointExpressions() {
		Reactor.Instance().getInterpreter().addPattern(new OnceExpression()); 
		Reactor.Instance().getInterpreter().addPattern(new DelayExpression()); 
		Reactor.Instance().getInterpreter().addPattern(new ContainerExpression()); 
		TestHandler check = new TestHandler();
		Handler[] handler = {check};
		Context context = Reactor.Instance().getInterpreter().interpret(new Context(new StringBuffer("once with delay of 5 seconds"), new Branch(ContainerExpression.TYPE)));

		Reactor.Instance().getInterpreter().makeTrigger(context.currentNode, handler);
		Reactor.Instance().tick();
		Assert.assertFalse(check.called);
		try {
			Thread.currentThread().sleep(6000);
		} catch(InterruptedException e) {Assert.fail("Main thread was interrputed");}
		
		Assert.assertTrue(check.called);
	}
	
	public void testFlowStatements() {
		Reactor.Instance().getInterpreter().addPattern(new ContainerExpression());
		Reactor.Instance().getInterpreter().addPattern(new OrExpression());
		Reactor.Instance().getInterpreter().addPattern(new AndExpression());
		Reactor.Instance().getInterpreter().addPattern(new OnceExpression()); 
		Reactor.Instance().getInterpreter().addPattern(new DelayExpression()); 
		Reactor.Instance().getInterpreter().addPattern(new TickExpression()); 
		TestHandler check = new TestHandler();
		Handler[] handler = {check};
		Context context = Reactor.Instance().getInterpreter().interpret(new Context(new StringBuffer("once with delay of 3 seconds and every tick"), new Branch(ContainerExpression.TYPE)));
		
		
		Reactor.Instance().getInterpreter().makeTrigger(context.currentNode, handler);
		Reactor.Instance().tick();
		Assert.assertFalse(check.called);
		long end = System.currentTimeMillis() + 5000;
		while ((System.currentTimeMillis() - end) < 0) {
			Reactor.Instance().tick();
		}
		
		Assert.assertTrue(check.called);	
		
		TestHandler check2 = new TestHandler();
		Handler[] handler2 = {check2};
		context = Reactor.Instance().getInterpreter().interpret(new Context(new StringBuffer("once with delay of 3 seconds or once with delay of 5 seconds"), new Branch(ContainerExpression.TYPE)));
		
		Reactor.Instance().getInterpreter().makeTrigger(context.currentNode, handler2);		
		end = System.currentTimeMillis() + 6000;
		while ((System.currentTimeMillis() - end) < 0) {
			Reactor.Instance().tick();
		}
		
		Assert.assertTrue(check2.called);
		Assert.assertEquals(check2.timesCalled, 2);
	}
	
	public void testUntil() {
		Reactor.Instance().getInterpreter().addPattern(new ContainerExpression());
		Reactor.Instance().getInterpreter().addPattern(new EveryExpression());
		Reactor.Instance().getInterpreter().addPattern(new OrExpression());
		Reactor.Instance().getInterpreter().addPattern(new AndExpression());
		Reactor.Instance().getInterpreter().addPattern(new OnceExpression()); 
		Reactor.Instance().getInterpreter().addPattern(new DelayExpression()); 
		Reactor.Instance().getInterpreter().addPattern(new TickExpression()); 
		Reactor.Instance().getInterpreter().addPattern(new UntilExpression());
		TestHandler check = new TestHandler();
		Handler[] handler = {check};
		Context context = Reactor.Instance().getInterpreter().interpret(new Context(new StringBuffer("every 5 seconds until 10 seconds"), new Branch(ContainerExpression.TYPE)));

		Reactor.Instance().getInterpreter().makeTrigger(context.currentNode, handler);
		Reactor.Instance().tick();
		try {
			Thread.currentThread().sleep(12000);
		} catch(InterruptedException e) {Assert.fail("Main thread was interrputed");}
		
		Assert.assertTrue(check.called);
		System.out.println("=======================----------------=====================");
	}
	
	public class TestHandler implements Handler {
		boolean called = false;
		int timesCalled = 0;
		public Object execute() {
			System.out.println("Test Handler Called!!");
			called = true;
			timesCalled++;
			return null;
		}
	}
}