package posl.engine.lexeme;

import java.util.List;

import posl.engine.api.ILexeme;
import posl.engine.core.PoslStream;
import posl.engine.token.Token;

public class Identifier implements ILexeme {

	@Override
	public boolean consume(List<Token> tokens, PoslStream ps) {
		if (isAlpha(ps.val()) || (ps.val() == '_' && isAlpha(ps.LA(1)))) {
			return processWord(tokens,ps);
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
	
	private static boolean isSpecial(int value) {
		return ((value >= '#') && (value <= '\''))
				|| ((value >= '*') && (value <= '/'))
				|| ((value >= ':') && (value <= '@')) || value == '\\'
				|| value == '^' || value == '!';
	}
	
	private static boolean isDigit(int value) {
		return value >= '0' && value <= '9';
	}

	private static boolean isAlpha(int value) {
		return ((value >= 'a') && (value <= 'z'))
				|| ((value >= 'A') && (value <= 'Z'));
	}

}
