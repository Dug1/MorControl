package com.mortorq.bhrobotics.morlib;

public class UnknownKeyException extends RuntimeException {
	private static final long serialVersionUID = 8884191860039896176L;
	private String key;
	
	public UnknownKeyException(String key) {
		super("Unknown key " + key + ".");
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}