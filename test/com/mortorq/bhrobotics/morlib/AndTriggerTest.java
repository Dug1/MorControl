package com.mortorq.bhrobotics.morlib;

import junit.framework.*;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class AndTriggerTest extends TestCase {
	private AndTrigger patrolcus;
	
	protected void setUp() throws Exception {
		Reactor.Instance().start();
		super.setUp();
	}
	
	protected void tearDown() throws Exception {
		Reactor.Instance().stop();
		super.tearDown();
	}
	
	public void testInit() {
		patrolcus = new AndTrigger(new boolean[1], (long)500, TimeUnit.MILLISECONDS);
		Assert.assertNotNull(patrolcus);
	}
	
	public void testConfig() {
		Config config = (Config)patrolcus.getConfig();
		patrolcus = new AndTrigger(new boolean[1], (long)500, TimeUnit.MILLISECONDS);
		
		Assert.assertFalse(config.isCallable());
		Assert.assertEquals(500, config.getDelay());
	}
	
	public void testSubmit() {
		Trigger trigger1 = new TickTrigger((long)0, TimeUnit.MILLISECONDS);
		Trigger trigger2 = new OnceTrigger((long)0, TimeUnit.MILLISECONDS);
		class AlertHandler implements Handler {
			int index;
			boolean[] flags;
			AlertHandler(int i, boolean[] f) {
				index = i;
				flags = f;
			}
			
			public Object execute() {
				flags[index] = true;
				return null;
			}
		}
		boolean[] flags = new boolean[2];
		Handler[] alerts1 = {new AlertHandler(1, flags)};
		Handler[] alerts2 = {new AlertHandler(2, flags)};
		Reactor.Instance().register(trigger1, alerts1);
		Reactor.Instance().register(trigger2, alerts2);
		class TestHandler implements Handler {
			boolean alert = false;
			
			public Object execute() {
				System.out.println("TROY!!!");
				alert = true;
				return null;
			}
		}
		
		TestHandler handler = new TestHandler();
		Reactor.Instance().register(patrolcus, handler);
		long end = System.currentTimeMillis() + 3000;
		while ((System.currentTimeMillis() - end) < 0) {
			Reactor.Instance().tick();
		}
		
		Assert.assertTrue(handler.alert);
		Assert.assertEquals(2, Reactor.Instance().getList().getSize());
	}
}