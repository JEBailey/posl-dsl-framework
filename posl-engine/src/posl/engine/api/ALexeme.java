package posl.engine.api;

import java.util.List;

import posl.engine.core.PoslStream;
import posl.engine.token.Token;

public abstract class ALexeme {
	
	public abstract boolean  consume(List<Token>tokens, PoslStream ps);
	
	
	public static boolean isDigit(int value) {
		return value >= '0' && value <= '9';
	}
	
	public static boolean isHex(int value) {
		return (value >= 'a' && value <= 'f') || (value >= 'A' && value <= 'F');
	}

	public static void consumeDigits(PoslStream ps) {
		while (isDigit(ps.val())) {
			ps.pop();
		}
	}
	
	public static boolean isSpecial(int value) {
		return ((value >= '#') && (value <= '\''))
				|| ((value >= '*') && (value <= '/'))
				|| ((value >= ':') && (value <= '@')) || value == '\\'
				|| value == '^' || value == '!';
	}

	public static boolean isAlpha(int value) {
		return ((value >= 'a') && (value <= 'z'))
				|| ((value >= 'A') && (value <= 'Z'));
	}

}
