package posl.engine.api;

public interface TokenVisitor {

	void visitComments(Token iToken);
	
	void visitEol(Token iToken);
	
	void visitGrammar(Token iToken);
	
	void visitIdentifier(Token iToken);
	
	void visitNumbers(Token iToken);
	
	void visitQuote(Token iToken);
	
	void visitWhitespace(Token iToken);

}
