package com.bhrobotics.morcontrol;

import junit.framework.TestCase;

public class RobotTest extends TestCase {
	private RobotStub robot = new RobotStub();
	
	public void testMode() {
		Robot.Mode disabled = Robot.Mode.DISABLED;
		Robot.Mode autonomous = Robot.Mode.AUTONOMOUS;
		Robot.Mode operatorControl = Robot.Mode.OPERATOR_CONTROL;
		
		assertEquals(disabled, disabled);
		assertEquals(autonomous, autonomous);
		assertEquals(operatorControl, operatorControl);
		assertFalse(disabled.equals(autonomous));
		assertFalse(disabled.equals(operatorControl));
		assertFalse(autonomous.equals(operatorControl));
	}
	
	public void testSwitchMode() {
		assertEquals(Robot.Mode.DISABLED, robot.getMode());
		assertEquals(1, robot.getStartDisabledCalls());
		assertEquals(0, robot.getStopDisabledCalls());
		assertEquals(0, robot.getStartAutonomousCalls());
		assertEquals(0, robot.getStopAutonomousCalls());
		assertEquals(0, robot.getStartOperatorControlCalls());
		assertEquals(0, robot.getStopOperatorControlCalls());
		
		robot.switchMode(Robot.Mode.AUTONOMOUS);
		assertEquals(1, robot.getStartDisabledCalls());
		assertEquals(1, robot.getStopDisabledCalls());
		assertEquals(1, robot.getStartAutonomousCalls());
		assertEquals(0, robot.getStopAutonomousCalls());
		assertEquals(0, robot.getStartOperatorControlCalls());
		assertEquals(0, robot.getStopOperatorControlCalls());
		
		robot.switchMode(Robot.Mode.OPERATOR_CONTROL);
		assertEquals(1, robot.getStartDisabledCalls());
		assertEquals(1, robot.getStopDisabledCalls());
		assertEquals(1, robot.getStartAutonomousCalls());
		assertEquals(1, robot.getStopAutonomousCalls());
		assertEquals(1, robot.getStartOperatorControlCalls());
		assertEquals(0, robot.getStopOperatorControlCalls());

		robot.switchMode(Robot.Mode.OPERATOR_CONTROL);
		assertEquals(1, robot.getStartDisabledCalls());
		assertEquals(1, robot.getStopDisabledCalls());
		assertEquals(1, robot.getStartAutonomousCalls());
		assertEquals(1, robot.getStopAutonomousCalls());
		assertEquals(1, robot.getStartOperatorControlCalls());
		assertEquals(0, robot.getStopOperatorControlCalls());
		
		robot.switchMode(Robot.Mode.DISABLED);
		assertEquals(2, robot.getStartDisabledCalls());
		assertEquals(1, robot.getStopDisabledCalls());
		assertEquals(1, robot.getStartAutonomousCalls());
		assertEquals(1, robot.getStopAutonomousCalls());
		assertEquals(1, robot.getStartOperatorControlCalls());
		assertEquals(1, robot.getStopOperatorControlCalls());
	}
}
