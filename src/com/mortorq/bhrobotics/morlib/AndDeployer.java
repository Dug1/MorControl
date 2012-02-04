package com.mortorq.bhrobotics.morlib;

import java.util.Vector;

public class AndDeployer implements Deployer {
	public static final String ID = "and ID";
	private Deployer[] children;
	private Handler[] handlers;
	private boolean[] flags;
	private boolean markedForDestruction = false;
	
	public AndDeployer(Handler handler, Node[] nodes) {
		Handler[] handlerArray = {handler};
		handlers = handlerArray;
		makeChildren(nodes);
	}
	
	public AndDeployer(Handler[] handlerArray, Node[] nodes) {
		handlers = handlerArray;
		makeChildren(nodes);
	}
	
	private void makeChildren(Node[] nodes) {
		children = new Deployer[nodes.length];
		flags = new boolean[nodes.length];
		class AndHandler implements Handler {
			int id;
			String target;
			
			AndHandler(String target, int index) {
				id = index;
				this.target = target;
			}
			
			public void execute(Event event) {
				Event newEvent = event.copy();
				newEvent.getData().put(AndDeployer.ID, new Integer(id));
				newEvent.getData().put(Event.NAME, target);
				Reactor.getInstance().addEvent(newEvent);
			}
		}
		for(int i = 0; i < nodes.length; i++) {
			Handler andHandler = new AndHandler(this.toString(), i);
			Handler[] handlerArray = {andHandler};
			children[i] = nodes[i].register(handlerArray); 
		}
	}
	
	private void checkChildren() {
		for(int i = 0; i < children.length; i++) {
			if(!Reactor.getInstance().getRegistry().hasFilter(children[i])) {
				markedForDestruction = true;
			}
		}
	}
	
	public boolean matches(Event event) {
		
		if(markedForDestruction) {
			for(int j = 0; j < children.length; j++) {
				Reactor.getInstance().getRegistry().remove(children[j]);
			}
			Reactor.getInstance().getRegistry().remove(this);
		}
		
		
		if (event.fetch(Event.NAME).equals(this.toString())) {
			flags[event.fetchAsInt(ID)] = true;
		}
		
		return false;
	}

	public Vector getHandlers() {
		Vector handlerVector = new Vector();
		for(int i = 0; i < handlers.length; i++) {
			handlerVector.addElement(handlers[i]);
		}
		return handlerVector;
	}
}