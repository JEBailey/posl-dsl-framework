package posl.engine.lexeme;

import java.util.List;

import posl.engine.api.ALexeme;
import posl.engine.core.PoslStream;
import posl.engine.token.Token;

public class Numbers extends ALexeme {

	@Override
	public boolean consume(List<Token> tokens, PoslStream ps) {
		// numbers
		if (isDigit(ps.val())
				|| (ps.val() == '-' && isDigit(ps.LA(1)))) {
			if (ps.val() == '0' && ps.LA(1) == 'x') {
				return processHexCode(tokens,ps);
			}
			return processNumber(tokens,ps);
		}
		return false;
	}

	private static boolean processNumber(List<Token> tokens, PoslStream ps) {
		ps.mark();
		ps.pop();
		consumeDigits(ps);
		// at this point we have defined all positive whole numbers
		// check for float
		if (ps.val() == '.') {
			ps.pop();
			consumeDigits(ps);
			if (ps.val() == 'e' || ps.val() == 'E') {
				ps.mark();
				ps.pop();
				if (isDigit(ps.val()) || (ps.val() == '-' || ps.val() == '+')) {
					ps.pop();
					consumeDigits(ps);
				} else {
					tokens.add(Token.NUMBER(ps.getSubString(),ps.getMark()));
					ps.reset();
				}
			}
		}
		tokens.add(Token.NUMBER(ps.getSubString(), ps.getMark()));
		return true;
	}

	// TODO
	private static boolean processHexCode(List<Token> tokens, PoslStream ps) {
		// skip the first 0x
		ps.pop();
		ps.pop();
		ps.mark();
		while (isDigit(ps.val()) || isHex(ps.val())) {
			ps.pop();
		}
		
		tokens.add(Token.NUMBER(String.valueOf(Integer.parseInt(ps.getSubString(), 16)), ps.getMark()));
		return true;
	}


}
