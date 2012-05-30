package posl.engine.api;

import java.util.List;

import posl.engine.core.StreamWrapper;

public interface ILexeme {
	
	boolean consume(int index, List<IToken>tokens, StreamWrapper wrapper);

}
