package com.mortorq.bhrobotics.morlib;

import java.util.Enumeration;
import java.util.Vector;

public class DeployerRegistry {
	private Vector filters = new Vector();
	
	public void add(Deployer deployer) {
		filters.addElement(deployer);
	}
	
	public void remove(Deployer deployer) {
		filters.removeElement(deployer);
	}
	
	public Enumeration getElements() {
		return filters.elements();
	}
	
	public boolean hasFilter(Deployer deployer) {
		return filters.contains(deployer);
	}
 }
