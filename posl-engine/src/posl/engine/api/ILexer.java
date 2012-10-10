package posl.engine.api;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

import posl.engine.core.PoslStream;

public interface ILexer extends Iterator<IToken> {

	public abstract void tokenize(InputStream is);
	
	public abstract void tokenize(Reader reader);

}