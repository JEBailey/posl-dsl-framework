package posl.engine.api;

import java.util.List;

import posl.engine.core.PoslStream;
import posl.engine.token.Token;

public interface ILexeme {
	
	boolean consume(List<Token>tokens, PoslStream ps);

}
