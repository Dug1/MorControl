package com.mortorq.bhrobotics.morlib;

public interface Pattern {
	public boolean matches();
	public EventType parse();
}