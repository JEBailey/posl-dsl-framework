package posl.engine.core;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import posl.engine.api.ALexeme;
import posl.engine.api.ILexer;
import posl.engine.lexeme.EOLComments;
import posl.engine.lexeme.Grammar;
import posl.engine.lexeme.Identifier;
import posl.engine.lexeme.MultiLineComments;
import posl.engine.lexeme.Numbers;
import posl.engine.lexeme.QuoteString;
import posl.engine.lexeme.WhiteSpace;
import posl.engine.token.Token;

public class Lexer implements ILexer {

	// list of tokens to be returned
	protected List<Token> tokens;

	private PoslStream wrapper;
	
	@SuppressWarnings("serial")
	private Map<String, ALexeme> lexemes = new LinkedHashMap<String, ALexeme>(){{
		put("whitespace",new WhiteSpace());
		put("eolComments",new EOLComments());
		put("multiLineComments",new MultiLineComments());
		put("numbers",new Numbers());
		put("identifier",new Identifier());
		put("quotes",new QuoteString());
		put("grammar",new Grammar());
	}};
	
	
	/**
	 * 
	 * 
	 */
	public void tokenize(InputStream is) {
		wrapper = new PoslStream(is);
		tokens = new ArrayList<Token>();
		tokenize();
		wrapper = null;
	}

	public void tokenize(Reader reader) {
		wrapper = new PoslStream(reader);
		tokens = new ArrayList<Token>();
		tokenize();
		wrapper = null;
	}

	/**
	 * 
	 * 
	 */
	public void tokenize() {
		Collection<ALexeme> entries = lexemes.values();
		boolean consumed = false;
		while (wrapper.hasMore()) {
			for (ALexeme lexeme:entries){
				consumed = lexeme.consume(tokens, wrapper) | consumed;
			}
			if (!consumed){
				return;
			}
			consumed = false;
		}
		tokens.add(Token.EOL(wrapper.pos()));
	}

	/*
	 * private boolean isHexDigit(int value) { return isDigit(value) || (value
	 * >= 'a' && value <= 'f') || (value >= 'A' && value <= 'F'); }
	 */
	@Override
	public boolean hasMore() {
		return !tokens.isEmpty();
	}

	@Override
	public Token next() {
		if (!tokens.isEmpty()) {
			return (Token) tokens.remove(0);
		}
		return null;
	}

	public List<Token> getTokens() {
		return tokens;
	}

}