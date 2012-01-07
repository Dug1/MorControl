package com.mortorq.bhrobotics.morlib;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class OnceTrigger implements Trigger {
	private boolean markedForDestruction = false;
	private long delay;
	
	public OnceTrigger(long d, TimeUnit t) {
		delay = TimeUnit.MILLISECONDS.convert(d, t);
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
		return new Config(delay, false);
	}
}