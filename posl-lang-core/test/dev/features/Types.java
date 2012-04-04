package dev.features;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import posl.engine.Interpreter;
import posl.engine.core.Context;
import posl.engine.error.PoslException;
import posl.engine.provider.PoslProvider;
import posl.engine.type.MultiLineStatement;

public class Types {
	
	private Context context;

    @Before
    public void setUp() throws PoslException {
		context = PoslProvider.getContext("posl:lang:core");
    }

	@Test
	public void testNumberTypeLong() throws PoslException {
		assertEquals(50L, eval("50"));
	}
	
	@Test
	public void testNumberTypeDecimal() throws PoslException {
		assertEquals(1.24555, eval("1.24555"));
	}
	
	@Test
	public void testBooleanTypeTrue() throws PoslException {
		assertEquals(Boolean.TRUE, eval("true"));
	}
	
	@Test
	public void testBooleanTypeFalse() throws PoslException {
		assertEquals(Boolean.FALSE, eval("false"));
	}
	
	@Test
	public void testStringType() throws PoslException {
		assertEquals("a string", eval("\"a string\""));
	}
	
	@Test
	public void testMultiLineStatementType() throws PoslException {
		assertEquals(MultiLineStatement.class,eval("set x { }").getClass());
	}
	
	
	
	
	private Object eval(String expression) throws PoslException {
        return Interpreter.process(context, expression);
    }

}
