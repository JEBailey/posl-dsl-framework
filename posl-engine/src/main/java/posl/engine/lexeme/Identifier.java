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
import posl.engine.type.Atom;

/**
 * 
 * 
 * 
 * @author jebailey
 *
 */
public class Identifier implements Lexeme {
	
	private static final Pattern pattern = Pattern.compile("\\p{IsAlphabetic}[\\p{IsAlphabetic}_\\p{IsDigit}]*");
	
	private Matcher matcher;
	
	private CharSequence cachedSequence;
	
	@Override
	public int consume(List<Token> tokens, CharSequence ps, int offset) {
		if (cachedSequence != ps){
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
		
		public Inner(String value, int i, int end) {
			this.value = new Atom(value);
			this.startPos = i;
			this.endPos = i + value.length();
		}
		
		
		@Override
		public Collector consume(Collector statement, Stack<Collector> statements,
				Stack<Character> charStack) {
			statement.add(value);
			return statement;
		}
		
		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitIdentifier(this);
		}

	}





}
