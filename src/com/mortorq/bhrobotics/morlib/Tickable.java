package com.mortorq.bhrobotics.morlib;

public interface Tickable {
	public void start();
	public void stop();
	public void tick();
}