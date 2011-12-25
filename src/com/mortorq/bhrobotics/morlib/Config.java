package com.mortorq.bhrobotics.morlib;

public class Config {
	private long delay;
	private boolean callable;
	
	public Config(long d, boolean c) {
		delay = d;
		callable = c;
	}
		
	public long getDelay() {
		return delay;
	}
	
	public boolean isCallable() {
		return callable;
	}
}