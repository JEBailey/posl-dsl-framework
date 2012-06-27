package posl.engine.lexeme;

import java.util.List;

import posl.engine.api.ALexeme;
import posl.engine.core.PoslStream;
import posl.engine.token.Token;

public class Grammar extends ALexeme {

	@Override
	public boolean consume(List<Token> tokens, PoslStream ps) {
		ps.mark();
		boolean grammar = false;
		switch (ps.val()){
		case '{':
		case '}':
		case '(':
		case ')':
		case '[':
		case ']':
			grammar = true;
		}
		if (grammar){
			tokens.add(Token.GRAMMAR((char)ps.pop(), ps.getMark()));
		}
		return grammar;
	}

}
