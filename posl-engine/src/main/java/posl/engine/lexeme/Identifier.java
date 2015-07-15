package posl.engine.lexeme;

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
import posl.engine.type.Atom;

/**
 * 
 * 
 * 
 * @author jebailey
 *
 */
public class Identifier implements Lexeme {
	
	Pattern pattern = Pattern.compile("^\\p{Alpha}\\p{Alnum}*?");

	@Override
	public boolean consume(List<Token> tokens, Stream ps) {
		if (LexUtil.isAlpha(ps.val()) || (ps.val() == '_' && LexUtil.isAlpha(ps.la(1)))) {
			return processWord(tokens, ps);
		} else if (LexUtil.isSpecial(ps.val())) {
			return processSpecial(tokens, ps);
		}
		return false;
	}

	private boolean processWord(List<Token> tokens, Stream ps) {
		ps.setMark();
		ps.pop();
		while (LexUtil.isAlpha(ps.val()) || LexUtil.isSpecial(ps.val()) || LexUtil.isDigit(ps.val())
				|| ps.val() == '_') {
			ps.pop();
		}
		return tokens.add(new Inner(ps.getSubString(), ps.getMark()));
	}



	private boolean processSpecial(List<Token> tokens, Stream ps) {
		ps.setMark();
		ps.pop();
		while (LexUtil.isSpecial(ps.val())) {
			ps.pop();
		}
		tokens.add(new Inner(ps.getSubString(), ps.getMark()));
		return true;
	}
	
	private class Inner extends BasicToken {
		
		public Inner(String value, int i) {
			this.value = new Atom(value);
			this.startPos = i;
			this.endPos = i + value.length();
		}
		
		public Inner(String value, int start, int end) {
			super(value,start,end);
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

	@Override
	public int consume(List<Token> tokens, CharSequence ps, int offset) {
		int totalCaptured = 0;
		Matcher matcher = pattern.matcher(ps);
		while (matcher.find(offset+totalCaptured)) {
			String s = matcher.group();
			tokens.add(new Inner(s, offset + totalCaptured, matcher.end()));
			totalCaptured += s.length();
		}
		return totalCaptured;
	}



}
