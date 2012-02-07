package com.bhrobotics.morcontrol.event;

public interface Tickable {
	public void start();
	public void stop();
	public void tick();
}