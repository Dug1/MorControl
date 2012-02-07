package com.bhrobotics.morcontrol.event;

import jregex.Matcher;

public class DelayExpression extends Expression {
	
	public DelayExpression() {
		super("with delay of ([0-9]+) (second|millisecond)[s]{0,1}");
	}
	
	public Context parse(StringBuffer buffer, Node tree) {
		Node[] children = tree.getChildren();
		Matcher matchMaker = getMatcher(buffer.readOne());
		
		buffer.replace(buffer.readOne().substring(matchMaker.end()).trim());
		Node delayNode = new DelayNode();
		Node delayedNode = children[children.length-1];
		delayNode.putData(Interpreter.DELAY, matchMaker.group(1));
		delayNode.putData(Interpreter.DELAY_UNIT, matchMaker.group(1).toUpperCase() + "S");
		delayNode.addChild(delayedNode);
		tree.removeChild(delayedNode);
		tree.addChild(delayNode);
		
		return new Context(buffer, tree);
	}
	
	public void clean() {
	}

	public boolean matchesNode(Node node) {
		return node instanceof DelayNode;
	}

	public static class DelayNode extends Branch {

		public Deployer register(Handler[] handlers) {
			Node childNode = getChildren()[0];
			String timeUnit = getData(Interpreter.DELAY_UNIT);
			long period = Long.parseLong(getData(Interpreter.DELAY)); 
			if(timeUnit.equalsIgnoreCase("SECONDS")) {
				period *= 1000;
			}
			
			class DelayHandler implements Handler {
				String subject;
				long period;
				
				DelayHandler(String subject, long period) {
					this.subject = subject;
					this.period = period;
				}
				
				public void execute(Event event) {
					DelayEmitter emitter = new DelayEmitter(period,subject);
					emitter.start();
				}
			}
			
			Deployer delayDeployer = new DelayDeployer(handlers);
			Handler delayHandler = new DelayHandler(delayDeployer.toString(), period);
			Handler[] handlerArray = {delayHandler};
			childNode.register(handlerArray);
			
			Reactor.getInstance().addDeployer(delayDeployer);

			return delayDeployer;
		}	
	}
}