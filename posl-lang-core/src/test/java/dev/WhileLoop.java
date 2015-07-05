package dev;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import posl.engine.Interpreter;
import posl.engine.core.Context;
import posl.engine.error.PoslException;
import posl.engine.provider.PoslProvider;

public class WhileLoop {

	private Context context;
	
	
	@Before
	public void setUp() throws Exception {
		context = PoslProvider.getContext("posl.lang.core");
	}

	@Test
	public void testWhileLoop() throws PoslException {
		assertEquals(500000D,evalMulti( "var x 0", 
										"while [< x 500000] {",
										     " ++ x",
										" }"));
	}
	
    private Object eval(String expression) throws PoslException {
        return Interpreter.process(context, expression);
    }

    private Object evalMulti(String... expressions) throws PoslException {
        StringBuilder stmt = new StringBuilder();
        for (String e : expressions) {
            stmt.append(e);
            stmt.append("\n");
        }
        return eval(stmt.toString());
    }

}
