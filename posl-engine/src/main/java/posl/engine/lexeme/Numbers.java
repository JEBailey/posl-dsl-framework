package posl.engine.lexeme;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import posl.engine.api.Collector;
import posl.engine.api.LexUtil;
import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.api.TokenVisitor;
import posl.engine.core.BasicToken;
import posl.engine.core.Stream;

public class Numbers implements Lexeme {

	Pattern pattern = Pattern.compile("^-?\\d+([.]\\d+)?(([eE])[+-]?\\d+)?");
	
	private static NumberFormat nf = NumberFormat.getInstance();

	@Override
	public boolean consume(List<Token> tokens, Stream ps) {
		// numbers
		if (LexUtil.isDigit(ps.val())
				|| (ps.val() == '-' && LexUtil.isDigit(ps.la(1)))) {
			if (ps.val() == '0' && ps.la(1) == 'x') {
				return processHexCode(tokens, ps);
			}
			return processNumber(tokens, ps);
		}
		return false;
	}

	private boolean processNumber(List<Token> tokens, Stream ps) {
		ps.setMark();
		ps.pop();
		consumeDigits(ps);
		// at this point we have defined all positive whole numbers
		// now check for float
		if (ps.val() == '.') {
			ps.pop();
			consumeDigits(ps);
			if (ps.val() == 'e' || ps.val() == 'E') {
				int priorMark = ps.getMark();
				ps.setMark();
				ps.pop();
				if (LexUtil.isDigit(ps.val())
						|| (ps.val() == '-' || ps.val() == '+')) {
					ps.pop();
					consumeDigits(ps);
				} else {
					// TODO:handle this properly
					// tokens.add(new Inner(ps.getSubString(), ps.getMark()));
					// tokens.add(Token.NUMBER(ps.getSubString(),ps.getMark()));
					ps.reset(priorMark);
				}
			}
		}
		String numText = ps.getSubString();
		tokens.add(new Inner(numText, ps.getMark(), numText.length()));
		return true;
	}

	private boolean processHexCode(List<Token> tokens, Stream ps) {
		// skip the first 0x
		ps.pop();
		ps.pop();
		ps.setMark();
		while (LexUtil.isDigit(ps.val()) || LexUtil.isHex(ps.val())) {
			ps.pop();
		}
		String numText = ps.getSubString();
		tokens.add(new Inner(String.valueOf(Integer.parseInt(numText, 16)), ps
				.getMark() - 2, numText.length() + 2));
		return true;
	}

	/**
	 * Moves the stream index forward until it reaches a non-digit character
	 * 
	 * @param ps
	 */
	public static void consumeDigits(Stream ps) {
		while (LexUtil.isDigit(ps.val())) {
			ps.pop();
		}
	}

	private class Inner extends BasicToken {
		

		public Inner(String value, int startPos, int length) {
			try {
				this.value = nf.parse(value);
			} catch (ParseException e) {
				this.value = new Error("bad number format");
			}
			this.startPos = startPos;
			this.endPos = startPos + length;
		}

		@Override
		public Collector consume(Collector statement,
				Stack<Collector> statements, Stack<Character> charStack) {
			statement.add(value);
			return statement;
		}

		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitNumbers(this);
		}

	}

	@Override
	public int consume(List<Token> tokens, CharSequence ps, int offset) {
		int totalCaptured = 0;
		Matcher matcher = pattern.matcher(ps);
		matcher.region(offset, ps.length());
		if (matcher.lookingAt()) {
			String s = matcher.group();
			tokens.add(new Inner(s, offset + totalCaptured, matcher.end()));
			totalCaptured += s.length();
		}
		return totalCaptured;
	}

}
