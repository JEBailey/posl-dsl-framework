package math.features;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import posl.engine.Interpreter;
import posl.engine.core.Context;
import posl.engine.error.PoslException;
import posl.engine.provider.PoslProvider;


public class Arithmetic {
    private Context context;

    @Before
    public void setUp() throws PoslException {
		context = PoslProvider.getContext("posl.lang.math");
    }

    @Test
    public void testAddition() throws PoslException {
        assertEquals(3, eval("+ 1 2"));
    }

    @Test
    public void testSubtraction() throws PoslException {
        assertEquals(1, eval("- 2 1"));
    }

    @Test
    public void testSubtraction_LessThanZero() throws PoslException {
        assertEquals(-1, eval("- 1 2"));
    }

    @Test
    public void testNegativeNumber() throws PoslException {
        assertEquals(-10,eval("-10"));
    }

    @Test
    public void testMultiplication() throws PoslException {
        assertEquals(50,eval("* 5 10"));
    }
    
    @Test
    public void testMultiplication_NegativeNumbers() throws PoslException {
        assertEquals(100, eval("* -10 -10"));
    }

    @Test
    public void testDivision() throws PoslException {
        assertEquals(new BigDecimal(2),evalDec("/ 10 5"));
    }

    @Test
    public void testNestedArithmetic() throws PoslException {
        assertEquals(144, eval("* [+ 6 6] [+ 24 -12]"));
    }
    
    private long eval(String expression) throws PoslException{
        return (Long)Interpreter.process(context, expression);
    }
    
    private BigDecimal evalDec(String expression) throws PoslException {
        return (BigDecimal)Interpreter.process(context, expression);
    }
}
