package com.mortorq.bhrobotics.morlib;

import junit.framework.*;
import edu.emory.mathcs.backport.java.util.concurrent.ScheduledFuture;
import java.util.Enumeration;

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
		Assert.assertNotNull(Reactor.Instance().getList());
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
	
	public void testSubmit() {
		HandlerExample oppenheimer = new HandlerExample("Oppenheimer","blow things up"); 
		HandlerExample fermi = new HandlerExample("Fermi","create a reactor"); 
		HandlerExample curie = new HandlerExample("Curie","make thing glow"); 
		HandlerExample heisenberg = new HandlerExample("Heisenberg","muddle with matrices"); 
		CallableTriggerExample calltrigger = new CallableTriggerExample();
		PeriodicTriggerExample periodtrigger = new PeriodicTriggerExample();
		AwaitedTriggerExample awaitedtrigger = new AwaitedTriggerExample();
		NonTermTriggerExample trigger = new NonTermTriggerExample();
		Thread thread = Thread.currentThread();
		
		Reactor.Instance().submit(heisenberg,trigger.getConfig());
		ScheduledFuture f = Reactor.Instance().submit(curie,calltrigger.getConfig());
		ScheduledFuture p = Reactor.Instance().submit(fermi,periodtrigger.getConfig());
		ScheduledFuture a = Reactor.Instance().submit(oppenheimer,awaitedtrigger.getConfig());
		
		try {
			thread.sleep(4000);
		} catch(InterruptedException e) {Assert.fail("Main thread was interrputed");}
		p.cancel(false);
		a.cancel(false);
		
		try {
			Assert.assertTrue(heisenberg.isCalled);
			Assert.assertSame(curie.name, f.get());
			Assert.assertTrue(fermi.timesCalled >= 2);
			Assert.assertTrue(oppenheimer.timesCalled >= 2);
		} catch (Exception e) {Assert.fail("Execution was interrupted");}
	}
	
	public void testProcess() { 
		HandlerExample feynman = new HandlerExample("Feynam", "draw diagrams");
		HandlerExample gellmann = new HandlerExample("Gell-Mann", "name new flavors");
		Thread thread = Thread.currentThread();
		
		Reactor.Instance().register(new NonTermTriggerExample(), feynman);
		Reactor.Instance().register(new TriggerExample(), gellmann);
		
		Assert.assertEquals(2,Reactor.Instance().getList().getSize());
		
		Reactor.Instance().tick();
		Reactor.Instance().tick();
		try {
			thread.sleep(1000);
		} catch(InterruptedException e) {Assert.fail("Main thread was interrputed");}
		
		Assert.assertTrue(gellmann.isCalled);
		Assert.assertTrue(feynman.isCalled);
		Assert.assertEquals(1,Reactor.Instance().getList().getSize());
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
	
	public class TriggerExample implements Trigger {
		public boolean markedForRemoval = false;
		
		public boolean isTriggered() {
			if (markedForRemoval) {
				tryRemove();
				return false;
			} 
			markedForRemoval = true;
			return true;
		}
		
		public Object getConfig() {
			return new Config((long)0, false);
		}
		
		public void tryRemove() {
			Reactor.Instance().getList().tryRemove(this);
		}
	}
	
	public class NonTermTriggerExample implements Trigger {
		public boolean isTriggered() {
			return true;
		}
		public Object getConfig() {
			return new Config((long)0, false);
		} 
	}
	
	public class PeriodicTriggerExample implements Trigger {
		public int times = 0;
		public boolean markedForRemoval = false;
		
		public boolean isTriggered() {
			if (markedForRemoval) {
				tryRemove();
				return false;
			} 
			markedForRemoval = true;
			return true;
		}
		public Object getConfig() {
			return new PeriodicConfig((long)0, (long)500, false);
		}
		
		public void tryRemove() {
			Reactor.Instance().getList().tryRemove(this);
		}
	}
	
	public class CallableTriggerExample implements Trigger {
		public boolean markedForRemoval = false;
		
		public boolean isTriggered() {
			if (markedForRemoval) {
				tryRemove();
				return false;
			} 
			markedForRemoval = true;
			return true;
		}
		public Object getConfig() {
			return new Config((long)0, true);
		}
		
		public void tryRemove() {
			Reactor.Instance().getList().tryRemove(this);
		}
	}
	
	public class AwaitedTriggerExample implements Trigger {
		public boolean markedForRemoval = false;
		
		public boolean isTriggered() {
			if (markedForRemoval) {
				tryRemove();
				return false;
			} 
			markedForRemoval = true;
			return true;
		}
		public Object getConfig() {
			return new PeriodicConfig((long)0, (long)500,true);
		}
		
		public void tryRemove() {
			Reactor.Instance().getList().tryRemove(this);
		}
	}
}	