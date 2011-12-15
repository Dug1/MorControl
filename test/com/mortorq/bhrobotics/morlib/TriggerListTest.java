/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mortorq.bhrobotics.morlib;

import junit.framework.*;
import java.util.Enumeration;

/**
 *
 * @author Daguan Lu
 */
public class TriggerListTest extends TestCase {
	TriggerList list;

    protected void setUp() throws Exception {
        super.setUp();
		list = new TriggerList();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }

	public void testInit() {
		Assert.assertNotNull(list);
	}
	
	public void testRegister() {
		list.register(new TriggerExample(), new HandlerExample("Strauss"));
		list.register(new TriggerExample(), new HandlerExample("Tchaikovsky"));
		
		Assert.assertTrue(list.getTriggers().hasMoreElements());
		Assert.assertEquals(list.getSize(),2);
	}
	
	public void testTick() {
		list.register(new TriggerExample(), new HandlerExample("Holst"));
		list.register(new NonTermTriggerExample(), new HandlerExample("Beethoven"));
		
		list.tick();
		
		Assert.assertEquals(list.getSize(),1);
	}
	
	public class HandlerExample implements Handler {
		String name;
		
		public HandlerExample(String s) {
			name = s;
		}
		
		public Object execute() {
			System.out.println(name + " composed a concerto in the key of Eb!!");
			return null;
		}
	}
	
	public class TriggerExample implements Trigger {
		public boolean isTriggered() {
			return true;
		}
		
		public boolean isFinished() {
			return true;
		}
	}
	
	public class NonTermTriggerExample implements Trigger {
		public boolean isTriggered() {
			return true;
		}
		
		public boolean isFinished() {
			return false;
		}
	}
}
