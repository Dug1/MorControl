package com.bhrobotics.morcontrol.event;

import com.bhrobotics.morcontrol.Tickable;
import com.bhrobotics.morcontrol.Ticker;

public abstract class Emitter implements Tickable {
	public void start() {
		Ticker.getInstance().registerTickable(this);
	}
	
	public void stop() {
		Ticker.getInstance().unregisterTickable(this);
	}
	
	protected void emit(Event event) {
		Reactor.getInstance().addEvent(event);
	}
}
