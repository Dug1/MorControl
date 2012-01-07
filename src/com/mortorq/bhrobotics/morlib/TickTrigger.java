package com.mortorq.bhrobotics.morlib;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class TickTrigger implements Trigger {
	private long delay;
	
	public TickTrigger(long d, TimeUnit t) {
		delay = TimeUnit.MILLISECONDS.convert(d, t);
	}	
	
	public void remove() {
		Reactor.Instance().getList().tryRemove(this);
	}
	
	public boolean isTriggered() {
		return true;
	}
	
	public Object getConfig() {
		return new Config(delay, false);
	}
}