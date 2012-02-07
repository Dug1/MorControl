package com.mortorq.bhrobotics.event;

import com.bhrobotics.morcontrol.event.Event;
import com.bhrobotics.morcontrol.event.EventBuilder;
import com.bhrobotics.morcontrol.event.GenericDeployer;
import com.bhrobotics.morcontrol.event.Handler;

import junit.framework.TestCase;

public class GenericFilterTest extends TestCase {
	private final String RIGHT_KEY = "Right Key";
	private final String WRONG_KEY = "WRONG Key";
	private final String KEY = "42";
	private final String BAD_KEY = "56";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testMatches() {
		String[] reqs = {KEY};
		GenericDeployer filter  = new GenericDeployer(RIGHT_KEY, reqs, new Handler(){
																			public void execute(Event event) {
																				System.out.println("Rar");
																			}
		});
		Event eventRight = new EventBuilder(RIGHT_KEY).with(KEY, 1).build();
		Event eventWrongData = new EventBuilder(RIGHT_KEY).with(BAD_KEY, 1).build();
		Event eventWrongName = new EventBuilder(WRONG_KEY).build();
		
		assertFalse(filter.matches(eventWrongData));
		assertFalse(filter.matches(eventWrongName));
		assertTrue(filter.matches(eventRight));
	}

}
