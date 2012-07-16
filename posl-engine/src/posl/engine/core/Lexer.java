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
import posl.engine.api.IToken;
import posl.engine.lexeme.Comments;
import posl.engine.lexeme.Eol;
import posl.engine.lexeme.Grammar;
import posl.engine.lexeme.Identifier;
import posl.engine.lexeme.Numbers;
import posl.engine.lexeme.QuoteString;
import posl.engine.lexeme.WhiteSpace;

public class Lexer implements ILexer {

	private List<IToken> tokens;

	private PoslStream wrapper;
	
	@SuppressWarnings("serial")
	private Map<String, ALexeme> lexemes = new LinkedHashMap<String, ALexeme>(){{
		put("whitespace",new WhiteSpace());
		put("comments",new Comments());
		put("numbers",new Numbers());
		put("identifier",new Identifier());
		put("quotes",new QuoteString());
		put("grammar",new Grammar());
		put("eol",new Eol());
	}};
	
	
	/**
	 * 
	 * 
	 */
	public void tokenize(InputStream is) {
		wrapper = new PoslStream(is);
		tokens = new ArrayList<IToken>();
		tokenize();
		wrapper = null;
	}

	public void tokenize(Reader reader) {
		wrapper = new PoslStream(reader);
		tokens = new ArrayList<IToken>();
		tokenize();
		wrapper = null;
	}

	/**
	 * 
	 * 
	 */
	public void tokenize() {
		boolean consumed = false;
		while (wrapper.hasMore()) {
			for (ALexeme lexeme:lexemes.values()){
				consumed = lexeme.consume(tokens, wrapper) | consumed;
			}
			if (!consumed){
				return;
			}
			consumed = false;
		}
	}



	@Override
	public IToken next() {
		if (!tokens.isEmpty()) {
			return (IToken) tokens.remove(0);
		}
		return null;
	}


	@Override
	public boolean hasNext() {
		return !tokens.isEmpty();
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		// not implemented
	}


}