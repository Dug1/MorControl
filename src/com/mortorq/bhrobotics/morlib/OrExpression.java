package com.mortorq.bhrobotics.morlib;

import jregex.Pattern;
import jregex.Matcher;

public class OrExpression implements Expression {
	private String matchRegex = "or";
	
	public boolean matches(String token) {
		Pattern pattern = new Pattern(token); 
		return pattern.matcher(matchRegex).matchesPrefix();
	}

	public Context parse(StringBuffer buffer, Node tree) {
		Pattern pattern = new Pattern(matchRegex); 
		Matcher matchMaker = pattern.matcher(buffer.readOne());
		
		tree.setType("Or");
		matchMaker.find();
		buffer.replace((buffer.readOne().substring(matchMaker.end())).trim());
		return new Context(buffer, tree);
	}

	public void clean() {
	}
}