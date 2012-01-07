package com.mortorq.bhrobotics.morlib;

import edu.emory.mathcs.backport.java.util.concurrent.ScheduledFuture;

public class FutureReference {
	private ScheduledFuture[] futures = null;
	
	public void setFutures(ScheduledFuture[] fs) {
		futures = fs;
	}
	
	public ScheduledFuture[] getFutures() {
		return futures;
	}	
	
	public boolean isSubmitted() {
		return (futures != null);
	}
	
	public boolean isDone() {
		if(isSubmitted()) {
			for(int i = 0; i < futures.length; i++) {
				if (!futures[i].isDone()) {
					return false;
				}
			} 
			return true;
		}
		return false;
	}
	
	public void cancel() {
		if (isSubmitted()) {
			for(int i = 0; i < futures.length; i++) {
				futures[i].cancel(false);
			}
		}
	}
}