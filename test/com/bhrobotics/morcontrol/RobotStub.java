package com.bhrobotics.morcontrol;

public class RobotStub extends Robot {
	private int startDisabledCalls;
	private int stopDisabledCalls;
	private int startAutonomousCalls;
	private int stopAutonomousCalls;
	private int startOperatorControlCalls;
	private int stopOperatorControlCalls;
	
	public RobotStub() {
		super();
	}
	
	public int getStartDisabledCalls() {
		return startDisabledCalls;
	}

	public int getStopDisabledCalls() {
		return stopDisabledCalls;
	}

	public int getStartAutonomousCalls() {
		return startAutonomousCalls;
	}

	public int getStopAutonomousCalls() {
		return stopAutonomousCalls;
	}

	public int getStartOperatorControlCalls() {
		return startOperatorControlCalls;
	}

	public int getStopOperatorControlCalls() {
		return stopOperatorControlCalls;
	}

	public void startDisabled() {
		startDisabledCalls++;
	}
	
	public void stopDisabled() {
		stopDisabledCalls++;
	}
	
	public void startAutonomous() {
		startAutonomousCalls++;
	}
	
	public void stopAutonomous() {
		stopAutonomousCalls++;
	}
	
	public void startOperatorControl() {
		startOperatorControlCalls++;
	}
	
	public void stopOperatorControl() {
		stopOperatorControlCalls++;
	}
}
