package com.mortorq.bhrobotics.morlib;

import junit.framework.*;
import edu.emory.mathcs.backport.java.util.concurrent.ScheduledFuture;

public class ReactorTest extends TestCase {
		
	protected void setUp() throws Exception {
		super.setUp();
		Reactor.Instance().start();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		Reactor.Instance().stop();
	}
	
	public void testInit() {
		Assert.assertNotNull(Reactor.Instance());
	}
	
	public void testSchedule() {
		HandlerExample bohr = new HandlerExample("Bohr","discover the mysteries of the atom"); 
		Thread thread = Thread.currentThread();
		
		Reactor.Instance().schedule(bohr,(long)0);
		try {
			thread.sleep(1000);
			} catch(InterruptedException e) {Assert.fail("Main thread was interrputed");}
		
		Assert.assertTrue(bohr.isCalled);
	}
	
	public void testScheduleInterval() {
		HandlerExample einstein = new HandlerExample("Einstein","unravel Newtonian physics");
		
		ScheduledFuture f = Reactor.Instance().scheduleInterval(einstein,(long)0,(long)500);
		try {
			Thread.sleep(4000);
			} catch(InterruptedException e) {Assert.fail("Main thread was interrputed");}
		f.cancel(false);
		
		Assert.assertTrue(einstein.timesCalled > 1);
	}
	
	public void testScheduleCallable() {
		HandlerExample schrödinger = new HandlerExample("Schrödinger","play with cats"); 
		Thread thread = Thread.currentThread();
		
		ScheduledFuture f = Reactor.Instance().scheduleCallable(schrödinger,(long)0);
		try {
			thread.sleep(1000);
		
			Assert.assertSame(schrödinger.name,f.get());
		} catch(Exception e) {Assert.fail("Main thread was interrputed");}
	}
	
	public class HandlerExample implements Handler{	
		public boolean isCalled = false; 
		public int timesCalled = 0;
		public String name;
		public String action;
		
		public HandlerExample(String n , String a) {
			name = n;
			action = a;
		}
		public Object execute() {
			System.out.println(name + " has been called to " + action + "!");
			isCalled = true;
			timesCalled++;
			return name;
		}
	}
}
	