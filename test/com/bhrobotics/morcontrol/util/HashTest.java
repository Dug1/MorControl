package com.bhrobotics.morcontrol.util;

import java.util.Hashtable;

import com.bhrobotics.morcontrol.util.Hash;
import com.bhrobotics.morcontrol.util.UnknownKeyException;

import junit.framework.TestCase;

public class HashTest extends TestCase {
	public void testCreateWithoutHashtable() {
		Hash hash = new Hash();
		assertEquals(new Hashtable(), hash.toHashtable());
	}
	
	public void testCreateWithHashtable() {
		Hashtable hashtable = new Hashtable();
		Hash hash = new Hash(hashtable);
		assertSame(hashtable, hash.toHashtable());
	}
	
	public void testEmpty() {
		Hash hash = new Hash();
		assertTrue(hash.isEmpty());
		
		hash.put("foo", "bar");
		assertFalse(hash.isEmpty());
	}
	
	public void testContainsKey() {
		Hash hash = new Hash();
		
		hash.put("foo", "bar");
		assertFalse(hash.containsKey("foo2"));
		
		hash.put("foo2", "bar2");
		assertTrue(hash.containsKey("foo2"));
	}
	
	public void testContains() {
		Hash hash = new Hash();
		
		hash.put("foo", "bar");
		assertFalse(hash.contains("bar2"));
		
		hash.put("foo2", "bar2");
		assertTrue(hash.contains("bar2"));
	}
	
	public void testGetAndPut() {
		Hash hash = new Hash();
		
		hash.put("object", "bar");
		assertEquals("bar", hash.get("object"));
		
		hash.put("string", "bar");
		assertEquals("bar", hash.getAsString("string"));

		hash.put("char", 'g');
		assertEquals('g', hash.getAsChar("char"));		
		
		hash.put("byte", (byte) 0xFF);
		assertEquals((byte) 0xFF, hash.getAsByte("byte"));		
		
		hash.put("short", (short) 4);
		assertEquals((short) 4, hash.getAsShort("short"));		
		
		hash.put("int", 2);
		assertEquals(2, hash.getAsInt("int"));		
		
		hash.put("long", 2L);
		assertEquals(2L, hash.getAsLong("long"));		
		
		hash.put("float", 2.0f);
		assertEquals(2.0f, hash.getAsFloat("float"), 0.01);		
		
		hash.put("double", 2.0);
		assertEquals(2.0, hash.getAsDouble("double"), 0.01);		
		
		hash.put("boolean", true);
		assertEquals(true, hash.getAsBoolean("boolean"));
	}
	
	
	public void testFetchWithoutADefaultValue() {
		Hash hash = new Hash();
		
		hash.put("object", "bar");
		assertEquals("bar", hash.fetch("object"));
		try {
			hash.fetch("no object");
			fail("Exception not thrown.");
		} catch (UnknownKeyException e) {
		}
		
		hash.put("string", "bar");
		assertEquals("bar", hash.fetchAsString("string"));
		try {
			hash.fetchAsString("no string");
			fail("Exception not thrown.");
		} catch (UnknownKeyException e) {
		}
	
		hash.put("char", 'g');
		assertEquals('g', hash.fetchAsChar("char"));		
		try {
			hash.fetchAsChar("no char");
			fail("Exception not thrown.");
		} catch (UnknownKeyException e) {
		}
		
		hash.put("byte", (byte) 0xFF);
		assertEquals((byte) 0xFF, hash.fetchAsByte("byte"));
		try {
			hash.fetchAsByte("no byte");
			fail("Exception not thrown.");
		} catch (UnknownKeyException e) {
		}
		
		hash.put("short", (short) 4);
		assertEquals((short) 4, hash.fetchAsShort("short"));
		try {
			hash.fetchAsShort("no short");
			fail("Exception not thrown.");
		} catch (UnknownKeyException e) {
		}
		
		hash.put("int", 2);
		assertEquals(2, hash.fetchAsInt("int"));
		try {
			hash.fetchAsInt("no int");
			fail("Exception not thrown.");
		} catch (UnknownKeyException e) {
		}
		
		hash.put("long", 2L);
		assertEquals(2L, hash.fetchAsLong("long"));		
		try {
			hash.fetchAsLong("no long");
			fail("Exception not thrown.");
		} catch (UnknownKeyException e) {
		}
		
		hash.put("float", 2.0f);
		assertEquals(2.0f, hash.fetchAsFloat("float"), 0.01);		
		try {
			hash.fetchAsFloat("no float");
			fail("Exception not thrown.");
		} catch (UnknownKeyException e) {
		}
		
		hash.put("double", 2.0);
		assertEquals(2.0, hash.fetchAsDouble("double"), 0.01);	
		try {
			hash.fetchAsDouble("no double");
			fail("Exception not thrown.");
		} catch (UnknownKeyException e) {
		}
		
		hash.put("boolean", true);
		assertEquals(true, hash.fetchAsBoolean("boolean"));
		try {
			hash.fetchAsBoolean("no boolean");
			fail("Exception not thrown.");
		} catch (UnknownKeyException e) {
		}
	}
	
