package posl.engine.api;

import java.util.List;

import posl.engine.core.Stream;

public interface Lexeme {
	/**
	 * Converts the input stream into one or more
	 * tokens that are added to the token list
	 * 
	 * @param tokens
	 * @param ps
	 * @return true if text from the stream was consumed
	 */
	boolean consume(List<Token> tokens, Stream ps);
	
}
