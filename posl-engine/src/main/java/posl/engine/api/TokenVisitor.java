package posl.engine.api;

/**
 * Token Visitor defines in an abstract way the higher level types
 * of tokens that can exist. 
 * 
 * 
 * @author jason
 *
 */
public interface TokenVisitor {

	void visitComments(Token token);
	
	void visitEol(Token token);
	
	void visitGrammar(Token token);
	
	void visitIdentifier(Token token);
	
	void visitNumbers(Token token);
	
	void visitQuote(Token token);
	
	void visitWhitespace(Token token);

}
