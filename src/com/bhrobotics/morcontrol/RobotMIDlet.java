package com.bhrobotics.morcontrol;

import edu.wpi.first.wpilibj.IterativeRobot;

public class RobotMIDlet extends IterativeRobot {
	private Robot robot;
	
    public void robotInit() {
    	/*try {
        	InputStream stream = getClass().getResourceAsStream("/RobotClass");
	    	byte[] klassBytes = new byte[256];
			int length = stream.read(klassBytes);
			String klassName = new String(klassBytes, 0, length);
			Class klass = Class.forName(klassName);
			
			robot = (Robot) klass.newInstance();
		} catch (IOException e) {
			throw new RobotInitializationException("Could not read robot class file.");
		} catch (InstantiationException e) {
			throw new RobotInitializationException("Could not instantiate robot class.");
		} catch (IllegalAccessException e) {
			throw new RobotInitializationException("Could not instantiate robot class.");
		} catch (ClassNotFoundException e) {
			throw new RobotInitializationException("Could not find robot class.");
		}*/
    	robot = new MorBot();
    	System.out.println("Deployed!");
    }
    
    public void disabledInit() {
    	robot.switchMode(Robot.Mode.DISABLED);
    }
    
    public void autonomousInit() {
    	robot.switchMode(Robot.Mode.AUTONOMOUS);
    }
    
    public void teleopInit() {
    	robot.switchMode(Robot.Mode.OPERATOR_CONTROL);
    }
}