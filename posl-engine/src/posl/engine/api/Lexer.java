package posl.engine.api;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

import posl.engine.core.Stream;

public interface Lexer extends Iterator<Token> {

	public abstract void tokenize(InputStream is);
	
	public abstract void tokenize(Reader reader);

}