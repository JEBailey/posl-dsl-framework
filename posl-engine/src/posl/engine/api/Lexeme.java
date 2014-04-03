package posl.engine.api;

import java.util.List;

import posl.engine.core.Stream;

public interface Lexeme {
	/**
	 * Converts the input stream into one or more tokens that are added to the
	 * token list. In the lexeme, the tokens are potentially parsed out of the
	 * Stream and added to the list of tokens.<br/>
	 * 
	 * 
	 * 
	 * @param tokens
	 *            that are being generated
	 * @param ps
	 *            stream of data that is being consumed.
	 * @return true if text from the stream was consumed
	 */
	boolean consume(List<Token> tokens, Stream ps);

}
