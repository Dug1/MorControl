package com.mortorq.bhrobotics.morlib;

public interface Trigger {
	public boolean isTriggered();
	public Object getConfig();
	public void remove();
}