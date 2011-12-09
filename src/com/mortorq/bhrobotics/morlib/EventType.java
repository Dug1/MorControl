package com.mortorq.bhrobotics.morlib;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class EventType {
	public final static int CALLABLE_HANDLER = 42;
	public final static int RUNNABLE_HANDLER = 56;
	
	private long delay;
	private long period;
	private boolean periodic;
	private boolean awaits;
	private int handler;
	private TimeUnit unit;
	
	private EventType(long d, long p, boolean pc, TimeUnit u, boolean w,int h) {
		delay = d;
		period = p;
		periodic = pc;
		unit = u;
		awaits = w;
		handler = h;
	}
	
	public long getDelay() {
		return delay;
	}
	
	public long getPeriod() {
		return period;
	}
	
	public boolean getIsPeriodic() {
		return periodic;
	}
	
	public TimeUnit getTimeUnit() {
		return unit;
	}
	
	public boolean getAwaitsTermination() {
		return awaits;
	}
	
	public int getHandlerType() {
		return handler;
	}
	
	public static class Builder {
		EventType config;
		
		public Builder() {
			config = new EventType(0,0,false,TimeUnit.SECONDS,false,RUNNABLE_HANDLER);
		}
		
		public Builder(EventType c) {
			config = c;
		}
		
		public Builder isNotDelayed() {
			config.delay = 0;
			return this;
		}
		
		public Builder isDelayedBy(long d, TimeUnit u) {
			config.delay = d;
			config.period = u.convert(config.period, config.unit);
			config.unit = u;
			return this;
		}
		
		public Builder executesOnce() {
			config.periodic = false;
			return this;
		}
		
		public Builder executesEvery(long p, TimeUnit u) {
			config.period = p;
			config.periodic = true;
			config.delay = u.convert(config.delay, config.unit);
			config.unit= u;
			return this;
		}
		
		public Builder awaitsPrevious(boolean w) {
			if (config.periodic) {
				config.awaits = w;
			}
			return this;
		}
		
		public Builder hasHandlerType(int t) {
			config.handler = t;
			return this;
		}
		
		public EventType build() {
			return config;
		}
	}
}
