package com.mortorq.bhrobotics.morlib;

public abstract class Emitter implements Tickable {
	
	protected void emit(Event event) {
		Reactor.getInstance().addEvent(event);
	}
	
	public void start() {
		Reactor.getInstance().addTickable(this);
	}
	
	public void stop() {
		Reactor.getInstance().removeTickable(this);
	}
}
