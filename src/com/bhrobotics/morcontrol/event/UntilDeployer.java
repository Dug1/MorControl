package com.bhrobotics.morcontrol.event;

public class UntilDeployer extends Timer implements Deployer {
	private long end;
	private Handler canceler;
	
	public UntilDeployer(long after, Deployer deployer) {
		end = after + System.currentTimeMillis();
		class CancelHandler implements Handler {
			Deployer subject;
			
			CancelHandler(Deployer deployer) {
				subject = deployer;
			}

			public void execute(Event event) {
				Reactor.getInstance().removeDeployer(subject);
			}
		}
		canceler = new CancelHandler(deployer);
		start();
	}

	public boolean matches(Event event) {
		return event.fetch(Event.NAME).equals(this.toString());
	}

	public Handler[] getHandlers() {
		Handler[] handlers = {canceler};
		return handlers;
	}

	public void tick() {
		if (System.currentTimeMillis() > end) {
			Event event = new EventBuilder(this.toString()).build();
			Reactor.getInstance().addEvent(event);
			stop();
		}
	} 
}