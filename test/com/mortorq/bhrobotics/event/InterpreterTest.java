package com.mortorq.bhrobotics.event;

import com.bhrobotics.morcontrol.event.Branch;
import com.bhrobotics.morcontrol.event.Context;
import com.bhrobotics.morcontrol.event.Expression;
import com.bhrobotics.morcontrol.event.Interpreter;
import com.bhrobotics.morcontrol.event.Node;
import com.bhrobotics.morcontrol.event.ParseException;
import com.bhrobotics.morcontrol.event.StringBuffer;

import junit.framework.*; 

public class InterpreterTest extends TestCase {
	private Interpreter interpreter;
	
	protected void setUp() throws Exception {
        super.setUp();
		interpreter = new Interpreter();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }
	
	public void testInit() {
		Assert.assertNotNull(interpreter);
	}
	
	public void testModifyPatternList() {
		Expression JoanOfArc = new TestExpression();
		Expression expression = new TestExpression();
		
		interpreter.addPattern(expression);
		
		Assert.assertEquals(1,interpreter.size());
		
		interpreter.removePattern(expression);
		
		Assert.assertEquals(0,interpreter.size());
		
		interpreter.addPattern(JoanOfArc);
		interpreter.addPattern(expression);
		
		Assert.assertEquals(2,interpreter.size());
		
		interpreter.clear();
		
		Assert.assertEquals(0,interpreter.size());
	}
	
	public void testInterpret() {
		System.out.println("------------------------------------------------------------");
		TestLeftExpression HenryIV = new TestLeftExpression();
		TestRightExpression LouisXIV = new TestRightExpression();
		TestExpression NapoleonIII = new TestExpression();
		interpreter.addPattern(HenryIV);
		interpreter.addPattern(LouisXIV);
		interpreter.addPattern(NapoleonIII);
		
		Context context = new Context(new StringBuffer(""), new Branch());
		try {
			interpreter.interpret(new Context(new StringBuffer("(test)"), new Branch()));
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		
		Assert.assertFalse(context.getBuffer().hasMoreTokens());
		Assert.assertTrue(HenryIV.hasBeenCalled);
		Assert.assertTrue(LouisXIV.hasBeenCalled);
		Assert.assertTrue(NapoleonIII.hasBeenCalled);
		
		System.out.println("------------------------------------------------------------");
	}
	
	public class TestLeftExpression extends Expression {
		public boolean hasBeenCalled = false;
		
		public TestLeftExpression() {
			super("[(]");
		}
		
		public String getType() {
			return "Test";
		}
		
		public boolean matches(String argument) {
			return (argument.equals("("));
		}
		
		public Context parse(StringBuffer buffer, Node tree) {
			System.out.println("( has been matched!");
			System.out.println(buffer.toString());
			hasBeenCalled = true;
			buffer.remove();
			return (new Context(buffer, tree));
		}
		
		public void clean()	{ 
		}

		public boolean matchesNode(Node node) {
			return false;
		}
	}		
	
	public class TestRightExpression extends Expression {
		protected TestRightExpression() {
			super("[)]");
		}

		public boolean hasBeenCalled = false;
		
		public String getType() {
			return "Test";
		}
		
		public boolean matches(String argument) {
			return (argument.equals(")"));
		}
		
		public Context parse(StringBuffer buffer, Node tree) {
			System.out.println(") has been matched!");
			System.out.println(buffer.toString());
			hasBeenCalled = true;
			buffer.remove();
			return (new Context(buffer, tree));
		}
		
		public void clean()	{ 
		}

		public boolean matchesNode(Node node) {
			return false;
		}
	}
	
	public class TestExpression extends Expression {
		protected TestExpression() {
			super("test");
		}

		public boolean hasBeenCalled = false;
		
		public String getType() {
			return "Test";
		}
		
		public boolean matches(String argument) {
			return (argument.equals("test"));
		}
		
		public Context parse(StringBuffer buffer, Node tree) {
			System.out.println("test has been matched!");
			System.out.println(buffer.toString());
			hasBeenCalled = true;
			buffer.remove();
			return (new Context(buffer, tree));
		}
		
		public void clean()	{ 
		}

		public boolean matchesNode(Node node) {
			return false;
		}
	}	
}