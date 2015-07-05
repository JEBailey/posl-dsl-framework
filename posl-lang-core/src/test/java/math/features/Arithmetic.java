package math.features;


import static org.junit.Assert.assertEquals;

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
		context = PoslProvider.getContext("posl.lang.core");
    }

    @Test
    public void testAddition() throws PoslException {
        assertEquals(3D, eval("+ 1 2"));
    }

    @Test
    public void testSubtraction() throws PoslException {
        assertEquals(1D, eval("- 2 1"));
    }

    @Test
    public void testSubtraction_LessThanZero() throws PoslException {
        assertEquals(-1D, eval("- 1 2"));
    }

    @Test
    public void testNegativeIntNumber() throws PoslException {
        assertEquals(-10L,eval("-10"));
    }
    
    @Test
    public void testNegativeDoubleNumber() throws PoslException {
        assertEquals(-10.02,eval("-10.02"));
    }

    @Test
    public void testMultiplication() throws PoslException {
        assertEquals(50D,eval("* 5 10"));
    }
    
    @Test
    public void testMultiplication_NegativeNumbers() throws PoslException {
        assertEquals(100D, eval("* -10 -10"));
    }

    @Test
    public void testDivision() throws PoslException {
        assertEquals(2D,eval("/ 10 5"));
    }

    @Test
    public void testNestedArithmetic() throws PoslException {
        assertEquals(144D, eval("* [+ 6 6] [+ 24 -12]"));
    }
    
    private Object eval(String expression) throws PoslException{
        return (Number)Interpreter.process(context, expression);
    }
    
}
