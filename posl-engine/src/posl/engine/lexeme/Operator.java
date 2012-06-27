package posl.engine.lexeme;

import java.util.List;

import posl.engine.api.ALexeme;
import posl.engine.core.PoslStream;
import posl.engine.token.Token;

public class Operator extends ALexeme {

	@Override
	public boolean consume(List<Token> tokens, PoslStream stream) {
		
		if (isSpecial(stream.val())) {
			return processSpecial(tokens,stream);
		} 
		return false;
	}
	
	private static boolean processSpecial(List<Token> tokens, PoslStream ps) {
		ps.mark();
		ps.pop();
		while (isSpecial(ps.val())) {
			ps.pop();
		}
		tokens.add(Token.WORD(ps.getSubString(), ps.getMark()));
		return true;
	}

}
