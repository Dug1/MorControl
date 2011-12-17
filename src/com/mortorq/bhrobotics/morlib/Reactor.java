package com.mortorq.bhrobotics.morlib;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import edu.emory.mathcs.backport.java.util.concurrent.Callable;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import edu.emory.mathcs.backport.java.util.concurrent.ScheduledFuture;
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
			reactor = new Reactor(5);
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
	
	public Runnable createRunnable(Handler h) {
            class Task implements Runnable {
                Handler handler;
                Task(Handler h) {
                    handler = h;
                }
                    
                public void run() {
                    handler.execute();
                }
            }
            return new Task(h);
	}
	
	public Callable createCallable(Handler h) {
            class Task implements Callable {
                Handler handler;
                Task(Handler h) {
                    handler = h;
                }
                
                public Object call() {
                    return handler.execute();
                }
            }
            return new Task(h);
	}
	
	public ScheduledFuture schedule(Handler h, long delay) {
		return threadPool.schedule(createRunnable(h),delay,TimeUnit.MILLISECONDS);
	}
	
	public ScheduledFuture scheduleInterval(Handler h, long delay, long period) {
		return threadPool.scheduleAtFixedRate(createRunnable(h),delay,period,TimeUnit.MILLISECONDS);
	}
	
	public ScheduledFuture scheduleAwaitedInterval(Handler h, long delay, long period) {
		return threadPool.scheduleWithFixedDelay(createRunnable(h),delay,period,TimeUnit.MILLISECONDS);
	}
	
	public ScheduledFuture scheduleCallable(Handler h, long delay) {
		return threadPool.schedule(createCallable(h),delay,TimeUnit.MILLISECONDS);
	}
}