package posl.engine.lexeme;

import java.util.List;

import posl.engine.api.ILexeme;
import posl.engine.core.PoslStream;
import posl.engine.token.Token;

public class Grammar implements ILexeme {

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
