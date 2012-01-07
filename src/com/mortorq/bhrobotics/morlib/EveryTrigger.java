package com.mortorq.bhrobotics.morlib;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class EveryTrigger implements Trigger {
	private boolean markedForDestruction = false;
	private long delay;
	private long period;
	
	public EveryTrigger(long d, TimeUnit u, long p, TimeUnit t) {
		delay = TimeUnit.MILLISECONDS.convert(d, u);
		period = TimeUnit.MILLISECONDS.convert(p, t);
	}	
	
	public void remove() {
		Reactor.Instance().getList().tryRemove(this);
	}
	
	public boolean isTriggered() {
		if (markedForDestruction) {
			remove();
			return false;
		}
		markedForDestruction = true;
		return true;
	}
	
	public Object getConfig() {
		return new PeriodicConfig(delay, period, false);
	}
}