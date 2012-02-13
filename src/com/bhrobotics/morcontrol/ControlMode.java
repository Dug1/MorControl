package com.bhrobotics.morcontrol;

import com.bhrobotics.morcontrol.event.ContainerExpression;
import com.bhrobotics.morcontrol.event.Context;
import com.bhrobotics.morcontrol.event.Interpreter;
import com.bhrobotics.morcontrol.event.ParseException;
import com.bhrobotics.morcontrol.event.Reactor;
import com.bhrobotics.morcontrol.event.StringBuffer;

public class ControlMode {
	Condition[] conditions;
	
	public ControlMode(Condition[] conditions) {
		this.conditions = conditions;
	}
	
	public void setUp(){
		for(int i = 0; i < conditions.length; i++) {
			Condition condition = conditions[i];
			Context context = new Context(new StringBuffer(condition.getTiming()), new ContainerExpression.ContainerNode());
			try {
				Interpreter.getInterpreter().interpret(context).getCurrentNode().register(condition.getHandlers());
			} catch (ParseException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void tearDown() {
		Reactor.getInstance().getRegistry().flush();
	}
}
