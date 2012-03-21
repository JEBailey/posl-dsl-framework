package posl.engine.api;

import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import posl.engine.token.Token;

public interface ILexer {

	/**
	 * 
	 * 
	 * @param br
	 */
	public abstract void tokenize(InputStream is);

	public abstract boolean hasMore();

	public abstract Token next();

	public abstract void tokenize(Reader reader);

	public abstract List<Token> getTokens();

}