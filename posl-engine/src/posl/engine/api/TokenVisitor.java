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

	void visitComments(Token iToken);
	
	void visitEol(Token iToken);
	
	void visitGrammar(Token iToken);
	
	void visitIdentifier(Token iToken);
	
	void visitNumbers(Token iToken);
	
	void visitQuote(Token iToken);
	
	void visitWhitespace(Token iToken);

}
