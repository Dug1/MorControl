package com.bhrobotics.morcontrol.devices;

import com.bhrobotics.morcontrol.devices.InvalidStateException;
import com.bhrobotics.morcontrol.devices.DigitalStateSerializer;
import com.bhrobotics.morcontrol.util.Hash;

import junit.framework.TestCase;

public class DigitalStateSerializerTest extends TestCase {
	private DigitalStateSerializer serializer = new DigitalStateSerializer();
	private DigitalState state = DigitalState.ON;
	
	public void testSerialize() {
		Hash hash = serializer.serialize(state);
		assertEquals(true, hash.getAsBoolean("value"));
	}

	public void testDeserialize() {
		Hash hash = new Hash();
		hash.put("value", true);
		assertEquals(state, serializer.deserialize(hash));
	}
	
	public void testInvalidHash() {
		try {
			Hash hash = new Hash();
			serializer.deserialize(hash);
			fail("Exception not thrown.");
		} catch (InvalidStateException e) {
		}
	}
}
