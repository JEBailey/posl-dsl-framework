package dev.features;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import posl.engine.Interpreter;
import posl.engine.core.Context;
import posl.engine.error.PoslException;
import posl.engine.provider.PoslProvider;
import posl.lang.executable.NamespaceExec;

public class Namespace {
    private Context context;

    @Before
    public void setUp() throws PoslException {
		context = PoslProvider.getContext("posl.lang.core");
    }

    @Test
    public void testNamespaceCreation() throws PoslException {
        eval("var Foo {}");
        Object namespace = eval("var Bar [new Foo]");
        assertEquals(NamespaceExec.class, namespace.getClass());
    }

    @Test
    public void testCreationWithFields() throws PoslException {
        evalMulti(
                "var Foo {",
                    "var length 5",
                "}");
        eval("var bar [new Foo]");
        assertEquals(new Long(5), eval("bar length"));
    }
    
    @Test
    public void testCreationWithFieldsUnicode() throws PoslException {
        evalMulti(
                "var Foo {",
                    "var છ 5",
                "}");
        eval("var bar [new Foo]");
        assertEquals(new Long(5), eval("bar છ"));
    }

    @Test
    public void testCreationWithFunctions() throws PoslException {
        evalMulti(
                "var Foo {",
                    "function compute(x){",
                        "* x 2",
                    "}",
                "}");
        eval("var bar [new Foo]");
        assertEquals(10D, eval("bar compute 5"));
    }

    @Test
    public void testCreationWithFieldsAndFunctions() throws PoslException {
        evalMulti(
                "var Foo {",
                    "var x 3",
                    "function mutate(y){",
                        "= x [* y x]",
                    "}",
                "}");
        eval("var bar [new Foo]");
        eval("bar mutate 10");                                  
        assertEquals(30D, eval("bar x"));
    }
    
    @Test
    public void testCreationWithFieldsAndFunctions2() throws PoslException {
        evalMulti(
                "var Foo {",
                    "var x 3",
                    "function mutate(y){",
                        "= x [* y x]",
                    "}",
                "}",
                "var z 25");
        eval("var bar [new Foo]");
        eval("bar mutate z");                                  
        assertEquals(75D, eval("bar x"));
    }
    
    @Test
    //used a random collection of unicode characters. If I somehow used an obscene
    //word by accident please let me know
    public void testCreationWithFieldsAndFunctionsUnicode() throws PoslException {
        evalMulti(
                "var אבא {",
                    "var x 3",
                    "function mutate(y){",
                        "= x [* y x]",
                    "}",
                "}");
        eval("var bar [new אבא]");
        eval("bar mutate 10");
        assertEquals(30D, eval("bar x"));
    }

    @Test
    public void testNamespaceState() throws PoslException{
        evalMulti(
                "var Foo {",
                    "var x 23",
                    "function set_x(y){",
                        "= x y",
                    "}",
                "}");
        
        //retains state
        eval("var bar [new Foo]");
        eval("bar set_x 123");
        assertEquals(123L, eval("bar x"));
        
        //does not share state across instances
        eval("var baz [new Foo]");
        eval("baz set_x 777");
 
        assertEquals(new Long(777), eval("baz x"));
        assertEquals(new Long(123), eval("bar x"));
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
