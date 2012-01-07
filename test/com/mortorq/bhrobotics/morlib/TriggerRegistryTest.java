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
public class TriggerRegistryTest extends TestCase {
	TriggerRegistry list;

    protected void setUp() throws Exception {
        super.setUp();
		list = new TriggerRegistry();
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
	
	public void tryRemove() {
		TriggerExample holst = new TriggerExample();
		list.register(holst, new HandlerExample("Holst"));
		
		list.tryRemove(holst);
		
		Assert.assertEquals(list.getSize(),0);
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
		private boolean markedForRemoval = false;
		
		public boolean isTriggered() {
			if (markedForRemoval) {
				remove();
				return false;
			} 
			markedForRemoval = true;
			return true;
		}
		
		public Object getConfig() {
			return new Config(0, false);
		}
		
		public void remove() {
			Reactor.Instance().getList().tryRemove(this);
		}
	}
	
	public class NonTermTriggerExample implements Trigger {
		public boolean isTriggered() {
			return true;
		}
		public Object getConfig() {
			return new Config(0, false);
		}
		
		public void remove() {
			//ignore
		}
	}
}