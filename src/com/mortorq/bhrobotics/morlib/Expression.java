package com.mortorq.bhrobotics.morlib;

import jregex.Matcher;
import jregex.Pattern;

public abstract class Expression {
	private Pattern pattern;
	
	protected Expression(String regex) {
		pattern = new Pattern(regex);
	}
	
	protected Matcher getMatcher(String argument) {
		Matcher matchMaker = pattern.matcher(argument);
		matchMaker.find();
		return matchMaker;
	}

	public boolean matches(String argument) {
		Matcher matchMaker = pattern.matcher(argument);
		return (matchMaker.find()) && (matchMaker.start() == 0);
	}
	
	public abstract boolean matchesNode(Node node);
	public abstract Context parse(StringBuffer buffer, Node tree);
	public abstract void clean();
}