package com.mortorq.bhrobotics.morlib;

import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import edu.emory.mathcs.backport.java.util.concurrent.Future;
import edu.emory.mathcs.backport.java.util.concurrent.ScheduledExecutorService;

public class Reactor implements Tickable {
	private static Reactor reactor = null;
	private int poolSize;
	private ScheduledExecutorService threadPool;
	
	private Reactor(int pool) {
		poolSize = pool;
	}
	
	public static Reactor Instance() {
		if(reactor == null) {
			reactor = new Reactor(1);
		}
		return reactor;
	}
	
	public void start() {
		threadPool = Executors.newScheduledThreadPool(poolSize);
	}
	
	public void stop() {
		threadPool.shutdown();
	}
	
	public void tick() {
	} 
}