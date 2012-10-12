package posl.engine.core;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
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
			// for each iteration through the available lexes
			// we want to be assured that something was consumed
			for (ALexeme lexeme:lexemes.values()){
				consumed = lexeme.consume(tokens, wrapper) | consumed;
			}
			// if we've iterated through and nothing has been consumed
			// return.(EOF could trigger this)
			// NOTE: If this IS EOF an EOL would have been caught
			if (!consumed){
				return;
			}
			//reset to false for next iteration
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