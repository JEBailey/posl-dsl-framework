package posl.engine.api;

import java.io.InputStream;
import java.util.Iterator;

public interface ILexer extends Iterator<IToken> {

	public abstract void tokenize(InputStream is);

}