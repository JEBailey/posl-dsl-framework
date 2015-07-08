package posl.engine.api;

import java.util.List;

import posl.engine.core.Stream;

public interface Lexeme {
	/**
	 * The lexeme implementation defines a word. The lexeme scans the incoming
	 * Stream and if the stream starts with the defined lexeme, that series of
	 * characters is consumed from the Stream and a token representing those
	 * characters are added to the token list.<br/>
	 * 
	 * 
	 * @param tokens
	 *            that are being generated
	 * @param ps
	 *            stream of data that is being consumed.
	 * @return true if text from the stream was consumed
	 */
	boolean consume(List<Token> tokens, Stream ps);
	
	
	int consume(List<Token> tokens, CharSequence ps, int offset);

}
