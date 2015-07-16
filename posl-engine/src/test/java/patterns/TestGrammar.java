package patterns;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.lexeme.Grammar;

public class TestGrammar {

	Lexeme lex = new Grammar();
	
	String data = "{";
	List<Token>tokens = new ArrayList<>();
	int consumed = 0;
	
	@Before
	public void init(){
		tokens.clear();
		consumed = 0;
	}
	
	@Test
	public void testLeftBrace() {
		data = "{";
		lex.consume(tokens, data, consumed);
		assertEquals(tokens.size(), 1);
		Token grammar = tokens.get(0);
		assertEquals("{", grammar.getString());
	}
	
	@Test
	public void testRightBrace() {
		data = "}";
		lex.consume(tokens, data, consumed);
		assertEquals(tokens.size(), 1);
		Token grammar = tokens.get(0);
		assertEquals("}", grammar.getString());
	}
	
	@Test
	public void testLeftBracket() {
		data = "[";
		lex.consume(tokens, data, consumed);
		assertEquals(tokens.size(), 1);
		Token grammar = tokens.get(0);
		assertEquals(data, grammar.getString());
	}
	
	@Test
	public void testRightBracket() {
		data = "]";
		lex.consume(tokens, data, consumed);
		assertEquals(tokens.size(), 1);
		Token grammar = tokens.get(0);
		assertEquals(data, grammar.getString());
	}
	
	@Test
	public void testLeftParen() {
		data = "(";
		consumed = lex.consume(tokens, data, consumed);
		assertEquals(tokens.size(), 1);
		Token grammar = tokens.get(0);
		assertEquals(data, grammar.getString());
		assertTrue(consumed == 1);
	}
	
	@Test
	public void testRightParen() {
		data = ")";
		consumed = lex.consume(tokens, data, consumed);
		assertEquals(tokens.size(), 1);
		Token grammar = tokens.get(0);
		assertEquals(data, grammar.getString());
		assertTrue(consumed == 1);
	}
	


}
