package com.bhrobotics.morcontrol.event;

public class PeriodicConfig extends Config {
	private long period;
	private boolean awaits;
	
	public PeriodicConfig(long d, long p, boolean a) {
		super(d, false);
		period = p;
		awaits = a;
	}
	
	public long getPeriod() {
		return period;
	}
	
	public boolean awaitsPrevious() {
		return awaits;
	}
}