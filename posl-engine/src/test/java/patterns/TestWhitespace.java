package patterns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.lexeme.WhiteSpace;

public class TestWhitespace {

	Lexeme lex = new WhiteSpace();
	
	String data = "{";
	List<Token>tokens = new ArrayList<>();
	int consumed = 0;
	
	@Before
	public void init(){
		tokens.clear();
		consumed = 0;
	}
	
	@Test
	public void testWhiteSpace() {
		data = " ";
		consumed = lex.consume(tokens, data, consumed);
		assertEquals(tokens.size(), 0);
		assertTrue(consumed == 1);
	}
	
	@Test
	public void testTab() {
		data = "	";
		consumed = lex.consume(tokens, data, consumed);
		assertEquals(tokens.size(), 0);
		assertTrue(consumed == 1);
	}
	
	@Test
	public void testManyTab() {
		data = "				";
		consumed = lex.consume(tokens, data, consumed);
		assertEquals(tokens.size(), 0);
		assertEquals(4,consumed);
	}
	
	@Test
	public void testMixed() {
		data = "				FooBar ";
		consumed = lex.consume(tokens, data, consumed);
		assertEquals(tokens.size(), 0);
		assertEquals(4,consumed);
	}
	
	@Test
	public void testMiddleCatch() {
		data = "beer				FooBar ";
		consumed = lex.consume(tokens, data, 4);
		assertEquals(tokens.size(), 0);
		assertEquals(4,consumed);
	}


}
