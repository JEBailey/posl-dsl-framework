package posl.engine.api;

import java.util.List;

import posl.engine.core.Stream;

public interface Lexeme {
	
	boolean consume(List<Token> tokens, Stream ps);
	
}
