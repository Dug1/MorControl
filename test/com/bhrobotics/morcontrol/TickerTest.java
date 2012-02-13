package com.bhrobotics.morcontrol;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

public class TickerTest extends MockObjectTestCase {
	private Ticker ticker = Ticker.getInstance();
	
	public void testForceTickAndNewData() {
		Mock mockTickable = mock(Tickable.class);
		mockTickable.expects(once()).method("tick");
		mockTickable.expects(once()).method("newData");
		Tickable tickable = (Tickable) mockTickable.proxy();
		
		ticker.registerTickable(tickable);
		ticker.forceTick();
		ticker.forceNewData();
		ticker.unregisterTickable(tickable);
		ticker.forceTick();
		ticker.forceNewData();
	}
	
	public void testStartAndStop() throws InterruptedException {
		Mock mockTickable = mock(Tickable.class);
		mockTickable.expects(once()).method("start");
		mockTickable.expects(atLeastOnce()).method("tick");
		mockTickable.expects(atLeastOnce()).method("newData");
		mockTickable.expects(once()).method("stop");
		Tickable tickable = (Tickable) mockTickable.proxy();
		
		ticker.registerTickable(tickable);
		ticker.start();
		Thread.sleep(10);
		ticker.stop();
	}
	
	public void testForceTick() {
		Mock mockTickable = mock(Tickable.class);
		mockTickable.expects(atLeastOnce()).method("tick");
		mockTickable.expects(never()).method("newData");
		Tickable tickable = (Tickable)mockTickable.proxy();
		Ticker.getInstance().registerTickable(tickable);
		
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() < start + 5000) {
			Ticker.getInstance().forceTick();
		}
	}
}
