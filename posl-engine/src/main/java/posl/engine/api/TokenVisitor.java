package posl.engine.api;

/**
 * Token Visitor defines in an abstract way the higher level types of tokens
 * that can exist.
 * 
 * 
 * @author jason
 *
 */
public interface TokenVisitor {

	/**
	 * The token represents one or more lines of text that is in the source code
	 * for informational purposes
	 * 
	 * @param token
	 */
	void visitComments(Token token);

	/**
	 * The end of a statement of a physical line
	 * 
	 * @param token
	 */
	void visitEol(Token token);

	/**
	 * Grammar is the characters that represent the language structure
	 * 
	 * @param token
	 */
	void visitGrammar(Token token);

	/**
	 * An identifier is a sequence that represents something else withing the
	 * code, this could be a variable, a keyword, or a static value
	 * 
	 * @param token
	 */
	void visitIdentifier(Token token);

	void visitNumbers(Token token);

	void visitQuote(Token token);

	void visitWhitespace(Token token);

}
