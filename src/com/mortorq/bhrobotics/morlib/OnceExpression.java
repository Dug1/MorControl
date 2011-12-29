package com.mortorq.bhrobotics.morlib;

import jregex.Matcher;
import jregex.Pattern;

public class OnceExpression implements Expression{
	private String matchRegex = "once";
	
	public boolean matches(String token) {
		Pattern pattern = new Pattern(matchRegex);
		Matcher matchMaker = pattern.matcher(token);
		return (matchMaker.find()) && (matchMaker.start() == 0);
	}

	public Context parse(StringBuffer buffer, Node tree) {
		Pattern pattern = new Pattern(matchRegex);
		Matcher matchMaker = pattern.matcher(buffer.readOne());
		matchMaker.find();
		buffer.replace((buffer.readOne().substring(matchMaker.end())).trim());
		Leaf everyLeaf = new Leaf("Once", "");
		tree.addNode(everyLeaf);
		return new Context(buffer, tree);
	}

	public void clean() {
	}
}