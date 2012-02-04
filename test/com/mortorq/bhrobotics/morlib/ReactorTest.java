package com.mortorq.bhrobotics.morlib;

import junit.framework.*;

public class ReactorTest extends TestCase {
	private final String TYPE = "ReactorTest";
	private final String INFO = "Random Stuff";
	
	protected void setUp() throws Exception {
		super.setUp();
		Reactor.getInstance().start();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		Reactor.getInstance().stop();
	}
	
	public void testInit() {
		Assert.assertNotNull(Reactor.getInstance());
	}
	
	public void testProcess() { 
		String[] reqs = {INFO};
		HandlerExample handler = new HandlerExample();
		Event firstEvent = new EventBuilder(TYPE).with(INFO, "Schrodinger is pondering about a cat").build();
		Event secondEvent = new EventBuilder(TYPE).with(INFO, "Heinsburg is messing with matricies").build();
		GenericDeployer filter = new GenericDeployer(TYPE, reqs,handler);
		Reactor.getInstance().addDeployer(filter);
		
		Reactor.getInstance().addEvent(firstEvent);
		Reactor.getInstance().addEvent(secondEvent);
		Reactor.getInstance().tick();
		Reactor.getInstance().tick();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
		
		assertTrue(handler.isCalled);
		assertEquals(2, handler.timesCalled);
	}
	
	public void testEmitterReg() {
		final String key = "RAR";
		class TestEmitter extends Emitter {
			public void tick() {
				Event event = new EventBuilder(key).build();
				emit(event);
			}
		}
	
		Emitter emitter = new TestEmitter();
		HandlerExample handler = new HandlerExample();
		Deployer deployer = new GenericDeployer(key, handler);
		emitter.start();
		Reactor.getInstance().addDeployer(deployer);
		
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() < start + 1000) {
			Reactor.getInstance().tick();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				fail(e.getMessage());
			}
		}

		assertTrue(handler.isCalled);
	}
	
	public class HandlerExample implements Handler {	
		public boolean isCalled = false; 
		public int timesCalled = 0;
		
		public void execute(Event event) {
			isCalled = true;
			timesCalled++;
		}
	}
}		