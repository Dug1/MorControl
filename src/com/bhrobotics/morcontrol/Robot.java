package com.bhrobotics.morcontrol;

public abstract class Robot {
	public static class Mode {
		private static final int DISABLED_VALUE = 0;
		private static final int AUTONOMOUS_VALUE = 1;
		private static final int OPERATOR_CONTROL_VALUE = 2;
		
		public static final Mode DISABLED = new Mode(DISABLED_VALUE);
		public static final Mode AUTONOMOUS = new Mode(AUTONOMOUS_VALUE);
		public static final Mode OPERATOR_CONTROL = new Mode(OPERATOR_CONTROL_VALUE);
		
		private int value;
		
		private Mode(int value) {
			this.value = value;
		}
		
		public int toInt() {
			return value;
		}
		
		public boolean equals(Object other) {
			if (other instanceof Mode) {
				Mode otherMode = (Mode) other;
				return value == otherMode.toInt();
			} else {
				return false;
			}
		}
	}
	
	private Mode mode;
	
	public Robot() {
		switchMode(Mode.DISABLED);
	}
	
	public Mode getMode() {
		return mode;
	}
		
	public void switchMode(Mode mode) {
		if (mode == null) {
			throw new NullPointerException("Mode cannot be null.");
		}
		
		if (!mode.equals(this.mode)) {
			stopCurrentMode();
			this.mode = mode;
			startCurrentMode();
		}
	}
	
	private void startCurrentMode() {
		if (this.mode == null) {
			return;
		}
		
		if (this.mode.equals(Mode.DISABLED)) {
			startDisabled();
		} else if (this.mode.equals(Mode.AUTONOMOUS)) {
			startAutonomous();
		} else if (this.mode.equals(Mode.OPERATOR_CONTROL)) {
			startOperatorControl();
		}
	}
	
	private void stopCurrentMode() {
		if (this.mode == null) {
			return;
		}
		
		if (this.mode.equals(Mode.DISABLED)) {
			stopDisabled();
		} else if (this.mode.equals(Mode.AUTONOMOUS)) {
			stopAutonomous();
		} else if (this.mode.equals(Mode.OPERATOR_CONTROL)) {
			stopOperatorControl();
		}
	}
	
	public void startDisabled() {
	}
	
	public void stopDisabled() {
	}

	public void startOperatorControl() {
	}
	
	public void stopOperatorControl() {
	}
	
	public void startAutonomous() {
	}
	
	public void stopAutonomous() {
	}
}