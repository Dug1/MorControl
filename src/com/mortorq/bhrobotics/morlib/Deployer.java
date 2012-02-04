package com.mortorq.bhrobotics.morlib;

import java.util.Vector;

public interface Deployer {
	public boolean matches(Event event);
	public Vector getHandlers();
}
