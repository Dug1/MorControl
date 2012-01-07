package com.mortorq.bhrobotics.morlib;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class AndTrigger implements Trigger {
	private boolean[] flags;
	private long delay;
	
	public AndTrigger(boolean[] f, long d, TimeUnit t) {
		flags = f;
		delay = TimeUnit.MILLISECONDS.convert(d, t);
	}
	
	public Object getConfig() {
		return new Config(delay, false);
	}
	
	public void remove() {
		Reactor.Instance().getList().tryRemove(this);
	}
	
	public boolean isTriggered() {
		boolean allDone = true;
		for(int i = 0; i < flags.length; i++) {
			if(!flags[i]) {
				allDone = false;
			}
			flags[i] = false;
		}
		return allDone;
	}
}