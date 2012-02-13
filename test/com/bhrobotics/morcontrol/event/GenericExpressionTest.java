package com.bhrobotics.morcontrol.event;

import junit.framework.TestCase;

public class GenericExpressionTest extends TestCase {
	public static final String EVENT_NAME_1 = "Juno";
	public static final String EVENT_NAME_2 = "Rhea";
	public static final String ATTR_1 = "#1";
	public static final String ATTR_2 = "#2";
	GenericExpression caesar = new GenericExpression();
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInit() {
		assertNotNull(caesar);
	}

	public void testMatches() {
		assertTrue(caesar.matches("Event " + EVENT_NAME_1 + " called"));
		assertTrue(caesar.matches("Event " + EVENT_NAME_1 + " with attributes " + ATTR_1 + " called"));
		assertTrue(caesar.matches("Event " + EVENT_NAME_2 + " called"));
		assertTrue(caesar.matches("Event " + EVENT_NAME_2 + " with attributes " + ATTR_2 + "," + ATTR_1 +  " called"));
		assertFalse(caesar.matches("Event " + "" + " called"));
		assertFalse(caesar.matches("Event " + EVENT_NAME_1 + "  " + " called"));
		
	}

	public void testParse() {
		StringBuffer buffer = new StringBuffer("Event " + EVENT_NAME_1 + " with attributes " + ATTR_1 + " " + ATTR_2 + " called");
		Node node = new ContainerExpression.ContainerNode();
		Context context = new Context(buffer, node);
		
		context = caesar.parse(context.getBuffer(), context.getCurrentNode());

		Node child = context.getCurrentNode().getChildren()[0];
		assertEquals(0, context.getBuffer().size());
		assertTrue(caesar.matchesNode(child));
		assertNotNull(child.getData().get(GenericExpression.ATTRIBUTES));
		String attributes = (String) child.getData().get(GenericExpression.ATTRIBUTES);
		assertEquals(ATTR_1 + " " + ATTR_2, attributes);
	}
	
	public void testGenericNode() {
		Reactor.getInstance().start();
		StringBuffer buffer = new StringBuffer("Event " + EVENT_NAME_1 + " with attributes " + ATTR_1 + " called");
		Node node = new ContainerExpression.ContainerNode();
		Context context = new Context(buffer, node);
		context = caesar.parse(context.getBuffer(), context.getCurrentNode());
		Node child = context.getCurrentNode().getChildren()[0];
		class TestHandler implements Handler {
			int timesCalled = 0;
			
			public void execute(Event event) {
				timesCalled++;
			}	
		}
		
		TestHandler handler = new TestHandler();
		Handler[] handlers = {handler};
		child.register(handlers);
		Event lacksName = new EventBuilder(EVENT_NAME_1).build();
		Event lacksAttr = new EventBuilder(EVENT_NAME_2).with(ATTR_2, false).build();
		Event goodOneAttr = new EventBuilder(EVENT_NAME_1).with(ATTR_1, true).build();
		Event goodTwoAttr = new EventBuilder(EVENT_NAME_1).with(ATTR_1, true).with(ATTR_2, 'c').build();
		Reactor.getInstance().addEvent(lacksName);
		Reactor.getInstance().addEvent(lacksAttr);
		Reactor.getInstance().addEvent(goodOneAttr);
		Reactor.getInstance().addEvent(goodTwoAttr);
		
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() < start+5000) {
			Reactor.getInstance().tick();
		}
		
		assertEquals(2,handler.timesCalled);
	}
}