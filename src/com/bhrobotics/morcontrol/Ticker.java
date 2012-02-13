package com.bhrobotics.morcontrol;

import java.util.Enumeration;
import java.util.Vector;

import com.bhrobotics.morcontrol.util.OperatingSystem;

import edu.wpi.first.wpilibj.DriverStation;

public class Ticker {
	private static Ticker ticker = null;
	private TickThread tickThread = new TickThread();
	private NewDataThread newDataThread = new NewDataThread();
	private Vector tickables = new Vector();
	private boolean running = false;
	
	private Ticker() {
	}
	
	public static Ticker getInstance() {
		if(ticker == null) {
			ticker = new Ticker();
		}
		return ticker;
	}
	
	public void registerTickable(Tickable tickable) {
		tickables.addElement(tickable);
	}
	
	public void unregisterTickable(Tickable tickable) {
		tickables.removeElement(tickable);
	}
	
	public void start() {
		if (!running) {
			running = true;
			
			Enumeration e = tickables.elements();
			while (e.hasMoreElements()) {
				Tickable tickable = (Tickable) e.nextElement();
				tickable.start();
			}
			
			tickThread.start();
			newDataThread.start();
		}
	}
	
	public void forceTick() {
		Enumeration e = tickables.elements();
		while (e.hasMoreElements()) {
			Tickable tickable = (Tickable) e.nextElement();
			tickable.tick();
		}
	}
	
	public void forceNewData() {
		Enumeration e = tickables.elements();
		while (e.hasMoreElements()) {
			Tickable tickable = (Tickable) e.nextElement();
			tickable.newData();
		}
	}
	
	public void stop() {
		if (running) {
			Enumeration e = tickables.elements();
			while (e.hasMoreElements()) {
				Tickable tickable = (Tickable) e.nextElement();
				tickable.stop();
			}

			running = false;
		}
	}
	
	public void kill() {
		newDataThread.interrupt();
		tickThread.interrupt();
		ticker = null;
	}
	
	private class NewDataThread extends Thread {
		private DriverStation ds;
		
		public NewDataThread() {
			if (OperatingSystem.isCRio()) {
				ds = DriverStation.getInstance();
			}
		}
		
		public void run() {
			while (running) {
				if (OperatingSystem.isCRio()) {
					ds.waitForData(500);
					if (ds.isNewControlData()) {
						forceNewData();
					}
				} else {
					forceNewData();
				}
			}
		}
	}
	
	private class TickThread extends Thread {
		public void run() {
			while (running) {
				forceTick();
			}
		}
	}

	public Vector getTickables() {
		return tickables;
	}
	
	public boolean isRunning() {
		return running;
	}
}