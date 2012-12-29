package posl.engine.api;

import java.util.List;

import posl.engine.core.Stream;

public abstract class Lexeme {
	
	public abstract boolean  consume(List<Token>tokens, Stream ps);
	
	/**
	 * determines if supplied char is a digit
	 * 
	 * @param value char value
	 * @return true if char represents a digit
	 */
	public static boolean isDigit(int value) {
		return Character.isDigit(value);
	}
	
	/**
	 * determines if the supplied value represents a hex character
	 * 0-9,a-f,A-F
	 * 
	 * 
	 * @param value
	 * @return true if value is a hex character
	 */
	public static boolean isHex(int value) {
		return (value >= 'a' && value <= 'f') || (value >= 'A' && value <= 'F');
	}

	/**
	 * Moves the stream index forward until it
	 * reaches a non-digit character
	 * 
	 * @param ps
	 */
	public static void consumeDigits(Stream ps) {
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
		return Character.isJavaIdentifierPart(value);
	}

}
