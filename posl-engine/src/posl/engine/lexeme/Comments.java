package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;

import posl.engine.api.ALexeme;
import posl.engine.api.IStatement;
import posl.engine.api.IToken;
import posl.engine.api.TokenVisitor;
import posl.engine.core.PoslStream;

public class Comments extends ALexeme {

	@Override
	public boolean consume(List<IToken> tokens, PoslStream ps) {
		// end of line comments
		if (ps.val() == '/' && ps.LA(1) == '/') {
			ps.mark();
			while (ps.hasMore() && ps.val() != '\n') {
				ps.pop();
			}
			tokens.add(new Inner(ps));//, ps.getMark()));
			return true;
		} else 
		if (ps.val() == '/' && ps.LA(1)== '*') {
			ps.mark();
			while (ps.hasMore()) {
				if (ps.val() == '*' && ps.LA(1)== '/') {
					ps.pop();
					ps.pop();
					break;
				}
				ps.pop();
			}
			tokens.add(new Inner(ps));
			return true;
		} 
		return false;
	}
	

	
	private class Inner extends IToken {
		
		public Inner(PoslStream ps) {
			this.value = ps.getSubString();
			this.startPos = ps.getMark();
			this.endPos = ps.pos();
		}

		@Override
		public IStatement consume(IStatement statement, Stack<IStatement> statements,
				Stack<Character> charStack) {
			return statement;
		}

		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitComments(this);
		}

	}

}
