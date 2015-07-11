package dev.features;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import posl.engine.Interpreter;
import posl.engine.core.Context;
import posl.engine.error.PoslException;
import posl.engine.provider.PoslProvider;

public class ErrorsTest {

	private Context context;

    @Before
    public void setUp() throws PoslException {
		context = PoslProvider.getContext("posl.lang.core");
    }
    
    @Test()
	public void testUndefinedKeyword() {
    	try {
    		eval("hahaha");
    	} catch (PoslException e){
    		assertEquals(new PoslException(-1,"Null reference for hahaha"),e);
    	}
	}
    
    @Test()
	public void testMatchingBrace() throws PoslException {
    	try {
    		eval("{hahaha}}");
    	} catch (PoslException e){
    		assertEquals(new PoslException(1,"could not match brace"),e);
    	}
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
