package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;

import posl.engine.api.ALexeme;
import posl.engine.api.IStatement;
import posl.engine.api.IToken;
import posl.engine.api.TokenVisitor;
import posl.engine.core.PoslStream;
import posl.engine.type.Atom;

public class Identifier extends ALexeme {

	@Override
	public boolean consume(List<IToken> tokens, PoslStream ps) {
		if (isAlpha(ps.val()) || (ps.val() == '_' && isAlpha(ps.LA(1)))) {
			return processWord(tokens, ps);
		} else if (isSpecial(ps.val())) {
			return processSpecial(tokens, ps);
		}
		return false;
	}

	private boolean processWord(List<IToken> tokens, PoslStream ps) {
		ps.setMark();
		ps.pop();
		while (isAlpha(ps.val()) || isSpecial(ps.val()) || isDigit(ps.val())
				|| ps.val() == '_') {
			ps.pop();
		}
		return tokens.add(new Inner(ps.getSubString(), ps.getMark()));
	}



	private boolean processSpecial(List<IToken> tokens, PoslStream ps) {
		ps.setMark();
		ps.pop();
		while (isSpecial(ps.val())) {
			ps.pop();
		}
		tokens.add(new Inner(ps.getSubString(), ps.getMark()));
		return true;
	}
	
	private class Inner extends IToken {
		
		public Inner(String value, int i) {
			this.value = value;
			this.startPos = i;
			this.endPos = i + value.length();
		}
		
		@Override
		public IStatement consume(IStatement statement, Stack<IStatement> statements,
				Stack<Character> charStack) {
			statement.addObject(new Atom(value));
			return statement;
		}
		
		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitIdentifier(this);
		}

	}



}
