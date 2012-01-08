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
	private TriggerRegistry list;
	private Interpreter interpreter;
	
	private Reactor(int pool) {
		poolSize = pool;
		interpreter = new Interpreter();
		interpreter.addPattern(new ContainerExpression());
		interpreter.addPattern(new EveryExpression());
		interpreter.addPattern(new OrExpression());
		interpreter.addPattern(new AndExpression());
		interpreter.addPattern(new OnceExpression()); 
		interpreter.addPattern(new DelayExpression()); 
		interpreter.addPattern(new TickExpression()); 
		interpreter.addPattern(new UntilExpression());
	}
	
	public static Reactor Instance() {
		if(reactor == null) {
			reactor = new Reactor(5);
		}
		return reactor;
	}
	
	private Runnable createRunnable(Handler h) {
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
	
	private Callable createCallable(Handler h) {
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
	
	private ScheduledFuture schedule(Handler h, long delay) {
		return threadPool.schedule(createRunnable(h),delay,TimeUnit.MILLISECONDS);
	}
	
	private ScheduledFuture scheduleInterval(Handler h, long delay, long period) {
		return threadPool.scheduleAtFixedRate(createRunnable(h),delay,period,TimeUnit.MILLISECONDS);
	}
	
	private ScheduledFuture scheduleAwaitedInterval(Handler h, long delay, long period) {
		return threadPool.scheduleWithFixedDelay(createRunnable(h),delay,period,TimeUnit.MILLISECONDS);
	}
	
	private ScheduledFuture scheduleCallable(Handler h, long delay) {
		return threadPool.schedule(createCallable(h),delay,TimeUnit.MILLISECONDS);
	}
	
	public TriggerRegistry getList() {
		return list;
	}	
	
	public Interpreter getInterpreter() {
		return interpreter;
	}
	
	public void start() {
		threadPool = Executors.newScheduledThreadPool(poolSize);
		list = new TriggerRegistry();
	}
	
	public void stop() {
		threadPool.shutdown();
		list.clear();
	}
	
	public void tick() {
		list.tick();
	} 
	
	public ScheduledFuture submit(Handler h, Object o) {
		if (o instanceof PeriodicConfig) {
			PeriodicConfig p = (PeriodicConfig) o;
			if (p.awaitsPrevious()) {
				return scheduleAwaitedInterval(h, p.getDelay(), p.getPeriod());
			} else {
				return scheduleInterval(h, p.getDelay(), p.getPeriod());
			}
		} else if(o instanceof Config) {
			Config c = (Config)o; 
			if (c.isCallable()) {
				return scheduleCallable(h, c.getDelay());
			} else {
				return schedule(h, c.getDelay());
			}
		}	
		return null;
	}
	
	public FutureReference register(Trigger trigger, Handler[] handlers) {
		return list.register(trigger, handlers);
	}	
	
	public FutureReference register(Trigger trigger, Handler handler) {
		return list.register(trigger, handler);
	}
	
	public FutureReference compile(String interpretable, Handler[] handlers) throws ParseException, TriggerException{ 
		return interpreter.compile(interpretable, handlers);
	}
	
	public FutureReference compile(String interpretable, Handler handler) throws ParseException, TriggerException{
		Handler[] h = {handler};
		return compile(interpretable, h);
	}
}