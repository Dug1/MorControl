package com.mortorq.bhrobotics.morlib;

import jregex.Matcher;
import jregex.Pattern;

public class EveryExpression implements Expression{
	private String matchRegex = "every ([0-9]+) (millisecond|second)[s]{0,1}";
	
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
		Leaf everyLeaf = new Leaf("Every", matchMaker.group(1) + " " + matchMaker.group(2));
		tree.addNode(everyLeaf);
		return new Context(buffer, tree);
	}

	public void clean() {
	}
}