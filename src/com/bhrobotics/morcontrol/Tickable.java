package com.bhrobotics.morcontrol;

public interface Tickable {
	public void start();
	public void stop();
	public void tick();
	public void newData();
}
