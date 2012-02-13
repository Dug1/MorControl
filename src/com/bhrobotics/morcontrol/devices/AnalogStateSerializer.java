package com.bhrobotics.morcontrol.devices;

import com.bhrobotics.morcontrol.util.Hash;
import com.bhrobotics.morcontrol.util.UnknownKeyException;

public class AnalogStateSerializer {
	public Hash serialize(AnalogState state) {
		Hash result = new Hash();
		result.put("value", state.toDouble());
		
		return result;
	}
	
	public AnalogState deserialize(Hash hash) {
		try {
			double value = hash.fetchAsDouble("value");
			return new AnalogState(value);
		} catch (UnknownKeyException e) {
			throw new InvalidStateException(e);
		} catch (ClassCastException e) {
			throw new InvalidStateException(e);
		}
	}
}
