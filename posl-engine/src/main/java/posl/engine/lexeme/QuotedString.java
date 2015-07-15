package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import posl.engine.api.Collector;
import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.api.TokenVisitor;
import posl.engine.core.BasicToken;
import posl.engine.core.Stream;

public class QuotedString implements Lexeme {

	Pattern pattern = Pattern.compile("^\"([^\"\\\\]*(?:\\\\.[^\"\\\\]*)*)\"");
	
	@Override
	public boolean consume(List<Token> tokens, Stream ps) {
		// string
		if (ps.val() == '"') {
			return processQuote(tokens,ps);
		}
		return false;
	}
	
	private boolean processQuote(List<Token> tokens, Stream ps) {
		StringBuilder sb = new StringBuilder();
		ps.pop();
		ps.setMark();
		while (ps.hasMore() && ps.val() != '"') {
			if (ps.val() == '\\') {
				ps.pop();
				switch (ps.val()) {
				case 'a':
					sb.append(0x7);
					break;
				case 'b':
					sb.append('\b');
					break;
				case 'f':
					sb.append((char)0xC);
					break;
				case 'n':
					sb.append('\n');
					break;
				case 'r':
					sb.append('\r');
					break;
				case 't':
					sb.append('\t');
					break;
				case 'v':
					sb.append((char)0xB);
					break;
				default:
					sb.append((char) ps.val());
				}// end switch
				ps.pop();
			} else {
				sb.append(ps.pop());
			}

		}// end while
		if (ps.val() == '"') {
			ps.pop();
			tokens.add(new Inner(sb.toString(), ps.getMark()));
		} else {
			ps.reset();
			return false;
		}
		return true;

	}
	
	private class Inner extends BasicToken {

		public Inner(String value, int i) {
			this.value = value;
			this.startPos = i;
			this.endPos = i+value.length() + 2;
		}
		
		public Inner(String value, int start, int end) {
			this.value = value;
			this.startPos = start;
			this.endPos = end;
		}

		@Override
		public Collector consume(Collector collector, Stack<Collector> collectors,
				Stack<Character> charStack) {
			collector.add(value);
			return collector;
		}
		
		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitQuote(this);
		}


	}

	@Override
	public int consume(List<Token> tokens, CharSequence ps, int offset) {
		int totalCaptured = 0;
		Matcher matcher = pattern.matcher(ps);
		while (matcher.find(offset+totalCaptured)) {
			String s = matcher.group(1);
			tokens.add(new Inner(s, offset + totalCaptured, matcher.end()));
			totalCaptured += s.length();
		}
		return totalCaptured;
	}

}
