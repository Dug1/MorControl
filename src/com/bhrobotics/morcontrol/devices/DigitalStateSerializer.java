package com.bhrobotics.morcontrol.devices;

import com.bhrobotics.morcontrol.util.Hash;
import com.bhrobotics.morcontrol.util.UnknownKeyException;

public class DigitalStateSerializer {
	public Hash serialize(DigitalState state) {
		Hash result = new Hash();
		if (state.equals(DigitalState.OFF)) {
			result.put("value", false);
		} else {
			result.put("value", true);
		}
		
		return result;
	}

	public DigitalState deserialize(Hash hash) {
		try {
			boolean value = hash.fetchAsBoolean("value");
			if (value) {
				return DigitalState.ON;
			} else {
				return DigitalState.OFF;
			}
		} catch (UnknownKeyException e) {
			throw new InvalidStateException(e);
		} catch (ClassCastException e) {
			throw new InvalidStateException(e);
		}
	}
}
