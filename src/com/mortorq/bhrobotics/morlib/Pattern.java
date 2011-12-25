package com.mortorq.bhrobotics.morlib;

public interface Pattern {
	public boolean matches(String s);
	public Trigger parse(String s);
}