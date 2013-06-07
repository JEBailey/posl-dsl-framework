package posl.engine.api;


public class LexUtil {
	
	public static boolean isAlpha(int value) {
		return Character.isJavaIdentifierPart(value);
	}
	
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
	
	
	public static boolean isSpecial(int value) {
		return ((value >= '#') && (value <= '\''))
				|| ((value >= '*') && (value <= '/'))
				|| ((value >= ':') && (value <= '@')) || value == '\\'
				|| value == '^' || value == '!';
	}
}
