package posl.engine.lexeme;

import java.util.List;

import posl.engine.api.ALexeme;
import posl.engine.core.PoslStream;
import posl.engine.token.Token;

public class Identifier extends ALexeme {

	@Override
	public boolean consume(List<Token> tokens, PoslStream ps) {
		if (isAlpha(ps.val()) || (ps.val() == '_' && isAlpha(ps.LA(1)))) {
			return processWord(tokens, ps);
		} else if (isSpecial(ps.val())) {
			return processSpecial(tokens, ps);
		}
		return false;
	}

	private static boolean processWord(List<Token> tokens, PoslStream ps) {
		ps.mark();
		ps.pop();
		while (isAlpha(ps.val()) || isSpecial(ps.val()) || isDigit(ps.val())
				|| ps.val() == '_') {
			ps.pop();
		}
		tokens.add(Token.WORD(ps.getSubString(), ps.getMark()));
		return true;
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
