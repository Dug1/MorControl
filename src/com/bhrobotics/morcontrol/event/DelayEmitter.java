package com.bhrobotics.morcontrol.event;

public class DelayEmitter extends Timer {
	private long endTime;
	private String target;
	
	public DelayEmitter(long period, String target) {
		endTime = System.currentTimeMillis() + period;
		this.target = target;
	}
	
	public void tick() {
		if(System.currentTimeMillis() > endTime) {
			Event event = new EventBuilder(target).build();
			Reactor.getInstance().addEvent(event);
			stop();
		}
	}
}