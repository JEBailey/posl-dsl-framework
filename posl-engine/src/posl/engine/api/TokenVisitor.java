package posl.engine.api;

/**
 * Token Visitor defines in an abstract way the higher level types
 * of tokens that can exist. 
 * 
 * Example is a comment. It doesn't define the nature of the comments or
 * what is used to represent a comment. Just that a comment exists and this token
 * represents that information.
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
