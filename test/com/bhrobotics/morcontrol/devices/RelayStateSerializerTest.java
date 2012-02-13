package com.bhrobotics.morcontrol.devices;

import com.bhrobotics.morcontrol.devices.InvalidStateException;
import com.bhrobotics.morcontrol.devices.RelayState;
import com.bhrobotics.morcontrol.devices.RelayStateSerializer;
import com.bhrobotics.morcontrol.util.Hash;

import junit.framework.TestCase;

public class RelayStateSerializerTest extends TestCase {
	private RelayStateSerializer serializer = new RelayStateSerializer();
	private RelayState forwardState = RelayState.FORWARD;
	
	public void testSerialize() {
		Hash hash = serializer.serialize(forwardState);
		assertEquals("forward", hash.getAsString("value"));
	}

	public void testDeserialize() {
		Hash hash = new Hash();
		hash.put("value", "forward");
		assertEquals(RelayState.FORWARD, serializer.deserialize(hash));
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
