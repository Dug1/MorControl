package com.bhrobotics.morcontrol.devices;

import com.bhrobotics.morcontrol.util.Hash;
import com.bhrobotics.morcontrol.util.UnknownKeyException;

public class RelayStateSerializer {
	public Hash serialize(RelayState state) {
		Hash result = new Hash();
		if (state.equals(RelayState.OFF)) {
			result.put("value", "off");
		} else if (state.equals(RelayState.FORWARD)) {
			result.put("value", "forward");
		} else if (state.equals(RelayState.REVERSE)) {
			result.put("value", "reverse");
		}
		
		return result;
	}
	
	public RelayState deserialize(Hash hash) {
		try {
			String value = hash.fetchAsString("value");
			if (value.equals("off")) {
				return RelayState.OFF;
			} else if (value.equals("forward")) {
				return RelayState.FORWARD;
			} else if (value.equals("reverse")) {
				return RelayState.REVERSE;
			} else {
				throw new InvalidStateException("Unknown relay value " + value + ".");
			}
		} catch (UnknownKeyException e) {
			throw new InvalidStateException(e);
		} catch (ClassCastException e) {
			throw new InvalidStateException(e);
		}
	}
}
