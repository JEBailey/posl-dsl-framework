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
import posl.engine.type.Statement;

/**
 * Consumes multiple end of line indicators
 * 
 * 
 * @author jebailey
 *
 */
public class Eol implements Lexeme {

	Pattern pattern = Pattern.compile("(\r?\n)");
	

	@Override
	public boolean consume(List<Token> tokens, Stream ps) {
		ps.setMark();
		if (ps.val() == -1) {
			tokens.add(new Inner(ps));
			return false;
		}
		while (ps.val() == '\n') {
			ps.pop();
			return tokens.add(new Inner(ps));
		}
		return false;
	}

	private class Inner extends BasicToken {

		public Inner(Stream ps) {
			this.value = "\n";
			this.startPos = ps.getMark();
			this.endPos = ps.getMark();
		}

		public Inner(String value, int start, int end) {
			this.value = value;
			this.startPos = start;
			this.endPos = end;
		}

		@Override
		public Collector consume(Collector statement,
				Stack<Collector> statements, Stack<Character> charStack) {
			if (statement.isFinished()) {
				statements.add(statement);
				statement = new Statement(startPos);
			}
			return statement;
		}

		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitEol(this);
		}

	}

	@Override
	public int consume(List<Token> tokens, CharSequence ps, int offset) {
		int totalCaptured = 0;
		Matcher matcher = pattern.matcher(ps);
		while (matcher.find(offset)) {
			String s = matcher.group();
			tokens.add(new Inner(s, offset + totalCaptured, matcher.end()));
			totalCaptured += s.length();
		}
		return totalCaptured;
	}

}