	public void testFetchWithADefaultValue() {
		Hash hash = new Hash();
		
		hash.put("object", "bar");
		assertEquals("bar", hash.fetch("object", "baz"));
		assertEquals("baz", hash.fetch("no object", "baz"));
		
		hash.put("string", "bar");
		assertEquals("bar", hash.fetchAsString("string", "baz"));
		assertEquals("baz", hash.fetchAsString("no string", "baz"));

		hash.put("char", 'g');
		assertEquals('g', hash.fetchAsChar("char", 'a'));		
		assertEquals('a', hash.fetchAsChar("no char", 'a'));		
		
		hash.put("byte", (byte) 0xFF);
		assertEquals((byte) 0xFF, hash.fetchAsByte("byte", (byte) 0x00));
		assertEquals((byte) 0x00, hash.fetchAsByte("no byte", (byte) 0x00));
		
		hash.put("short", (short) 4);
		assertEquals((short) 4, hash.fetchAsShort("short", (short) 2));
		assertEquals((short) 2, hash.fetchAsShort("no short", (short) 2));
		
		hash.put("int", 2);
		assertEquals(2, hash.fetchAsInt("int", 3));
		assertEquals(3, hash.fetchAsInt("no int", 3));
		
		hash.put("long", 2L);
		assertEquals(2L, hash.fetchAsLong("long", 5L));		
		assertEquals(5L, hash.fetchAsLong("no long", 5L));		
		
		hash.put("float", 2.0f);
		assertEquals(2.0f, hash.fetchAsFloat("float", 5.0f), 0.01);		
		assertEquals(5.0f, hash.fetchAsFloat("no float", 5.0f), 0.01);		
		
		hash.put("double", 2.0);
		assertEquals(2.0, hash.fetchAsDouble("double", 9.0), 0.01);	
		assertEquals(9.0, hash.fetchAsDouble("no double", 9.0), 0.01);	
		
		hash.put("boolean", true);
		assertEquals(true, hash.fetchAsBoolean("boolean", false));
		assertEquals(false, hash.fetchAsBoolean("no boolean", false));
	}
	
	public void testRemove() {
		Hash hash = new Hash();
		hash.put("foo", "bar");
		hash.remove("foo");
		assertFalse(hash.containsKey("foo"));
	}
	
	public void testSize() {
		Hash hash = new Hash();
		assertEquals(0, hash.size());
		
		hash.put("foo", "bar");
		assertEquals(1, hash.size());
		
		hash.put("baz", "quz");
		assertEquals(2, hash.size());
	}
	
	public void testClear() {
		Hash hash = new Hash();
		hash.put("foo", "bar");
		hash.put("baz", "quz");
		hash.clear();
		
		assertTrue(hash.isEmpty());
	}
	
	public void testToString() {
		Hashtable hashtable = new Hashtable();
		Hash hash = new Hash(hashtable);
		hash.put("foo", "bar");
		
		assertEquals(hashtable.toString(), hash.toString());
	}
}
