package com.bhrobotics.morcontrol.devices;

import com.bhrobotics.morcontrol.devices.InvalidStateException;
import com.bhrobotics.morcontrol.util.Hash;

import junit.framework.TestCase;

public class AnalogStateSerializerTest extends TestCase {
	private AnalogStateSerializer serializer = new AnalogStateSerializer();
	private AnalogState state = new AnalogState(0.0);
	
	public void testSerialize() {
		Hash hash = serializer.serialize(state);
		assertEquals(0.0, hash.getAsDouble("value"), 0.01);
	}

	public void testDeserialize() {
		Hash hash = new Hash();
		hash.put("value", 0.0);
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
