package posl.engine.core;

import java.util.ArrayList;
import java.util.List;

import posl.engine.api.Lexeme;
import posl.engine.api.Lexer;
import posl.engine.api.Token;
import posl.engine.lexeme.Eol;
import posl.engine.tokens.EOS;

/**
 * Tokenizes the incoming CharSequence based on the List of Lexemes
 * 
 * 
 * @author jebailey
 *
 */
public class DefaultLexer implements Lexer {

	private List<Token> tokens;

	private Stream wrapper;
	
	private List<Lexeme> lexemes;
	
	private CharSequence data;

	@Override
	public void tokenize(CharSequence reader, List<Lexeme>lexemes) {
		data = reader;
		wrapper = new Stream(reader);
		tokens = new ArrayList<Token>();
		this.lexemes = lexemes;
		tokenize();
		wrapper = null;
	}

	/**
	 * 
	 * 
	 */
	private void tokenize() {
		int offset = 0;
		while (offset < data.length()) {
			// for each iteration through the available lexes
			// we want to be assured that something was consumed
			int consumed = offset;
			for (Lexeme lexeme:lexemes){
				consumed += lexeme.consume(tokens, data, consumed);
				if (consumed != offset){
					break;
				}
			}
			// if we've iterated through and nothing has been consumed
			// return.(EOF could trigger this)
			// NOTE: If this IS EOF an EOL would have been caught
			if (consumed == offset){
				if (data.length() > consumed){
					//TODO unhandled data - must log
					//String problem = wrapper.getSubString();
					System.out.print(wrapper.pop());
				}
				return;
			}
			//reset to false for next iteration
			offset = consumed;
		}
		tokens.add(new EOS("", offset, offset));
	}
	
	/**
	 * alternate implementation that supports the regex version
	 * 
	 */
	private void tokenize2() {
		boolean consumed = false;
		while (wrapper.hasMore()) {
			// for each iteration through the available lexes
			// we want to be assured that something was consumed
			for (Lexeme lexeme:lexemes){
				consumed = lexeme.consume(tokens, wrapper) | consumed;
			}
			// if we've iterated through and nothing has been consumed
			// return.(EOF could trigger this)
			// NOTE: If this IS EOF an EOL would have been caught
			if (!consumed){
				if (wrapper.hasMore()){
					//TODO unhandled data - must log
					//String problem = wrapper.getSubString();
					System.out.print(wrapper.pop());
				}
				return;
			}
			//reset to false for next iteration
			consumed = false;
		}
	}

	@Override
	public Token next() {
		if (!tokens.isEmpty()) {
			return tokens.remove(0);
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