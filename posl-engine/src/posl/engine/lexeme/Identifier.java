package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;

import posl.engine.api.Collector;
import posl.engine.api.LexUtil;
import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.api.TokenVisitor;
import posl.engine.core.BasicToken;
import posl.engine.core.Stream;
import posl.engine.type.Atom;

public class Identifier implements Lexeme {

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
