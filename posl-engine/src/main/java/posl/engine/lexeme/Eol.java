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
import posl.engine.type.Statement;

/**
 * Consumes multiple end of line indicators
 * 
 * 
 * @author jebailey
 *
 */
public class Eol implements Lexeme {

	private static final Pattern pattern = Pattern.compile("\r?\n");
	
	private Matcher matcher;
	
	private CharSequence cachedSequence;
	
	
	@Override
	public int consume(List<Token> tokens, CharSequence ps, int offset) {
		if (ps != cachedSequence){
			cachedSequence = ps;
			matcher = pattern.matcher(ps);
		}
		matcher.region(offset, ps.length());
		if (matcher.lookingAt()) {
			String s = matcher.group();
			tokens.add(new Inner(s, offset, matcher.end()));
			return s.length();
		}
		return 0;
	}
	

	private class Inner extends BasicToken {

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
				statement = new Statement(startPos,statement.getLineNumber());
			}
			return statement;
		}

		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitEol(this);
		}

	}



}
