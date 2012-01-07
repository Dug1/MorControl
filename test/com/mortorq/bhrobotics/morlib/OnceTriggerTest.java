package com.mortorq.bhrobotics.morlib;

import junit.framework.*;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class OnceTriggerTest extends TestCase {
	private OnceTrigger achilles;
	
	protected void setUp() {
		 achilles = new OnceTrigger((long)0, TimeUnit.MILLISECONDS);
	}
	
	public void testInit() {
		Assert.assertNotNull(achilles);
	}
	
	public void testConfig() {
		Config config = (Config)achilles.getConfig();
		
		Assert.assertFalse(config.isCallable());
	}
	
	public void testSubmit() {
		Reactor.Instance().start();
		class TestHandler implements Handler {
			boolean alert = false;
			
			public Object execute() {
				System.out.println("TROY!!!");
				alert = true;
				return null;
			}
		}

		TestHandler handler = new TestHandler();
		Reactor.Instance().register(achilles, handler);
		Reactor.Instance().tick();
		Reactor.Instance().tick();
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {System.out.println("AAAAAAHHHHH!!!");}
		
		Assert.assertTrue(handler.alert);
		Assert.assertEquals(0,Reactor.Instance().getList().getSize());
	}
}