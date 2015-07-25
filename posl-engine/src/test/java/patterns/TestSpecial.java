package patterns;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.lexeme.Grammar;
import posl.engine.lexeme.Special;
import posl.engine.lexeme.WhiteSpace;

public class TestSpecial {

	Lexeme lex = new Special();
	
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
		data = "-- ";
		consumed = lex.consume(tokens, data, consumed);
		assertEquals(tokens.size(), 1);
		assertTrue(consumed == 2);
	}
	
	@Test
	public void testLeftBrace() {
		data = "{	";
		consumed = lex.consume(tokens, data, 0);
		assertEquals(1,tokens.size());
		assertTrue(consumed == 1);
	}
	
	@Test
	public void testManyTab() {
		data = "	&&		";
		consumed = lex.consume(tokens, data, 1);
		assertEquals(tokens.size(), 1);
		assertEquals(2,consumed);
	}
	

}
