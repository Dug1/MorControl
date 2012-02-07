package com.bhrobotics.morcontrol.event;

import com.sun.squawk.util.StringTokenizer;

import jregex.Matcher;

public class GenericExpression extends Expression {
	public static final String ATTRIBUTES = "attributes";
	public static final String TARGET = "target";
	
	public GenericExpression() {
		super("Event ([a-zA-Z]+)( with attributes(( [\\S]+)+))? called");
	}

	public boolean matchesNode(Node node) {
		return node instanceof GenericNode;
	}

	public Context parse(StringBuffer buffer, Node tree) {
		String token = buffer.readOne();
		Matcher matcher = getMatcher(token);
		String name = matcher.group(1).trim();
		String attributes = matcher.group(3).trim();
		
		buffer.replace(token.substring(matcher.end()));
		
		Node genericNode = new GenericNode();
		genericNode.putData(TARGET, name);
		genericNode.putData(ATTRIBUTES, attributes);
		tree.addChild(genericNode);
		
		return new Context(buffer, tree);
	}

	public void clean() {
	}
	
	public static class GenericNode extends Leaf {
		public Deployer register(Handler[] handlers) {
			StringTokenizer tokenizer = new StringTokenizer((String)getData(ATTRIBUTES));
			String target = (String)getData(TARGET);
			String[] attributes = new String[tokenizer.countTokens()];
			for (int i = 0; tokenizer.hasMoreElements(); i++) {
				attributes[i] = tokenizer.nextToken(); 
			}
			GenericDeployer deployer = new GenericDeployer(target, attributes, handlers);
			Reactor.getInstance().addDeployer(deployer);
			return deployer;
		}
	}
}