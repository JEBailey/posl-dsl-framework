package posl.engine.lexeme;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import posl.engine.api.Collector;
import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.api.TokenVisitor;
import posl.engine.core.BasicToken;

public class Numbers implements Lexeme {

	Pattern pattern = Pattern.compile("^-?\\d+([.]\\d+)?(([eE])[+-]?\\d+)?");
	
	private static NumberFormat nf = NumberFormat.getInstance();

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



}
