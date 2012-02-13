package com.bhrobotics.morcontrol;

import com.bhrobotics.morcontrol.event.Handler;

public class Condition {
	private String timing;
	private Handler[] handlers;
	
	public Condition(String string, Handler[] handler) {
		setTiming(string);
		setHandlers(handler);
	}

	public Handler[] getHandlers() {
		return handlers;
	}

	public void setHandlers(Handler[] handlers) {
		this.handlers = handlers;
	}

	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}
}
