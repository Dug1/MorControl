package com.bhrobotics.morcontrol.util;

public class OperatingSystem {
	public static String getName() {
		return System.getProperty("os.name");
	}
	
	public static boolean isCRio() {
		return getName().startsWith("VxWorks");
	}
}
