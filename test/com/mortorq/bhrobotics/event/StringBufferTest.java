package com.mortorq.bhrobotics.event;

import com.bhrobotics.morcontrol.event.StringBuffer;

import junit.framework.*;

public class StringBufferTest extends TestCase {
	
	public void testInit() {
		Assert.assertNotNull(new StringBuffer(""));
	}
	
	public void testTokenizer() {
		StringBuffer buffer = new StringBuffer("((buttons are cool) and (rainbows are cool)) or I am cool");
		
		Assert.assertEquals(10,buffer.size());
	}
	
	public void testReadOne() {
		StringBuffer buffer = new StringBuffer("((buttons are cool) and (rainbows are cool)) or I am cool");
		
		Assert.assertEquals("(",buffer.readOne());
	}
		
	public void testRemove() {
		StringBuffer buffer = new StringBuffer("((buttons are cool) and (rainbows are cool)) or I am cool");
		
		buffer.remove();
		
		Assert.assertEquals(9,buffer.size());
	}
	
	public void testReplace() {
		StringBuffer buffer = new StringBuffer("((buttons are cool) and (rainbows are cool)) or I am cool");
		
		buffer.replace("RAR");
		
		Assert.assertEquals(10,buffer.size());
		Assert.assertEquals("RAR", buffer.readOne());
	}
	
	public void testIterativeRead() {
		StringBuffer buffer = new StringBuffer("((buttons are cool) and (rainbows are cool)) or I am cool");
		String result = "";
		
		while(buffer.hasMoreTokens()) {
			result = result + buffer.readOne();
			buffer.remove();
		}
		
		Assert.assertEquals("((buttons are cool)and(rainbows are cool))or I am cool", result);
	}
}