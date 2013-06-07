package posl.engine.api;

import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface Lexer extends Iterator<Token> {

	public abstract void tokenize(InputStream is, List<Lexeme>lexemes);
	
	public abstract void tokenize(Reader reader, List<Lexeme>lexemes);

}