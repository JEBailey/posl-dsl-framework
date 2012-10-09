package posl.engine.api;

public interface TokenVisitor {

	void visitComments(IToken iToken);
	
	void visitEol(IToken iToken);
	
	void visitGrammar(IToken iToken);
	
	void visitIdentifier(IToken iToken);
	
	void visitNumbers(IToken iToken);
	
	void visitQuote(IToken iToken);
	
	void visitWhitespace(IToken iToken);

}
